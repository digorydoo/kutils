@file:Suppress("unused")

package ch.digorydoo.kutils.waveforms

import kotlin.math.floor

/**
 * Fake sinus function, made up from basic oscillator functions.
 * @param x: unit angle, 1.0 is once around the circle.
 * @return: [-1..+1]
 */
fun fsin(x: Double): Double {
    var m = x - floor(x)
    if (m < 0) m += 1.0
    val saw = if (m < 0.5) 4.0 * m - 1.0 else 4.0 * m - 3.0
    val sqr = if (m < 0.5) 1.0 else -1.0
    return (1.0 - saw * saw) * sqr
}

/**
 * Fake sinus function, made up from basic oscillator functions.
 * @param x: unit angle, 1.0 is once around the circle.
 * @return: [-1..+1]
 */
fun fsin(x: Float): Float =
    fsin(x.toDouble()).toFloat()
