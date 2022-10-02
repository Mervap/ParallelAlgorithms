package me.mervap.sort

fun IntArray.sequentialQuickSort() = sequentialQuickSort(0, size)

internal fun IntArray.sequentialQuickSort(l: Int, r: Int) {
  if (r - l < 12) {
    sequentialQuadraticSort(l, r)
    return
  }

  val m = partition(l, r)
  if (l < m) {
    sequentialQuickSort(l, m)
  }
  if (m < r) {
    sequentialQuickSort(m, r)
  }
}
