package me.mervap.bfs

interface Graph {
  val nodesCount: Int
  fun edges(node: Int): IntArray
}

class AdjacencyListGraph(private val edges: Array<IntArray>) : Graph {
  override val nodesCount = edges.size
  override fun edges(node: Int): IntArray = edges[node]
}

class CubicGraph(private val sideLen: Int) : Graph {
  private val sideArea = sideLen * sideLen
  override val nodesCount = sideArea * sideLen

  override fun edges(node: Int): IntArray {
    val x = node % sideLen
    val y = node % sideArea / sideLen
    val z = node / sideArea

    var edgesCount = 0
    if (x > 0) ++edgesCount
    if (y > 0) ++edgesCount
    if (z > 0) ++edgesCount
    if (x < sideLen - 1) ++edgesCount
    if (y < sideLen - 1) ++edgesCount
    if (z < sideLen - 1) ++edgesCount

    val edges = IntArray(edgesCount)
    edgesCount = 0
    if (x > 0) edges[edgesCount++] = node - 1
    if (y > 0) edges[edgesCount++] = node - sideLen
    if (z > 0) edges[edgesCount++] = node - sideArea
    if (x < sideLen - 1) edges[edgesCount++] = node + 1
    if (y < sideLen - 1) edges[edgesCount++] = node + sideLen
    if (z < sideLen - 1) edges[edgesCount] = node + sideArea

    return edges
  }
}