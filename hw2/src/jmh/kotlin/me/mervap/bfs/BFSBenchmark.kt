package me.mervap.bfs

import org.openjdk.jmh.annotations.*
import java.util.*
import java.util.concurrent.TimeUnit

@Fork(1)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.SECONDS)
@Warmup(iterations = 3, time = 1)
@Measurement(iterations = 5, time = 1)
@Suppress("unused")
abstract class BFSBenchmark {
  protected lateinit var graph: Graph

  @Setup(Level.Iteration)
  fun setup() {
    graph = CubicGraph(800)
  }
}