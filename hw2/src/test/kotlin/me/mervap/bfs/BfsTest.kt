@file:OptIn(ExperimentalStdlibApi::class)

package me.mervap.bfs

import me.mervap.bfs.Bfs.PARALLEL
import me.mervap.bfs.Bfs.SEQUENTIAL
import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource
import kotlin.random.Random
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

enum class Bfs {
  SEQUENTIAL, PARALLEL
}

class BfsTest {

  @ParameterizedTest
  @EnumSource(Bfs::class)
  fun bamboo(bfs: Bfs) {
    val nodes = IntArray(NODES_CNT) { it }
    nodes.shuffle()

    val expected = IntArray(NODES_CNT)
    val edges = Array(NODES_CNT) { intArrayOf() }
    nodes.forEachIndexed { ind, node ->
      edges[node] = when {
        0 < ind && ind < NODES_CNT - 1 -> {
          intArrayOf(nodes[ind - 1], nodes[ind + 1])
        }

        ind < NODES_CNT - 1 -> {
          intArrayOf(nodes[ind + 1])
        }

        else -> {
          intArrayOf(nodes[ind - 1])
        }
      }
      expected[node] = ind
    }

    doTest(bfs, AdjacencyListGraph(edges), nodes[0]) { actual ->
      assertContentEquals(expected, actual)
    }
  }

  @ParameterizedTest
  @EnumSource(Bfs::class)
  fun cycle(bfs: Bfs) {
    val nodes = IntArray(NODES_CNT) { it }
    nodes.shuffle()

    val edges = Array(NODES_CNT) { intArrayOf() }
    nodes.forEachIndexed { ind, node ->
      edges[node] = when {
        0 < ind && ind < NODES_CNT - 1 -> {
          intArrayOf(nodes[ind - 1], nodes[ind + 1])
        }

        ind < NODES_CNT - 1 -> {
          intArrayOf(nodes.last(), nodes[ind + 1])
        }

        else -> {
          intArrayOf(nodes[ind - 1], nodes.first())
        }
      }
    }

    val expected = IntArray(NODES_CNT)
    expected[nodes[0]] = 0
    var dist = 1
    var i = 1
    var j = NODES_CNT - 1
    while (i <= j) {
      expected[nodes[i]] = dist
      expected[nodes[j]] = dist
      ++dist
      ++i
      --j
    }

    doTest(bfs, AdjacencyListGraph(edges), nodes[0]) { actual ->
      assertContentEquals(expected, actual)
    }
  }

  @ParameterizedTest
  @EnumSource(Bfs::class)
  fun tree(bfs: Bfs) {
    val edges = mutableListOf<IntArray>()
    val expected = IntArray(NODES_CNT)
    for (i in 0..<NODES_CNT) {
      val parent = if (i == 0) -1 else (i - 1) / 2
      val left = 2 * i + 1
      val right = 2 * i + 2
      if (parent >= 0) {
        expected[i] = expected[parent] + 1
      }
      edges.add(
        listOf(left, right, parent).filter { it in 0..<NODES_CNT }.toIntArray()
      )
    }

    doTest(bfs, AdjacencyListGraph(edges.toTypedArray()), 0) { actual ->
      assertContentEquals(expected, actual)
    }
  }


  @ParameterizedTest
  @EnumSource(Bfs::class)
  fun `all connected`(bfs: Bfs) {
    val edges = Array(NODES_CNT) { node ->
      IntArray(NODES_CNT - 1) {
        if (it < node) it else it + 1
      }.also { it.shuffle() }
    }

    val startNode = Random.nextInt(NODES_CNT)
    doTest(bfs, AdjacencyListGraph(edges), startNode) { actual ->
      val expected = IntArray(NODES_CNT) { if (it == startNode) 0 else 1 }
      assertContentEquals(expected, actual)
    }
  }

