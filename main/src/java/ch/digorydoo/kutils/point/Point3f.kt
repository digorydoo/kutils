package ch.digorydoo.kutils.point

import ch.digorydoo.kutils.math.max
import kotlin.math.abs
import kotlin.math.sqrt

@Suppress("unused", "MemberVisibilityCanBePrivate")
open class Point3f(
    open val x: Float,
    open val y: Float,
    open val z: Float,
) {
    constructor(): this(0.0f, 0.0f, 0.0f)
    constructor(ix: Int, iy: Int, iz: Int): this(ix.toFloat(), iy.toFloat(), iz.toFloat())
    constructor(pt: Point3f): this(pt.x, pt.y, pt.z)

    operator fun plus(other: Point3f) =
        MutablePoint3f(x + other.x, y + other.y, z + other.z)

    operator fun minus(other: Point3f) =
        MutablePoint3f(x - other.x, y - other.y, z - other.z)

    operator fun times(factor: Float) =
        MutablePoint3f(x * factor, y * factor, z * factor)

    operator fun times(factor: Double) =
        MutablePoint3f((x * factor).toFloat(), (y * factor).toFloat(), (z * factor).toFloat())

    operator fun div(divisor: Float) =
        MutablePoint3f(x / divisor, y / divisor, z / divisor)

    fun newScaled(factor: Float) =
        MutablePoint3f(x * factor, y * factor, z * factor)

    fun newScaled(xfactor: Float, yfactor: Float, zfactor: Float) =
        MutablePoint3f(x * xfactor, y * yfactor, z * zfactor)

    fun newScaled(factor: Point3f) =
        MutablePoint3f(x * factor.x, y * factor.y, z * factor.z)

    fun hasSameValues(pt: Point3f) =
        x == pt.x && y == pt.y && z == pt.z

    fun sqrDistanceTo(pt: Point3f) =
        sqrDistanceTo(pt.x.toDouble(), pt.y.toDouble(), pt.z.toDouble())

    fun sqrDistanceTo(px: Float, py: Float, pz: Float) =
        sqrDistanceTo(px.toDouble(), py.toDouble(), pz.toDouble())

    fun sqrDistanceTo(px: Double, py: Double, pz: Double): Double {
        val dx = x - px
        val dy = y - py
        val dz = z - pz
        return dx * dx + dy * dy + dz * dz
    }

    fun distanceTo(px: Float, py: Float, pz: Float) =
        sqrt(sqrDistanceTo(px, py, pz))

    fun distanceTo(pt: Point3f) =
        sqrt(sqrDistanceTo(pt.x, pt.y, pt.z))

    fun sqrLength(): Double =
        sqrDistanceTo(0.0, 0.0, 0.0)

    fun length() =
        sqrt(sqrDistanceTo(0.0, 0.0, 0.0)).toFloat()

    fun newNormalized(): MutablePoint3f {
        val len = length()
        return MutablePoint3f(x / len, y / len, z / len)
    }

    fun dotProduct(p: Point3f) =
        x * p.x + y * p.y + z * p.z

    fun newCrossed(pt: Point3f) =
        MutablePoint3f(
            y * pt.z - z * pt.y,
            z * pt.x - x * pt.z,
            x * pt.y - y * pt.x
        )

    fun maxAbsComponent() =
        max(abs(x), abs(y), abs(z))

    fun isZero() =
        x == 0.0f && y == 0.0f && z == 0.0f

    fun toMutable() =
        MutablePoint3f(x, y, z)

    fun toXY() =
        Point2f(x, y)

    fun toMutableXY() =
        MutablePoint2f(x, y)

    final override fun toString() =
        "($x, $y, $z)"
}
