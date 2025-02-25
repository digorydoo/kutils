@file:Suppress("unused")

package ch.digorydoo.kutils.waveforms

import kotlin.math.truncate

/**
 * Triangle function.
 * @param x: unit angle, 1.0 is once around the circle.
 * @return: [-1..+1]
 */
fun triang(x: Double): Double {
    var m = x - truncate(x)
    if (m < 0) m += 1.0
    return when {
        m < 0.25 -> 4.0 * m
        m < 0.50 -> 2.0 - 4.0 * m
        m < 0.75 -> 2.0 - 4.0 * m
        else -> 4.0 * m - 4.0
    }
}

/**
 * Triangle function.
 * @param x: unit angle, 1.0 is once around the circle.
 * @return: [-1..+1]
 */
fun triang(x: Float) =
    triang(x.toDouble()).toFloat()
