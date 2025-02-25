@file:Suppress("unused")

package ch.digorydoo.kutils.math

import kotlin.math.PI

/**
 * Normalizes an angle into the range -PI..+PI.
 * @param angle: angle in Radians
 * @return A value within -PI..+PI
 */
fun normAngle(angle: Float): Float {
    val s = if (angle < 0.0f) -1.0f else 1.0f
    val a = if (angle < 0.0f) -angle else angle
    return s * (((a + PI) % (2.0 * PI)) - PI).toFloat()
}

/**
 * Normalizes an angle into the range -PI..+PI.
 * @param angle: angle in Radians
 * @return A value within -PI..+PI
 */
fun normAngle(angle: Double): Double {
    val s = if (angle < 0.0) -1.0 else 1.0
    val a = if (angle < 0.0) -angle else angle
    return s * (((a + PI) % (2.0 * PI)) - PI)
}
