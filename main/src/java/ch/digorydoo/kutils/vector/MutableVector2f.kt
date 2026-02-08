package ch.digorydoo.kutils.vector

import ch.digorydoo.kutils.math.clamp
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Suppress("unused", "MemberVisibilityCanBePrivate")
class MutableVector2f(x: Float, y: Float): Vector2f(x, y) {
    constructor(): this(0.0f, 0.0f)
    constructor(ix: Int, iy: Int): this(ix.toFloat(), iy.toFloat())
    constructor(vec: Vector2f): this(vec.x, vec.y)

    override var x: Float = x
    override var y: Float = y

    fun set(other: Vector2f): MutableVector2f {
        x = other.x
        y = other.y
        return this
    }

    fun set(theX: Int, theY: Int): MutableVector2f {
        x = theX.toFloat()
        y = theY.toFloat()
        return this
    }

    fun set(theX: Float, theY: Float): MutableVector2f {
        x = theX
        y = theY
        return this
    }

    fun add(vec: Vector2f): MutableVector2f {
        x += vec.x
        y += vec.y
        return this
    }

    fun add(theX: Float, theY: Float): MutableVector2f {
        x += theX
        y += theY
        return this
    }

    fun subtract(vec: Vector2f): MutableVector2f {
        x -= vec.x
        y -= vec.y
        return this
    }

    fun subtract(theX: Float, theY: Float): MutableVector2f {
        x -= theX
        y -= theY
        return this
    }

    fun scale(byX: Float, byY: Float): MutableVector2f {
        x *= byX
        y *= byY
        return this
    }

    fun scale(factor: Float): MutableVector2f {
        x *= factor
        y *= factor
        return this
    }

    fun scale(factor: Vector2f): MutableVector2f {
        x *= factor.x
        y *= factor.y
        return this
    }

    override operator fun plus(other: Vector2f) =
        MutableVector2f(x + other.x, y + other.y)

    override operator fun minus(other: Vector2f) =
        MutableVector2f(x - other.x, y - other.y)

    override operator fun times(factor: Float) =
        MutableVector2f(x * factor, y * factor)

    // operator fun times(factor: Vector2f) dot product...

    override operator fun div(divisor: Float) =
        MutableVector2f(x / divisor, y / divisor)

    operator fun plusAssign(other: Vector2f) {
        x += other.x
        y += other.y
    }

    operator fun minusAssign(other: Vector2f) {
        x -= other.x
        y -= other.y
    }

    operator fun timesAssign(factor: Float) {
        x *= factor
        y *= factor
    }

    operator fun divAssign(divisor: Float) {
        x /= divisor
        y /= divisor
    }

    fun clampTo(left: Float, top: Float, right: Float, bottom: Float): MutableVector2f {
        x = clamp(x, left, right)
        y = clamp(y, top, bottom)
        return this
    }

    fun roundToGrid(xgrid: Int, ygrid: Int) =
        roundToGrid(xgrid.toFloat(), ygrid.toFloat())

    fun roundToGrid(xgrid: Float, ygrid: Float): MutableVector2f {
        x = (x / xgrid).roundToInt() * xgrid
        y = (y / ygrid).roundToInt() * ygrid
        return this
    }

    fun normalize(): MutableVector2f {
        val len = length()

        if (len > 0) {
            x /= len
            y /= len
        } else {
            x = 0.0f
            y = 0.0f
        }

        return this
    }

    fun rotate(alpha: Float) =
        rotate(alpha.toDouble())

    fun rotate(alpha: Double): MutableVector2f {
        val c = cos(alpha)
        val s = sin(alpha)
        val newX = x * c - y * s
        val newY = x * s + y * c
        x = newX.toFloat()
        y = newY.toFloat()
        return this
    }

    fun toImmutable() =
        Vector2f(x, y)
}

