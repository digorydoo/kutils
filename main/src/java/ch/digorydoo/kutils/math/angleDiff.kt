package ch.digorydoo.kutils.math

import kotlin.math.IEEErem
import kotlin.math.PI

fun angleDiff(phi: Double, rho: Double): Double =
    (rho - phi).IEEErem(2 * PI)

fun angleDiff(phi: Float, rho: Float): Float =
    angleDiff(phi.toDouble(), rho.toDouble()).toFloat()
