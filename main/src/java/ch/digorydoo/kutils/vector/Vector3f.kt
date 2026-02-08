package ch.digorydoo.kutils.vector

import ch.digorydoo.kutils.math.max
import kotlin.math.abs
import kotlin.math.sqrt

@Suppress("unused", "MemberVisibilityCanBePrivate")
open class Vector3f(
    open val x: Float,
    open val y: Float,
    open val z: Float,
) {
    // constructor() with empty arguments intentionally not implemented -- use Vector3f.zero instead!
    constructor(ix: Int, iy: Int, iz: Int): this(ix.toFloat(), iy.toFloat(), iz.toFloat())
    constructor(vec: Vector3f): this(vec.x, vec.y, vec.z)

    operator fun component1() = x
    operator fun component2() = y
    operator fun component3() = z

    operator fun plus(other: Vector3f) =
        MutableVector3f(x + other.x, y + other.y, z + other.z)

    operator fun minus(other: Vector3f) =
        MutableVector3f(x - other.x, y - other.y, z - other.z)

    operator fun times(factor: Float) =
        MutableVector3f(x * factor, y * factor, z * factor)

    operator fun times(factor: Double) =
        MutableVector3f((x * factor).toFloat(), (y * factor).toFloat(), (z * factor).toFloat())

    operator fun div(divisor: Float) =
        MutableVector3f(x / divisor, y / divisor, z / divisor)

    fun newScaled(factor: Float) =
        MutableVector3f(x * factor, y * factor, z * factor)

    fun newScaled(xfactor: Float, yfactor: Float, zfactor: Float) =
        MutableVector3f(x * xfactor, y * yfactor, z * zfactor)

    fun newScaled(factor: Vector3f) =
        MutableVector3f(x * factor.x, y * factor.y, z * factor.z)

    fun hasSameValues(vec: Vector3f) =
        x == vec.x && y == vec.y && z == vec.z

    fun sqrDistanceTo(vec: Vector3f) =
        sqrDistanceTo(vec.x.toDouble(), vec.y.toDouble(), vec.z.toDouble())

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

    fun distanceTo(vec: Vector3f) =
        sqrt(sqrDistanceTo(vec.x, vec.y, vec.z))

    fun sqrLength(): Double =
        sqrDistanceTo(0.0, 0.0, 0.0)

    fun length() =
        sqrt(sqrDistanceTo(0.0, 0.0, 0.0)).toFloat()

    fun newNormalized(): MutableVector3f {
        val len = length()
        return MutableVector3f(x / len, y / len, z / len)
    }

    fun dotProduct(vec: Vector3f) =
        x * vec.x + y * vec.y + z * vec.z

    fun newCrossed(vec: Vector3f) =
        MutableVector3f(
            y * vec.z - z * vec.y,
            z * vec.x - x * vec.z,
            x * vec.y - y * vec.x
        )

    fun maxAbsComponent() =
        max(abs(x), abs(y), abs(z))

    fun isZero() =
        x == 0.0f && y == 0.0f && z == 0.0f

    fun toMutable() =
        MutableVector3f(x, y, z)

    fun toXY() =
        Vector2f(x, y)

    fun toMutableXY() =
        MutableVector2f(x, y)

    final override fun toString() =
        "($x, $y, $z)"

    companion object {
        val zero = Vector3f(0.0f, 0.0f, 0.0f)
    }
}
