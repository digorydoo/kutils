@file:Suppress("unused")

package ch.digorydoo.kutils.filter

import ch.digorydoo.kutils.math.lerp
import kotlin.math.pow

/**
 * @param x: must be in [0..1]
 * @param pre: must be in [0..1]
 * @param attack: must be in [0..1], pre <= attack
 * @param decay: must be in [0..1], attack <= decay
 * @param sustain: must be in [0..1]
 * @param release: must be in [0..1], decay <= release
 * @param post: must be in [0..1], release <= post
 * @param acurve: 0>..+x, curve of attack, 1=linear
 * @param rcurve: 0>..+x, curve of release, 1=linear
 * @return A value in [0..1]
 */
fun envelope(
    x: Float,
    pre: Float = 0.0f,
    attack: Float = 0.1f,
    decay: Float = attack,
    sustain: Float = 0.8f,
    release: Float = 0.9f,
    post: Float = 1.0f,
    acurve: Float = 1.0f,
    rcurve: Float = 1.0f,
) = when {
    x < pre -> 0.0f
    x < attack -> ((x - pre) / (attack - pre)).pow(acurve)
    x < decay -> lerp(1.0f, sustain, (x - attack) / (decay - attack))
    x < release -> sustain
    x < post -> lerp(0.0f, sustain, (1.0f - (x - release) / (post - release)).pow(rcurve))
    else -> 0.0f
}

/**
 * Stays at 0 until x reaches pre, then goes linearly up to 1 until x reaches post,
 * then stays at 1.
 * @param x: must be in [0..1]
 * @param pre: must be in [0..1]
 * @param post: must be in [0..1]
 * @return A value in [0..1]
 */
fun delay(x: Double, pre: Double, post: Double = 1.0) = when {
    x <= pre -> 0.0
    x >= post -> 1.0
    else -> (x - pre) / (post - pre)
}

/**
 * Stays at 0 until x reaches pre, then goes linearly up to 1 until x reaches post,
 * then stays at 1.
 * @param x: must be in [0..1]
 * @param pre: must be in [0..1]
 * @param post: must be in [0..1]
 * @return A value in [0..1]
 */
fun delay(x: Float, pre: Float, post: Float = 1.0f) =
    delay(x.toDouble(), pre.toDouble(), post.toDouble()).toFloat()
