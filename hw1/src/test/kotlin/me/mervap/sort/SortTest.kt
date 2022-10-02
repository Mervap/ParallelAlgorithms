package me.mervap.sort

import org.junit.jupiter.params.ParameterizedTest
import org.junit.jupiter.params.provider.EnumSource

import kotlin.random.Random
import kotlin.test.assertContentEquals
import kotlin.test.assertEquals

enum class Sorter {
  SEQUENTIAL, PARALLEL
}

class SortTest {

  @ParameterizedTest
  @EnumSource(Sorter::class)
  fun allPermutations(sorter: Sorter) = doPermutationsTest(sorter) { it }

  @ParameterizedTest
  @EnumSource(Sorter::class)
  fun allPermutationsRandom(sorter: Sorter) = doPermutationsTest(sorter) { Random.nextInt(-3, 3) }

  @ParameterizedTest
  @EnumSource(Sorter::class)
  fun sameValues(sorter: Sorter) = doTest(sorter) {
    IntArray(1_000_000) { 10 }
  }

  @ParameterizedTest
  @EnumSource(Sorter::class)
  fun bigArray(sorter: Sorter) = doTest(sorter) {
    IntArray(1_000_000) { Random.nextInt() }
  }

  @ParameterizedTest
  @EnumSource(Sorter::class)
  fun bigArrayLimitedRandom(sorter: Sorter) = doTest(sorter) {
    IntArray(1_000_000) { Random.nextInt(-1000, 1000) }
  }

  private fun permutations(len: Int): Sequence<IntArray> {
    return generateSequence(IntArray(len) { it }) { array ->
      var index = -1
      for (i in len - 1 downTo 1) {
        if (array[i] > array[i - 1]) {
          index = i - 1
          break
        }
      }

      if (index == -1) {
        return@generateSequence null
      }


      var j = len - 1
      for (i in len - 1 downTo index + 1) {
        if (array[i] > array[index]) {
          j = i
          break
        }
      }

      val tmp = array[index]
      array[index] = array[j]
      array[j] = tmp

      array.reverse(index + 1, len)
      array
    }
  }

  private fun doPermutationsTest(sorter: Sorter, init: (Int) -> Int) {
    for (i in 1..8) {
      var cnt = 0
      val data = IntArray(i, init)
      for (perm in permutations(i)) {
        cnt += 1
        doTest(sorter) {
          IntArray(i) { data[perm[it]] }
        }
      }

      var fact = 1
      for (j in 2..i) {
        fact *= j
      }

      assertEquals(fact, cnt)
    }
  }

  private fun doTest(sorter: Sorter, init: () -> IntArray) {
    val array = init()
    val expected = array.clone()

    expected.sort()
    when (sorter) {
      Sorter.SEQUENTIAL -> array.sequentialQuickSort()
      Sorter.PARALLEL -> array.parallelQuickSort()
    }

    if (expected.size < 1000) {
      assertContentEquals(expected, array)
    } else {
      // Can't use assertContentEquals - OOM
      assertEquals(expected.size, array.size)
      for (i in expected.indices) {
        assertEquals(expected[i], array[i], "elements differ at index $i")
      }
    }
  }
}
