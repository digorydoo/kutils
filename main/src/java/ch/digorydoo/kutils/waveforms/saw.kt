@file:Suppress("unused")

package ch.digorydoo.kutils.waveforms

import ch.digorydoo.kutils.math.lerp
import kotlin.math.truncate

/**
 * Upwards sawtooth starting at 0.
 * @param x: unit angle, 1.0 is once around the circle
 * @return: [-1..+1]
 */
fun saw(x: Double): Double =
    if (x < 0.0) {
        -saw(-x)
    } else {
        2.0 * ((x + 0.5) - truncate(x + 0.5)) - 1.0
    }

/**
 * Upwards sawtooth starting at 0.
 * @param x: unit angle, 1.0 is once around the circle
 * @param filter: 0 is unfiltered, 1 is sine
 * @return: [-1..+1]
 */
fun saw(x: Double, filter: Double) =
    lerp(saw(x), nsin(x), filter)

/**
 * Upwards sawtooth starting at 0.
 * @param x: unit angle, 1.0f is once around the circle
 * @return: [-1..+1]
 */
fun saw(x: Float): Float =
    if (x < 0.0f) {
        -saw(-x)
    } else {
        2.0f * ((x + 0.5f) - truncate(x + 0.5f)) - 1.0f
    }

/**
 * Upwards sawtooth starting at 0.
 * @param x: unit angle, 1.0f is once around the circle
 * @param filter: 0 is unfiltered, 1 is sine
 * @return: [-1..+1]
 */
fun saw(x: Float, filter: Float) =
    lerp(saw(x), nsin(x), filter)
