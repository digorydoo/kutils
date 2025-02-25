@file:Suppress("unused")

package ch.digorydoo.kutils.math

import kotlin.math.abs
import kotlin.math.pow

/**
 * Pow function that keeps the original signum.
 * @param x
 * @param p
 * @return x to the power of p, with the same sign as x
 */
fun spow(x: Float, p: Float) =
    sign(x) * abs(x).pow(p)

/**
 * Pow function that keeps the original signum.
 * @param x
 * @param p
 * @return x to the power of p, with the same sign as x
 */
fun spow(x: Double, p: Double) =
    sign(x) * abs(x).pow(p)
