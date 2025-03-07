package ch.digorydoo.kutils.point

import ch.digorydoo.kutils.matrix.Matrix4f

@Suppress("unused", "MemberVisibilityCanBePrivate")
class MutablePoint4f(theX: Float, theY: Float, theZ: Float, theW: Float): Point4f(theX, theY, theZ, theW) {
    constructor(): this(0.0f, 0.0f, 0.0f, 0.0f)
    constructor(pt: Point4f): this(pt.x, pt.y, pt.z, pt.w)

    override var x: Float = theX
    override var y: Float = theY
    override var z: Float = theZ
    override var w: Float = theW

    fun set(other: Point4f) {
        x = other.x
        y = other.y
        z = other.z
        w = other.w
    }

    fun set(theX: Float, theY: Float, theZ: Float, theW: Float) {
        x = theX
        y = theY
        z = theZ
        w = theW
    }

    fun set(theX: Double, theY: Double, theZ: Double, theW: Double) {
        x = theX.toFloat()
        y = theY.toFloat()
        z = theZ.toFloat()
        w = theW.toFloat()
    }

    fun setMultiplied(mat: Matrix4f, vec: Point4f) {
        mat.multiplyTo(vec, this)
    }

    fun add(pt: Point4f) {
        x += pt.x
        y += pt.y
        z += pt.z
        w += pt.w
    }

    fun add(theX: Float, theY: Float, theZ: Float, theW: Float) {
        x += theX
        y += theY
        z += theZ
        w += theW
    }

    fun subtract(pt: Point4f) {
        x -= pt.x
        y -= pt.y
        z -= pt.z
        w -= pt.w
    }

    fun subtract(theX: Float, theY: Float, theZ: Float, theW: Float) {
        x -= theX
        y -= theY
        z -= theZ
        w -= theW
    }

    fun scale(byX: Float, byY: Float, byZ: Float, byW: Float) {
        x *= byX
        y *= byY
        z *= byZ
        w *= byW
    }

    fun scale(factor: Float) {
        x *= factor
        y *= factor
        z *= factor
        w *= factor
    }

    fun scale(factor: Point4f) {
        x *= factor.x
        y *= factor.y
        z *= factor.z
        w *= factor.w
    }

    override operator fun plus(other: Point4f) =
        MutablePoint4f(x + other.x, y + other.y, z + other.z, w + other.w)

    override operator fun minus(other: Point4f) =
        MutablePoint4f(x - other.x, y - other.y, z - other.z, w - other.w)

    override operator fun times(factor: Float) =
        MutablePoint4f(x * factor, y * factor, z * factor, w * factor)

    override operator fun div(divisor: Float) =
        MutablePoint4f(x / divisor, y / divisor, z / divisor, w / divisor)

    operator fun plusAssign(other: Point4f) {
        x += other.x
        y += other.y
        z += other.z
        w += other.w
    }

    operator fun minusAssign(other: Point4f) {
        x -= other.x
        y -= other.y
        z -= other.z
        w -= other.w
    }

    operator fun timesAssign(factor: Float) {
        x *= factor
        y *= factor
        z *= factor
        w *= factor
    }

    operator fun divAssign(divisor: Float) {
        x /= divisor
        y /= divisor
        z /= divisor
        w /= divisor
    }

    fun toImmutable() =
        Point4f(x, y, z, w)
}
