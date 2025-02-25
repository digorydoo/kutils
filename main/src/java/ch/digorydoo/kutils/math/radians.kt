@file:Suppress("unused")

package ch.digorydoo.kutils.math

import kotlin.math.PI

private const val RADIANS_TO_DEGREES_FACTOR: Double = 360.0 / (2.0 * PI)
private const val RADIANS_TO_DEGREES_FACTOR_FLOAT: Float = RADIANS_TO_DEGREES_FACTOR.toFloat()

fun Double.toDegrees(): Double = this * RADIANS_TO_DEGREES_FACTOR
fun Double.toRadians(): Double = this / RADIANS_TO_DEGREES_FACTOR
fun Float.toDegrees(): Float = this * RADIANS_TO_DEGREES_FACTOR_FLOAT
fun Float.toRadians(): Float = this / RADIANS_TO_DEGREES_FACTOR_FLOAT
