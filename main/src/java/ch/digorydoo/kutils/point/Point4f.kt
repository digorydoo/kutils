package ch.digorydoo.kutils.point

import ch.digorydoo.kutils.math.max
import kotlin.math.abs

@Suppress("unused", "MemberVisibilityCanBePrivate")
open class Point4f(
    open val x: Float,
    open val y: Float,
    open val z: Float,
    open val w: Float,
) {
    // constructor() doesn't exist -- use Point4f.zero instead!
    constructor(pt: Point4f): this(pt.x, pt.y, pt.z, pt.w)

    open operator fun plus(other: Point4f) =
        Point4f(x + other.x, y + other.y, z + other.z, w + other.w)

    open operator fun minus(other: Point4f) =
        Point4f(x - other.x, y - other.y, z - other.z, w - other.w)

    open operator fun times(factor: Float) =
        Point4f(x * factor, y * factor, z * factor, w * factor)

    open operator fun times(factor: Double) =
        Point4f((x * factor).toFloat(), (y * factor).toFloat(), (z * factor).toFloat(), (w * factor).toFloat())

    open operator fun div(divisor: Float) =
        Point4f(x / divisor, y / divisor, z / divisor, w / divisor)

    fun newScaled(factor: Float) =
        Point4f(x * factor, y * factor, z * factor, w * factor)

    fun newScaled(xfactor: Float, yfactor: Float, zfactor: Float, wfactor: Float) =
        Point4f(x * xfactor, y * yfactor, z * zfactor, w * wfactor)

    fun newScaled(factor: Point4f) =
        Point4f(x * factor.x, y * factor.y, z * factor.z, w * factor.w)

    fun hasSameValues(pt: Point4f) =
        x == pt.x && y == pt.y && z == pt.z && w == pt.w

    fun maxAbsComponent() =
        max(abs(x), abs(y), abs(z), abs(w))

    fun toMutable() =
        MutablePoint4f(x, y, z, w)

    final override fun toString() =
        "($x, $y, $z, $w)"

    companion object {
        val zero = Point4f(0.0f, 0.0f, 0.0f, 0.0f)
    }
}
