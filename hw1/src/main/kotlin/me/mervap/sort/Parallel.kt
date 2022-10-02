package me.mervap.sort

import kotlinx.coroutines.*

@OptIn(ExperimentalCoroutinesApi::class)
fun IntArray.parallelQuickSort(parallelism: Int = 4) = runBlocking {
  withContext(Dispatchers.Default.limitedParallelism(parallelism)) {
    coroutineScope {
      parallelQuickSort(0, size)
    }
  }
}

private suspend fun IntArray.parallelQuickSort(l: Int, r: Int) {
  if (r - l < 10000) {
    sequentialQuickSort(l, r)
    return
  }

  val m = partition(l, r)
  coroutineScope {
    if (l < m) {
      launch { parallelQuickSort(l, m) }
    }
    if (m < r) {
      launch { parallelQuickSort(m, r) }
    }
  }
}