  @ParameterizedTest
  @EnumSource(Bfs::class)
  fun `all connected layers`(bfs: Bfs) {
    val layersCnt = Random.nextInt(10, NODES_CNT / 3)
    val nodes = IntArray(NODES_CNT) { it }
    nodes.shuffle()

    val layerSize = NODES_CNT / layersCnt
    val layers = Array(layersCnt) { layerInd ->
      val st = layerInd * layerSize
      val end = if (layerInd == layersCnt - 1) NODES_CNT else st + layerSize
      val layersNodes = mutableListOf<Int>()
      for (i in st..<end) {
        layersNodes.add(nodes[i])
      }
      layersNodes
    }

    val expected = IntArray(NODES_CNT)
    val edges = Array(NODES_CNT) { intArrayOf() }
    layers.forEachIndexed { ind, layerNodes ->
      for (node in layerNodes) {
        expected[node] = ind
        val nodeEdges = layerNodes.filter { it != node }.toMutableList()
        if (ind > 0) {
          nodeEdges.addAll(layers[ind - 1])
        }
        if (ind < layersCnt - 1) {
          nodeEdges.addAll(layers[ind + 1])
        }
        nodeEdges.shuffle()
        edges[node] = nodeEdges.toIntArray()
      }
    }

    val startNode = layers[0].random()
    layers[0].filter { it != startNode }.forEach { expected[it] += 1 }
    doTest(bfs, AdjacencyListGraph(edges), startNode) { actual ->
      assertContentEquals(expected, actual)
    }
  }

  @ParameterizedTest
  @EnumSource(Bfs::class)
  fun `no edges`(bfs: Bfs) {
    val startNode = Random.nextInt(NODES_CNT)
    val edges = (0..<NODES_CNT).map { IntArray(0) }.toTypedArray()
    doTest(bfs, AdjacencyListGraph(edges), startNode) { actual ->
      val expected = IntArray(NODES_CNT) { if (it == startNode) 0 else -1 }
      assertContentEquals(expected, actual)
    }
  }

  @ParameterizedTest
  @EnumSource(Bfs::class)
  fun `two separate components`(bfs: Bfs) {
    val nodes = IntArray(NODES_CNT) { it }
    nodes.shuffle()

    val expected = IntArray(NODES_CNT)
    val edges = Array(NODES_CNT) { IntArray(0) }
    nodes.forEachIndexed { ind, node ->
      val nodeEdges = mutableListOf<Int>()
      when {
        ind < NODES_CNT / 2 -> {
          for (i in 0..<NODES_CNT / 2) {
            if (i == ind) continue
            nodeEdges.add(nodes[i])
          }
          expected[node] = 1
        }

        else -> {
          for (i in NODES_CNT / 2..<NODES_CNT) {
            if (i == ind) continue
            nodeEdges.add(nodes[i])
          }
          expected[node] = -1
        }
      }
      edges[node] = nodeEdges.toIntArray()
    }

    val startNode = nodes[Random.nextInt(NODES_CNT / 2)]
    expected[startNode] = 0
    doTest(bfs, AdjacencyListGraph(edges), startNode) { actual ->
      assertContentEquals(expected, actual)
    }
  }

  @ParameterizedTest
  @EnumSource(Bfs::class)
  fun `single node`(bfs: Bfs) = doTest(bfs, AdjacencyListGraph(Array(1) { IntArray(0) }), 0) { actual ->
    assertContentEquals(intArrayOf(0), actual)
  }

  @ParameterizedTest
  @EnumSource(Bfs::class)
  fun `extra small cubic`(bfs: Bfs) = doCubicTest(bfs, 1)

  @ParameterizedTest
  @EnumSource(Bfs::class)
  fun `small cubic`(bfs: Bfs) = doCubicTest(bfs, 10)

  @ParameterizedTest
  @EnumSource(Bfs::class)
  fun `big cubic`(bfs: Bfs) = doCubicTest(bfs, 300)

  private fun doCubicTest(bfs: Bfs, size: Int) = doTest(bfs, CubicGraph(size), 0) {
    for (z in 0..<size) {
      val zShift = z * size * size
      for (y in 0..<size) {
        val shift = zShift + y * size
        for (x in 0..<size) {
          assertEquals(x + y + z, it[shift + x], "Distance to ($x, $y, $z)")
        }
      }
    }
  }

  private fun doTest(bfs: Bfs, graph: Graph, startNode: Int, check: (IntArray) -> Unit) {
    val distances = when (bfs) {
      SEQUENTIAL -> graph.sequentialBfs(startNode)
      PARALLEL -> graph.parallelBfs(startNode)
    }
    check(distances)
  }
}

private val NODES_CNT = 1000