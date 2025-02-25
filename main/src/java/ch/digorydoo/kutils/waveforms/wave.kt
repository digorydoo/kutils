@file:Suppress("unused")

package ch.digorydoo.kutils.waveforms

import ch.digorydoo.kutils.math.lerp

/**
 * @param t: unit angle, 1.0 is once around the circle.
 * @param shape: 0 is sine, 1 adds a lot of mid-range frequencies
 * @return: [-1..+1]
 */
fun wave1(t: Float, shape: Float = 0.5f) =
    nsin(t + shape * nsin(t)) * lerp(1.0f, 0.5f, shape)

/**
 * @param t: unit angle, 1.0 is once around the circle.
 * @param shape: 0 is triangle, 1 adds mid-range and high frequencies
 * @return: [-1..+1]
 */
fun wave2(t: Float, shape: Float = 0.5f) =
    triang(t + shape * nsin(t + 0.25f + shape * nsin(t - shape) - shape) - shape) * lerp(1.0f, 0.5f, shape)

/**
 * @param t: unit angle, 1.0 is once around the circle.
 * @param shape: 0 is sine, higher values make the sound harsh or mid-range
 * @return: [-1..+1]
 */
fun wave3(t: Float, shape: Float = 0.75f) =
    nsin(t + shape * pulse(t + shape * pulse(t), 1.0f - shape)) * lerp(1.0f, 0.3f, shape)
