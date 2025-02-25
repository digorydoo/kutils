@file:Suppress("unused")

package ch.digorydoo.kutils.math

/**
 * @param x: The value to be checked against the threshold
 * @param t: The threshold
 * @return Either 0 (when x is below threshold) or 1 (when x >= t)
 */
fun threshold(x: Float, t: Float = 0.5f) =
    if (x < t) 0.0f else 1.0f

/**
 * @param x: The value to be checked against the threshold
 * @param t: The threshold
 * @return Either 0 (when x is below threshold) or 1 (when x >= t)
 */
fun threshold(x: Double, t: Double = 0.5) =
    if (x < t) 0.0 else 1.0
