package ch.digorydoo.kutils.vector

import ch.digorydoo.kutils.math.max
import kotlin.math.abs

@Suppress("unused", "MemberVisibilityCanBePrivate")
open class Vector4f(
    open val x: Float,
    open val y: Float,
    open val z: Float,
    open val w: Float,
) {
    // constructor() with empty arguments intentionally not implemented -- use Vector4f.zero instead!
    constructor(vec: Vector4f): this(vec.x, vec.y, vec.z, vec.w)

    operator fun component1() = x
    operator fun component2() = y
    operator fun component3() = z
    operator fun component4() = w

    open operator fun plus(other: Vector4f) =
        Vector4f(x + other.x, y + other.y, z + other.z, w + other.w)

    open operator fun minus(other: Vector4f) =
        Vector4f(x - other.x, y - other.y, z - other.z, w - other.w)

    open operator fun times(factor: Float) =
        Vector4f(x * factor, y * factor, z * factor, w * factor)

    open operator fun times(factor: Double) =
        Vector4f((x * factor).toFloat(), (y * factor).toFloat(), (z * factor).toFloat(), (w * factor).toFloat())

    open operator fun div(divisor: Float) =
        Vector4f(x / divisor, y / divisor, z / divisor, w / divisor)

    fun newScaled(factor: Float) =
        Vector4f(x * factor, y * factor, z * factor, w * factor)

    fun newScaled(xfactor: Float, yfactor: Float, zfactor: Float, wfactor: Float) =
        Vector4f(x * xfactor, y * yfactor, z * zfactor, w * wfactor)

    fun newScaled(factor: Vector4f) =
        Vector4f(x * factor.x, y * factor.y, z * factor.z, w * factor.w)

    fun hasSameValues(vec: Vector4f) =
        x == vec.x && y == vec.y && z == vec.z && w == vec.w

    fun maxAbsComponent() =
        max(abs(x), abs(y), abs(z), abs(w))

    fun toMutable() =
        MutableVector4f(x, y, z, w)

    final override fun toString() =
        "($x, $y, $z, $w)"

    companion object {
        val zero = Vector4f(0.0f, 0.0f, 0.0f, 0.0f)
    }
}
