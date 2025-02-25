@file:Suppress("unused")

package ch.digorydoo.kutils.waveforms

import ch.digorydoo.kutils.math.lerp
import kotlin.math.floor

/**
 * Pulse function.
 * @param x: unit angle, 1.0 is once around the circle.
 * @param width: pulse width; 0 is a square; [-1..+1]
 * @param smooth: makes the vertical jump less steep; 0 is no smoothing, 1 is a triangle; [0..1]
 * @return: [-1..+1]
 */
fun pulse(x: Double, width: Double = 0.0, smooth: Double = 0.1): Double {
    val m = 2.0 * (x - floor(x)) - 1.0 // -1..+1
    val s = smooth / 2
    return when {
        s <= 0 -> when {
            m < width -> -1.0
            else -> 1.0
        }
        m < -1.0 + s -> lerp(0.0, -1.0, 1.0 - (s - 1.0 - m) / s)
        m < width - s -> -1.0
        m < width + s -> lerp(-1.0, 1.0, 1.0 - (width + s - m) / (s * 2.0))
        m < 1.0 - s -> 1.0
        else -> lerp(1.0, 0.0, -(1.0 - s - m) / s)
    }
}

/**
 * Pulse function.
 * @param x: unit angle, 1.0 is once around the circle.
 * @param width: pulse width; 0 is a square; [-1..+1]
 * @param smooth: makes the vertical jump less steep; 0 is no smoothing, 1 is a triangle; [0..1]
 * @return: [-1..+1]
 */
fun pulse(x: Float, width: Float = 0.0f, smooth: Float = 0.1f) =
    pulse(x.toDouble(), width.toDouble(), smooth.toDouble()).toFloat()
