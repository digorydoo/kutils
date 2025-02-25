@file:Suppress("unused")

package ch.digorydoo.kutils.waveforms

import ch.digorydoo.kutils.math.clamp
import ch.digorydoo.kutils.math.lerp
import ch.digorydoo.kutils.math.sign
import kotlin.math.floor
import kotlin.math.sin

/**
 * Noise function.
 * @param t
 * @return: [-1..+1]
 */
fun noise(t: Double): Double {
    var a = 9943758.51 * sin(3.01745 * floor(t * 4.0) + 0.04)
    a = 2.0 * (a - floor(a) - 0.5)
    val b = 43758.5453 * sin(6.28297 * a)
    return (b - floor(b)) * sign(b)
}

/**
 * Noise function.
 * @param t
 * @return: [-1..+1]
 */
fun noise(t: Float): Float =
    noise(t.toDouble()).toFloat()

/**
 * Alternate noise function.
 * @param t
 * @param colour: [0..1]
 * @return: [-1..+1]
 */
fun noise2(t: Double, colour: Double = 0.5): Double {
    val n1 = noise(t)
    val n2 = noise(t + 0.01 + 0.5 * colour)
    return clamp(n1 + n2, -1.0, 1.0)
}

/**
 * Alternate noise function.
 * @param t
 * @param colour: [0..1]
 * @return: [-1..+1]
 */
fun noise2(t: Float, colour: Float = 0.5f) =
    noise2(t.toDouble(), colour.toDouble()).toFloat()

/**
 * @param t
 * @param amount: 0 >.. 1.0f
 * @return: [-1..+1]
 */
fun crackle(t: Float, amount: Float = 0.2f) =
    if (noise(t) > 1.0f - amount) pulse(t * 0.5f, 0.9f) else 0.0f

/**
 * ASIC salad function.
 * @param t
 * @param colour: [0..1]
 * @return: [-1..+1]
 */
fun bitnoise(t: Double, colour: Double = 1.0): Double {
    val f1 = 220.0 + 420.0 * colour
    val f2 = 4200.0 + 2200.0 * colour
    val n = 0.5 + 0.5 * noise(t * 8.0)
    return fsin(t * lerp(f1, f2, n))
}

/**
 * ASIC salad function.
 * @param t
 * @param colour: [0..1]
 * @return: [-1..+1]
 */
fun bitnoise(t: Float, colour: Float = 1.0f) =
    bitnoise(t.toDouble(), colour.toDouble()).toFloat()
