package ch.digorydoo.kutils.math

import kotlin.math.PI
import kotlin.test.Test
import kotlin.test.assertEquals

internal class AngleDiffTest {
    @Test
    fun `should yield zero when given two identical values`() {
        assertEquals(0.0f, angleDiff(0.0f, 0.0f))
        assertEquals(0.0f, angleDiff(PI.toFloat(), PI.toFloat()))
        assertEquals(0.0f, angleDiff(-PI.toFloat(), -PI.toFloat()))
        assertEquals(0.0f, angleDiff(42.0f, 42.0f))
        assertEquals(0.0f, angleDiff(-42.0f, -42.0f))
    }

    @Test
    fun `should yield a positive value less than PI when angles are close and a less than b`() {
        assertEquals(0.01f, angleDiff(0.0f, 0.01f), TOLERANCE, "(1)")
        assertEquals(0.01f, angleDiff(5.0f, 5.01f), TOLERANCE, "(2)")
        assertEquals(0.01f, angleDiff(-5.01f, -5.0f), TOLERANCE, "(3)")
        assertEquals(0.01f, angleDiff(42.0f, 42.01f), TOLERANCE, "(4)")
        assertEquals(0.01f, angleDiff(-42.01f, -42.0f), TOLERANCE, "(5)")
        assertEquals(0.01f, angleDiff(-0.005f, 0.005f), TOLERANCE, "(6)")
    }

    @Test
    fun `should yield a negative value less than PI when angles are close and a greater than b`() {
        assertEquals(-0.01f, angleDiff(0.01f, 0.0f), TOLERANCE, "(1)")
        assertEquals(-0.01f, angleDiff(5.01f, 5.0f), TOLERANCE, "(2)")
        assertEquals(-0.01f, angleDiff(-5.0f, -5.01f), TOLERANCE, "(3)")
        assertEquals(-0.01f, angleDiff(42.01f, 42.0f), TOLERANCE, "(4)")
        assertEquals(-0.01f, angleDiff(-42.0f, -42.01f), TOLERANCE, "(5)")
        assertEquals(-0.01f, angleDiff(0.005f, -0.005f), TOLERANCE, "(6)")
    }

    @Test
    fun `should correctly handle the situation when a and b are further apart`() {
        assertEquals(3.12f, angleDiff(0.0f, 3.12f), TOLERANCE, "(1)")
        assertEquals(3.13f, angleDiff(0.0f, 3.13f), TOLERANCE, "(2)")
        assertEquals(3.14f, angleDiff(0.0f, 3.14f), TOLERANCE, "(3)")
        assertEquals(-3.1331851f, angleDiff(0.0f, 3.15f), TOLERANCE, "(4)")
        assertEquals(-3.1231852f, angleDiff(0.0f, 3.16f), TOLERANCE, "(5)")
        assertEquals(-3.1131852f, angleDiff(0.0f, 3.17f), TOLERANCE, "(6)")
        assertEquals(-3.1031852f, angleDiff(0.0f, 3.18f), TOLERANCE, "(7)")
        assertEquals(-3.1031852f, angleDiff(11.0f, 14.18f), TOLERANCE, "(8)")
        assertEquals(-3.1031852f, angleDiff(22.0f, 25.18f), TOLERANCE, "(9)")
    }

    @Test
    fun `should correctly handle other edge cases`() {
        assertEquals(-PI / 2, angleDiff(0.0, 3 * PI / 2), TOLERANCE_D, "(1)")
        assertEquals(-PI / 2, angleDiff(0.0, 3 * PI / 2 + 2 * PI), TOLERANCE_D, "(2)")
        assertEquals(PI / 2, angleDiff(0.0, -3 * PI / 2), TOLERANCE_D, "(3)")
        assertEquals(PI / 2, angleDiff(0.0, -3 * PI / 2 - 2 * PI), TOLERANCE_D, "(4)")
    }

    companion object {
        private const val TOLERANCE = 0.00001f
        private const val TOLERANCE_D = 0.000001
    }
}
