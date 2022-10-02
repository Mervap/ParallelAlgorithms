package me.mervap.sort

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole

@Suppress("unused", "UNUSED_PARAMETER")
open class ParallelSortBenchmark : SortBenchmark() {

  @Param(value = ["1", "4", "8"])
  var parallelism: Int = 1

  @Benchmark
  fun parallel(bh: Blackhole) {
    array.parallelQuickSort(parallelism)
  }
}