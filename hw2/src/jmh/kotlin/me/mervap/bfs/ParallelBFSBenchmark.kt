package me.mervap.bfs

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.annotations.Param
import org.openjdk.jmh.infra.Blackhole

@Suppress("unused")
open class ParallelBFSBenchmark : BFSBenchmark() {

  @Param(value = ["4"])
  var parallelism: Int = 1

  @Benchmark
  fun parallel(bh: Blackhole) {
    bh.consume(graph.parallelBfs(0, parallelism))
  }
}