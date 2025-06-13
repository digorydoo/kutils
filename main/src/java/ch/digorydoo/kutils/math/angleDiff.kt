package ch.digorydoo.kutils.math

import kotlin.math.PI

fun angleDiff(phi: Float, rho: Float): Float {
    // val sphi = if (phi < 0.0f) -1.0f else 1.0f
    // val aphi = if (phi < 0.0f) -phi else phi
    // val normPhi = sphi * (((aphi + PI) % (2.0 * PI)) - PI)
    //
    // val srho = if (rho < 0.0f) -1.0f else 1.0f
    // val arho = if (rho < 0.0f) -rho else rho
    // val normRho = srho * (((arho + PI) % (2.0 * PI)) - PI)

    val diff = (rho.toDouble() - phi + PI) % (2.0 * PI)
    return (if (diff < 0) diff + 2 * PI else diff - PI).toFloat()
}
