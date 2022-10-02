package me.mervap.sort

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole

@Suppress("unused", "UNUSED_PARAMETER")
open class SequentialSortBenchmark : SortBenchmark() {

  @Benchmark
  fun sequential(bh: Blackhole) {
    array.sequentialQuickSort()
  }

}