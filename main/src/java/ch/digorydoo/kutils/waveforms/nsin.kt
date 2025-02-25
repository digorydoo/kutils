@file:Suppress("unused")

package ch.digorydoo.kutils.waveforms

import kotlin.math.PI
import kotlin.math.sin

/**
 * Normalised sine function.
 * @param x: unit angle, 1.0 is once around the circle.
 * @return: [-1..+1]
 */
fun nsin(x: Double) =
    sin(x * 2.0 * PI)

/**
 * Normalised sine function.
 * @param x: unit angle, 1.0 is once around the circle.
 * @return: [-1..+1]
 */
fun nsin(x: Float) =
    nsin(x.toDouble()).toFloat()
