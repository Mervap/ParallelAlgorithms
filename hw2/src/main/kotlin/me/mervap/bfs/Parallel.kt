package me.mervap.bfs

import kotlinx.coroutines.*

@OptIn(ExperimentalCoroutinesApi::class)
fun Graph.parallelBfs(startNode: Int, parallelism: Int = 4) = runBlocking {
  withContext(Dispatchers.Default.limitedParallelism(parallelism)) {
    coroutineScope {
      parallelBfs(startNode)
    }
  }
}

@OptIn(ExperimentalStdlibApi::class)
private suspend fun Graph.parallelBfs(startNode: Int): IntArray {
  var frontier = IntArray(1) { startNode }
  var frontierDistance = 0
  val distances = IntArray(nodesCount) { -1 }

  val visited = AtomicBooleanArray(nodesCount)
  visited[startNode] = true

  while (frontier.isNotEmpty()) {
    val frontierSize = frontier.size
    val outDegs = IntArray(frontierSize)

    // Calculate node out degrees & update distances for frontier
    pfor(frontierSize, seqIfLessThan = SEQ_BOUND) { ind ->
      val node = frontier[ind]
      distances[node] = frontierDistance
      outDegs[ind] = edges(node).size
    }
    frontierDistance += 1

    // Scan on out degrees
    for (i in 1..<frontierSize) {
      outDegs[i] += outDegs[i - 1]
    }

    // Find nodes for next frontier. Probably with empty slots
    val distinctOutDegs = IntArray(frontierSize)
    val newNodes = IntArray(outDegs.last())
    pfor(frontierSize, seqIfLessThan = CAS_SEQ_BOUND) { ind ->
      val relativeInd = if (ind == 0) 0 else outDegs[ind - 1]
      var currentNodesInd = relativeInd
      for (node in edges(frontier[ind])) {
        if (visited.compareAndSet(node, expectedValue = false, newValue = true)) {
          newNodes[currentNodesInd++] = node
        }
      }
      distinctOutDegs[ind] = currentNodesInd - relativeInd
    }

    // Scan on out degrees with unique values
    for (i in 1..<frontierSize) {
      distinctOutDegs[i] += distinctOutDegs[i - 1]
    }

    // If no empty slots - we've done
    if (distinctOutDegs.last() == newNodes.size) {
      frontier = newNodes
      continue
    }

    // Build next frontier without empty slots
    frontier = IntArray(distinctOutDegs.last())
    pfor(frontierSize, seqIfLessThan = SEQ_BOUND) { ind ->
      val (relativeInd, frontierRelativeInd) = when (ind) {
        0 -> 0 to 0
        else -> outDegs[ind - 1] to distinctOutDegs[ind - 1]
      }
      val nodesToCopy = distinctOutDegs[ind] - frontierRelativeInd
      for (i in 0..<nodesToCopy) {
        frontier[frontierRelativeInd + i] = newNodes[relativeInd + i]
      }
    }
  }

  return distances
}

@OptIn(ExperimentalStdlibApi::class)
private suspend inline fun pfor(cnt: Int, seqIfLessThan: Int, crossinline body: (Int) -> Unit) {
  if (cnt < seqIfLessThan) {
    for (i in 0..<cnt) {
      body(i)
    }
  } else {
    coroutineScope {
      val blockSize = cnt / PFOR_PAR
      var relativeInd = 0
      for (p in 1..PFOR_PAR) {
        val start = relativeInd
        relativeInd += blockSize
        val end = if (p == PFOR_PAR) cnt else relativeInd
        launch {
          for (i in start..<end) {
            body(i)
          }
        }
      }
    }
  }
}

private const val SEQ_BOUND = 48
private const val CAS_SEQ_BOUND = 2
private const val PFOR_PAR = 4
