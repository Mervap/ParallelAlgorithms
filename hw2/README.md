# Homework 2

![Build](https://github.com/Mervap/ParallelAlgorithms/actions/workflows/build_hw2.yml/badge.svg)

## Info

Kotlin + coroutines + ZGC

Run benchmarking: `./gradlew jmh`

## Local results:

Measured on a cubic graph `[800 x 800 x 800]`

```
Benchmark                          (parallelism)  Mode  Cnt   Score   Error  Units
ParallelBFSBenchmark.parallel                  4  avgt    5  22.104 ± 0.954   s/op
SequentialBFSBenchmark.sequential            N/A  avgt    5  66.821 ± 1.378   s/op
```

[Full JMH Report](FULL_REPORT.md)

Speed up by `3.02` times