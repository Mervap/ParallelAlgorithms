package me.mervap.sort

import kotlin.random.Random

@OptIn(ExperimentalStdlibApi::class)
internal fun IntArray.sequentialQuadraticSort(l: Int, r: Int) {
  if (r - l < 2) {
    return
  }
  for (i in l..<r) {
    for (j in i + 1..<r) {
      if (get(i) > get(j)) {
        swap(i, j)
      }
    }
  }
}

internal fun IntArray.partition(l: Int, r: Int): Int {
  val thresholdVal = get(Random.nextInt(l, r))
  var lBound = l
  var rBound = r - 1
  while (lBound <= rBound) {
    while (get(lBound) < thresholdVal) {
      ++lBound
    }
    while (get(rBound) > thresholdVal) {
      --rBound
    }

    if (lBound <= rBound) {
      if (get(lBound) > get(rBound)) {
        swap(lBound, rBound)
      }
      ++lBound
      --rBound
    }
  }
  return lBound
}

private fun IntArray.swap(i: Int, j: Int) {
  val tmp = get(i)
  set(i, get(j))
  set(j, tmp)
}
