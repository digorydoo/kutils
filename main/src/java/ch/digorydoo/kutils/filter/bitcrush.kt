@file:Suppress("unused")

package ch.digorydoo.kutils.filter

import kotlin.math.floor

/**
 * Reduces the bit depth of the amplitude.
 * @param a: [-1..+1]
 * @param steps: number of steps, use 256 for 8bit
 * @return: [-1..+1]
 */
fun bitcrush(a: Double, steps: Int = 256) =
    floor(a * (0.5 + steps / 2.0)) / steps * 2.0

/**
 * Reduces the bit depth of the amplitude.
 * @param a: [-1..+1]
 * @param steps: number of steps, use 256 for 8bit
 * @return: [-1..+1]
 */
fun bitcrush(a: Float, steps: Int = 256) =
    bitcrush(a.toDouble(), steps).toFloat()
