package ch.digorydoo.kutils.collections

import kotlin.test.Test
import kotlin.test.assertEquals

internal class AverageOrNullTest {
    @Test
    fun `should return null when there is no entry`() {
        class X(val x: Int)

        val list = listOf<X>()
        val avg = list.averageOrNull { it.x }
        assertEquals(null, avg, "should be null")
    }

    @Test
    fun `should return the value when there is only one entry`() {
        class X(val x: Int)

        val list = listOf(X(42))
        val avg = list.averageOrNull { it.x }
        assertEquals(42.0f, avg)
    }

    @Test
    fun `should return the average when there are three entries`() {
        val list = listOf(1, 7, 19)
        val avg = list.averageOrNull { it }
        assertEquals(9.0f, avg)
    }
}

internal class WeightedAverageTest {
    @Test
    fun `should return null when there is no entry`() {
        class X(val x: Int)

        val list = listOf<X>()
        val result = list.weightedAverageOrNull { index, value -> ValueAndWeight(value.x.toFloat(), 9.5f) }
        assertEquals(null, result, "should be null")
    }

    @Test
    fun `should return the value when there is only one entry`() {
        class X(val x: Int)

        val list = listOf(X(42))
        val result = list.weightedAverageOrNull { index, value -> ValueAndWeight(value.x.toFloat(), 9.5f) }
        assertEquals(42.0f, result)
    }

    @Test
    fun `should return the average when all weights are the same`() {
        val list = listOf(1, 7, 19)
        val result = list.weightedAverageOrNull { index, value -> ValueAndWeight(value.toFloat(), 9.5f) }
        assertEquals(9.0f, result)
    }

    @Test
    fun `should return the weighted average when the weights differ`() {
        val list = listOf(1, 7, 19)
        val result = list.weightedAverageOrNull { index, value ->
            ValueAndWeight(value.toFloat(), 1.0f / (1.0f + index))
        }
        assertEquals(5.909091f, result)
    }
}
