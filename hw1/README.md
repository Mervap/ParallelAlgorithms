# Homework 1

![Build](https://github.com/Mervap/ParallelAlgorithms/actions/workflows/build_hw1.yml/badge.svg)

## Info

Kotlin + coroutines

Run benchmarking: `./gradlew jmh`

ThreadPool limited by `-XX:ActiveProcessorCount=4` (for proof there are benchmarking for `parallelism = 8`)

## Local results:

```
Benchmark                             (parallelism)  Mode  Cnt      Score      Error  Units
StdlibSortBenchmark.stdlibSequential            N/A  avgt    5   8248.526 ± 1005.776  ms/op
StdlibSortBenchmark.stdlibParallel              N/A  avgt    5   2609.100 ±  985.001  ms/op

SequentialSortBenchmark.sequential              N/A  avgt    5  11784.529 ± 2215.460  ms/op
ParallelSortBenchmark.parallel                    4  avgt    5   3643.907 ±  817.377  ms/op

ParallelSortBenchmark.parallel                    1  avgt    5  12569.444 ± 1348.745  ms/op
ParallelSortBenchmark.parallel                    8  avgt    5   3783.053 ±  432.357  ms/op
```

Speed up by `3.2` times