@file:Suppress("unused")

package ch.digorydoo.kutils.math

import ch.digorydoo.kutils.point.MutablePoint3f
import ch.digorydoo.kutils.point.Point3f

/**
 * Linear interpolation or extrapolation
 * @param from
 * @param to
 * @param rel: use [0..1] for interpolation, values outside for extrapolation
 * @return from if rel is 0, to if rel is 1
 */
fun lerp(from: Double, to: Double, rel: Double) =
    (1.0 - rel) * from + rel * to

/**
 * Linear interpolation or extrapolation
 * @param from
 * @param to
 * @param rel: use [0..1] for interpolation, values outside for extrapolation
 * @return from if rel is 0, to if rel is 1
 */
fun lerp(from: Float, to: Float, rel: Float) =
    (1.0f - rel) * from + rel * to

/**
 * Linear interpolation of points
 */
fun lerp(from: Point3f, to: Point3f, rel: Float) =
    MutablePoint3f(
        lerp(from.x, to.x, rel),
        lerp(from.y, to.y, rel),
        lerp(from.z, to.z, rel),
    )
