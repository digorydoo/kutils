@file:Suppress("unused")

package ch.digorydoo.kutils.math

import kotlin.math.pow
import kotlin.random.Random

/**
 * @param bias Must be 0 ..< 1. A value of 0 will result in a uniform distribution.
 * @return Float within 0 ..< 1, with a bias towards 1.
 */
fun randomFloatBiasedTowardsEnd(bias: Float): Float {
    require(bias in 0.0f ..< 1.0f)
    return 1.0f - (1.0f - Random.nextFloat()).pow(1.0f / (1.0f - bias))
}

/**
 * @param n Upper limit of the range. A negative or zero value will always result in 0.
 * @param bias Must be 0 ..< 1. A value of 0 will result in a uniform distribution.
 * @return Int within 0 ..< n, with a bias towards n.
 */
fun randomIntBiasedTowardsEnd(n: Int, bias: Float): Int = when {
    n <= 0 -> 0
    else -> (n * randomFloatBiasedTowardsEnd(bias)).toInt()
}
