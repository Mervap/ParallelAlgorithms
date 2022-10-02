package me.mervap.sort

import org.openjdk.jmh.annotations.*
import org.openjdk.jmh.infra.Blackhole
import java.util.*

@Suppress("unused", "UNUSED_PARAMETER")
open class StdlibSortBenchmark : SortBenchmark() {

  @Benchmark
  fun stdlibSequential(bh: Blackhole) {
    array.sort()
  }

  @Benchmark
  fun stdlibParallel(bh: Blackhole) {
    Arrays.parallelSort(array)
  }

}