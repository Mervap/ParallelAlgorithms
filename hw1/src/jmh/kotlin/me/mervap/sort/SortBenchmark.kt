package me.mervap.sort

import org.openjdk.jmh.annotations.*
import java.util.*
import java.util.concurrent.TimeUnit
import kotlin.random.Random

@Fork(1)
@State(Scope.Benchmark)
@BenchmarkMode(Mode.AverageTime)
@OutputTimeUnit(TimeUnit.MILLISECONDS)
@Warmup(iterations = 5, time = 1)
@Measurement(iterations = 5, time = 1)
@Suppress("unused")
abstract class SortBenchmark {
  protected lateinit var array: IntArray

  @Setup(Level.Iteration)
  fun setup() {
    array = IntArray(1e8.toInt()) { Random.nextInt() }
  }
}
