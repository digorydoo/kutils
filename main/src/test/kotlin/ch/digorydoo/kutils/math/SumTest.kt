package ch.digorydoo.kutils.math

import kotlin.test.Test
import kotlin.test.assertEquals

internal class SumTest {
    @Test
    fun `should sum the values of a List mapped to Float`() {
        val list = listOf(3.3f, 0f, -9f, 10.1f)
        val sum = list.sumOfFloat { it * 2f }
        assertEquals(8.8f, sum, TOLERANCE_F)
    }

    @Test
    fun `should sum the values of a List mapped to Double`() {
        val list = listOf(3.3, 0.0, -9.0, 10.1)
        val sum = list.sumOfDouble { it * 2 }
        assertEquals(8.8, sum, TOLERANCE_D)
    }

    @Test
    fun `should sum the values of a List mapped to Int`() {
        val list = listOf("one", "three", "ninety")
        val sum = list.sumOfInt { it.length }
        assertEquals(14, sum)
    }

    @Test
    fun `should sum the values of a List mapped to Long`() {
        val list = listOf("one", "three", "ninety")
        val sum = list.sumOfLong { it.length.toLong() }
        assertEquals(14L, sum)
    }

    @Test
    fun `should sum the values of an Array mapped to Float`() {
        val arr = arrayOf(3.3f, 0f, -9f, 10.1f)
        val sum = arr.sumOfFloat { it * 2f }
        assertEquals(8.8f, sum, TOLERANCE_F)
    }

    @Test
    fun `should sum the values of an Array mapped to Double`() {
        val arr = arrayOf(3.3, 0.0, -9.0, 10.1)
        val sum = arr.sumOfDouble { it * 2 }
        assertEquals(8.8, sum, TOLERANCE_D)
    }

    @Test
    fun `should sum the values of an Array mapped to Int`() {
        val arr = arrayOf("one", "three", "ninety")
        val sum = arr.sumOfInt { it.length }
        assertEquals(14, sum)
    }

    @Test
    fun `should sum the values of an Array mapped to Long`() {
        val arr = arrayOf("one", "three", "ninety")
        val sum = arr.sumOfLong { it.length.toLong() }
        assertEquals(14L, sum)
    }

    @Test
    fun `should sum the values of a List of Float starting from some index`() {
        val list = listOf(3.3f, 0f, -9f, 10.1f)
        val sum = list.sumStartingFrom(2)
        assertEquals(1.1f, sum, TOLERANCE_F)
    }

    @Test
    fun `should sum the values of a List of Double starting from some index`() {
        val list = listOf(3.3, 0.0, -9.0, 10.1)
        val sum = list.sumStartingFrom(2)
        assertEquals(1.1, sum, TOLERANCE_D)
    }

    @Test
    fun `should sum the values of a List of Int starting from some index`() {
        val list = listOf(3, 0, -9, 10)
        val sum = list.sumStartingFrom(2)
        assertEquals(1, sum)
    }

    @Test
    fun `should sum the values of a List of Long starting from some index`() {
        val list = listOf(3L, 0L, -9L, 10L)
        val sum = list.sumStartingFrom(2)
        assertEquals(1L, sum)
    }

    @Test
    fun `should sum the values of an Array of Float starting from some index`() {
        val arr = arrayOf(3.3f, 0f, -9f, 10.1f)
        val sum = arr.sumStartingFrom(2)
        assertEquals(1.1f, sum, TOLERANCE_F)
    }

    @Test
    fun `should sum the values of an Array of Double starting from some index`() {
        val arr = arrayOf(3.3, 0.0, -9.0, 10.1)
        val sum = arr.sumStartingFrom(2)
        assertEquals(1.1, sum, TOLERANCE_D)
    }

    @Test
    fun `should sum the values of an Array of Int starting from some index`() {
        val arr = arrayOf(3, 0, -9, 10)
        val sum = arr.sumStartingFrom(2)
        assertEquals(1, sum)
    }

    @Test
    fun `should sum the values of an Array of Long starting from some index`() {
        val arr = arrayOf(3L, 0L, -9L, 10L)
        val sum = arr.sumStartingFrom(2)
        assertEquals(1L, sum)
    }

    @Test
    fun `should sum the values of a List mapped to Float starting from some index`() {
        val list = listOf(3.3f, 0f, -9f, 10.1f)
        val sum = list.sumOfFloatStartingFrom(1) { it * 2f }
        assertEquals(2.2f, sum, TOLERANCE_F)
    }

    @Test
    fun `should sum the values of a List mapped to Double starting from some index`() {
        val list = listOf(3.3, 0.0, -9.0, 10.1)
        val sum = list.sumOfDoubleStartingFrom(1) { it * 2.0 }
        assertEquals(2.2, sum, TOLERANCE_D)
    }

    @Test
    fun `should sum the values of a List mapped to Int starting from some index`() {
        val list = listOf("one", "three", "ninety")
        val sum = list.sumOfIntStartingFrom(1) { it.length }
        assertEquals(11, sum)
    }

    @Test
    fun `should sum the values of a List mapped to Long starting from some index`() {
        val list = listOf("one", "three", "ninety")
        val sum = list.sumOfLongStartingFrom(1) { it.length.toLong() }
        assertEquals(11L, sum)
    }

    @Test
    fun `should sum the values of an Array mapped to Float starting from some index`() {
        val arr = arrayOf(3.3f, 0f, -9f, 10.1f)
        val sum = arr.sumOfFloatStartingFrom(1) { it * 2f }
        assertEquals(2.2f, sum, TOLERANCE_F)
    }

    @Test
    fun `should sum the values of an Array mapped to Double starting from some index`() {
        val arr = arrayOf(3.3, 0.0, -9.0, 10.1)
        val sum = arr.sumOfDoubleStartingFrom(1) { it * 2.0 }
        assertEquals(2.2, sum, TOLERANCE_D)
    }

    @Test
    fun `should sum the values of an Array mapped to Int starting from some index`() {
        val arr = arrayOf("one", "three", "ninety")
        val sum = arr.sumOfIntStartingFrom(1) { it.length }
        assertEquals(11, sum)
    }

    @Test
    fun `should sum the values of an Array mapped to Long starting from some index`() {
        val arr = arrayOf("one", "three", "ninety")
        val sum = arr.sumOfLongStartingFrom(1) { it.length.toLong() }
        assertEquals(11L, sum)
    }

    companion object {
        private const val TOLERANCE_F = 0.000001f
        private const val TOLERANCE_D = 0.000001
    }
}
