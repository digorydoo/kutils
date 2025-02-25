@file:Suppress("unused")

package ch.digorydoo.kutils.math

/**
 * Convex function. As k approaches infinity, convex(x,k) gets more and more linear.
 * @param x: must be in [0..1]
 * @param k: must be (1..oo)
 * @return A value in [0..1]
 */
fun accel(x: Double, k: Double) =
    k * (k - 1.0) / (k - x) + 1.0 - k

/**
 * Convex function. As k approaches infinity, convex(x,k) gets more and more linear.
 * @param x: must be in [0..1]
 * @param k: must be (1..oo)
 * @return A value in [0..1]
 */
fun accel(x: Float, k: Float) =
    accel(x.toDouble(), k.toDouble()).toFloat()

/**
 * Concave function. As k approaches infinity, concave(x,k) gets more and more linear.
 * @param x: must be in [0..1]
 * @param k: must be (1..oo)
 * @return A value in [0..1]
 */
fun decel(x: Double, k: Double) =
    k - k * (k - 1.0) / (k - (1.0 - x))

/**
 * Concave function. As k approaches infinity, concave(x,k) gets more and more linear.
 * @param x: must be in [0..1]
 * @param k: must be (1..oo)
 * @return A value in [0..1]
 */
fun decel(x: Float, k: Float) =
    decel(x.toDouble(), k.toDouble()).toFloat()
