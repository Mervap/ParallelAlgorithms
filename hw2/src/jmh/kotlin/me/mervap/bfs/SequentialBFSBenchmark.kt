package me.mervap.bfs

import org.openjdk.jmh.annotations.Benchmark
import org.openjdk.jmh.infra.Blackhole

@Suppress("unused")
open class SequentialBFSBenchmark : BFSBenchmark() {

  @Benchmark
  fun sequential(bh: Blackhole) {
    bh.consume(graph.sequentialBfs(0))
  }
}
