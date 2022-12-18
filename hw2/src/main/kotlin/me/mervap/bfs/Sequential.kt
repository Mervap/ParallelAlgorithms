package me.mervap.bfs

fun Graph.sequentialBfs(startNode: Int): IntArray {
  val queue = ArrayDeque<Int>()
  val distance = IntArray(nodesCount) { -1 }
  distance[startNode] = 0
  queue.addLast(startNode)

  while (queue.isNotEmpty()) {
    val node = queue.removeFirst()
    for (edge in edges(node)) {
      if (distance[edge] == -1) {
        distance[edge] = distance[node] + 1
        queue.addLast(edge)
      }
    }
  }

  return distance
}
