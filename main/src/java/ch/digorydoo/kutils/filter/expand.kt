@file:Suppress("unused")

package ch.digorydoo.kutils.filter

import ch.digorydoo.kutils.math.sign
import kotlin.math.abs
import kotlin.math.pow

/**
 * Pushes the waveform outwards towards the boundaries -1..+1.
 * @param x: [-1..+1]
 * @param c: 0=linear, 1=quadratic compression
 * @return [-1..+1]
 */
fun expand(x: Float, c: Float) =
    sign(x) * (1.0f - (1.0f - abs(x)).pow(c + 1.0f))

/**
 * Pushes the waveform outwards towards the boundaries -1..+1.
 * @param x: [-1..+1]
 * @param c: 0=linear, 1=quadratic compression
 * @return [-1..+1]
 */
fun expand(x: Double, c: Double) =
    sign(x) * (1.0 - (1.0 - abs(x)).pow(c + 1.0))
