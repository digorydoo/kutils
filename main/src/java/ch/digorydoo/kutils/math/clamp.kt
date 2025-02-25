@file:Suppress("unused")

package ch.digorydoo.kutils.math

/**
 * @param x: The value to be clamped
 * @param min: Minimum value
 * @param max: Maximum value
 * @return The value x clamped to the minimum and maximum given
 */
fun clamp(x: Double, min: Double, max: Double) = when {
    x <= min -> min
    x >= max -> max
    else -> x
}

/**
 * @param x: The value to be clamped
 * @param min: Minimum value
 * @param max: Maximum value
 * @return The value x clamped to the minimum and maximum given
 */
fun clamp(x: Float, min: Float, max: Float) = when {
    x <= min -> min
    x >= max -> max
    else -> x
}

/**
 * @param x: The value to be clamped
 * @param min: Minimum value
 * @param max: Maximum value
 * @return The value x clamped to the minimum and maximum given
 */
fun clamp(x: Int, min: Int, max: Int) = when {
    x <= min -> min
    x >= max -> max
    else -> x
}

/**
 * @param x: The value to be clamped
 * @param range: minimum and maximum
 * @return The value x clamped to the minimum and maximum given
 */
fun clamp(x: Int, range: IntRange) = when {
    x <= range.first -> range.first
    x >= range.last -> range.last
    else -> x
}

/**
 * @param x: The value to be clamped
 * @return The value x clamped to 0..1
 */
fun clamp(x: Double) =
    clamp(x, 0.0, 1.0)

/**
 * @param x: The value to be clamped
 * @return The value x clamped to 0..1
 */
fun clamp(x: Float) =
    clamp(x, 0.0f, 1.0f)
