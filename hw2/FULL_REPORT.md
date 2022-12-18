```
# JMH version: 1.35
# VM version: JDK 17.0.4.1, OpenJDK 64-Bit Server VM, 17.0.4.1+9-LTS
# VM invoker: /Users/mervap/Library/Java/JavaVirtualMachines/corretto-17.0.4.1/Contents/Home/bin/java
# VM options: -Dfile.encoding=UTF-8 -Djava.io.tmpdir=/Users/mervap/Study/ParallelAlgo/hw2/build/tmp/jmh -Duser.country=US -Duser.language=en -Duser.variant -XX:+UseZGC -XX:ActiveProcessorCount=4 -Xms25G -Xmx25G -Xmn15G
# Blackhole mode: compiler (auto-detected, use -Djmh.blackhole.autoDetect=false to disable)
# Warmup: 3 iterations, 1 s each
# Measurement: 5 iterations, 1 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: me.mervap.bfs.ParallelBFSBenchmark.parallel
# Parameters: (parallelism = 4)

# Run progress: 0.00% complete, ETA 00:00:16
# Fork: 1 of 1
# Warmup Iteration   1: 25.089 s/op
# Warmup Iteration   2: 22.849 s/op
# Warmup Iteration   3: 22.234 s/op
Iteration   1: 22.353 s/op
Iteration   2: 22.186 s/op
Iteration   3: 22.298 s/op
Iteration   4: 21.845 s/op
Iteration   5: 21.836 s/op


Result "me.mervap.bfs.ParallelBFSBenchmark.parallel":  
  22.104 ±(99.9%) 0.954 s/op [Average]
  (min, avg, max) = (21.836, 22.104, 22.353), stdev = 0.248
  CI (99.9%): [21.150, 23.057] (assumes normal distribution)


# JMH version: 1.35
# VM version: JDK 17.0.4.1, OpenJDK 64-Bit Server VM, 17.0.4.1+9-LTS
# VM invoker: /Users/mervap/Library/Java/JavaVirtualMachines/corretto-17.0.4.1/Contents/Home/bin/java
# VM options: -Dfile.encoding=UTF-8 -Djava.io.tmpdir=/Users/mervap/Study/ParallelAlgo/hw2/build/tmp/jmh -Duser.country=US -Duser.language=en -Duser.variant -XX:+UseZGC -XX:ActiveProcessorCount=4 -Xms25G -Xmx25G -Xmn15G
# Blackhole mode: compiler (auto-detected, use -Djmh.blackhole.autoDetect=false to disable)
# Warmup: 3 iterations, 1 s each
# Measurement: 5 iterations, 1 s each
# Timeout: 10 min per iteration
# Threads: 1 thread, will synchronize iterations
# Benchmark mode: Average time, time/op
# Benchmark: me.mervap.bfs.SequentialBFSBenchmark.sequential

# Run progress: 50.00% complete, ETA 00:03:02
# Fork: 1 of 1
# Warmup Iteration   1: 68.752 s/op
# Warmup Iteration   2: 67.539 s/op
# Warmup Iteration   3: 66.886 s/op
Iteration   1: 67.339 s/op
Iteration   2: 66.328 s/op
Iteration   3: 66.829 s/op
Iteration   4: 66.793 s/op
Iteration   5: 66.814 s/op


Result "me.mervap.bfs.SequentialBFSBenchmark.sequential":
  66.821 ±(99.9%) 1.378 s/op [Average]
  (min, avg, max) = (66.328, 66.821, 67.339), stdev = 0.358
  CI (99.9%): [65.443, 68.199] (assumes normal distribution)


# Run complete. Total time: 00:11:49

REMEMBER: The numbers below are just data. To gain reusable insights, you need to follow up on
why the numbers are the way they are. Use profilers (see -prof, -lprof), design factorial
experiments, perform baseline and negative tests that provide experimental control, make sure
the benchmarking environment is safe on JVM/OS/HW level, ask for reviews from the domain experts.
Do not assume the numbers tell you what you want them to tell.

NOTE: Current JVM experimentally supports Compiler Blackholes, and they are in use. Please exercise
extra caution when trusting the results, look into the generated code to check the benchmark still
works, and factor in a small probability of new VM bugs. Additionally, while comparisons between
different JVMs are already problematic, the performance difference caused by different Blackhole
modes can be very significant. Please make sure you use the consistent Blackhole mode for comparisons.

Benchmark                          (parallelism)  Mode  Cnt   Score   Error  Units
ParallelBFSBenchmark.parallel                  4  avgt    5  22.104 ± 0.954   s/op
SequentialBFSBenchmark.sequential            N/A  avgt    5  66.821 ± 1.378   s/op
```