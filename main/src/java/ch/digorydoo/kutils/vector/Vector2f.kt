package ch.digorydoo.kutils.vector

import kotlin.math.abs
import kotlin.math.max
import kotlin.math.sqrt

@Suppress("unused", "MemberVisibilityCanBePrivate")
open class Vector2f(open val x: Float, open val y: Float) {
    // constructor() with empty arguments intentionally not implemented -- use Vector2f.zero instead!
    constructor(ix: Int, iy: Int): this(ix.toFloat(), iy.toFloat())
    constructor(vec: Vector2f): this(vec.x, vec.y)

    operator fun component1() = x
    operator fun component2() = y

    open operator fun plus(other: Vector2f) =
        Vector2f(x + other.x, y + other.y)

    open operator fun minus(other: Vector2f) =
        Vector2f(x - other.x, y - other.y)

    open operator fun times(factor: Float) =
        Vector2f(x * factor, y * factor)

    // operator fun times(factor: Vector2f) dot product...

    open operator fun div(divisor: Float) =
        Vector2f(x / divisor, y / divisor)

    fun newScaled(factor: Float) =
        Vector2f(x * factor, y * factor)

    fun newScaled(xfactor: Float, yfactor: Float) =
        Vector2f(x * xfactor, y * yfactor)

    fun newScaled(factor: Vector2f) =
        Vector2f(x * factor.x, y * factor.y)

    fun hasSameValues(vec: Vector2f) =
        x == vec.x && y == vec.y

    fun sqrDistanceTo(px: Float, py: Float) =
        sqrDistanceTo(px.toDouble(), py.toDouble())

    fun sqrDistanceTo(px: Double, py: Double): Double {
        val dx = x - px
        val dy = y - py
        return dx * dx + dy * dy
    }

    fun distanceTo(px: Float, py: Float) =
        sqrt(sqrDistanceTo(px, py))

    fun distanceTo(vec: Vector2f) =
        sqrt(sqrDistanceTo(vec.x, vec.y))

    fun distanceTo(vec: Vector2i) =
        sqrt(sqrDistanceTo(vec.x.toFloat(), vec.y.toFloat()))

    fun length() =
        sqrt(sqrDistanceTo(0.0, 0.0)).toFloat()

    fun newNormalized(): Vector2f {
        val len = length()
        return Vector2f(x / len, y / len)
    }

    fun dotProduct(vec: Vector2f) =
        x * vec.x + y * vec.y

    fun maxAbsComponent() =
        max(abs(x), abs(y))

    fun isZero() =
        x == 0.0f && y == 0.0f

    fun toMutable() =
        MutableVector2f(x, y)

    fun toVector2i() =
        Vector2i(x.toInt(), y.toInt())

    final override fun toString() =
        "($x, $y)"

    companion object {
        val zero = Vector2f(0, 0)
        val point01 = Vector2f(0, 1)
        val point10 = Vector2f(1, 0)
        val point11 = Vector2f(1, 1)
    }
}

