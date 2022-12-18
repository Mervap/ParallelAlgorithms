package me.mervap.bfs

import java.lang.invoke.MethodHandles

@Suppress("MemberVisibilityCanBePrivate", "CanBeParameter")
class AtomicBooleanArray(val length: Int) {
  private val array = BooleanArray(length)

  operator fun set(ind: Int, value: Boolean) {
    varHandle.setVolatile(array, ind, value)
  }

  fun compareAndSet(ind: Int, expectedValue: Boolean, newValue: Boolean): Boolean {
    return varHandle.compareAndSet(array, ind, expectedValue, newValue)
  }

  companion object {
    private val varHandle = MethodHandles.arrayElementVarHandle(BooleanArray::class.java)
  }
}
