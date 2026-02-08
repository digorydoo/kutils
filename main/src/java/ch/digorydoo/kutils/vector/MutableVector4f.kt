package ch.digorydoo.kutils.vector

import ch.digorydoo.kutils.matrix.Matrix4f

@Suppress("unused", "MemberVisibilityCanBePrivate")
class MutableVector4f(x: Float, y: Float, z: Float, w: Float): Vector4f(x, y, z, w) {
    constructor(): this(0.0f, 0.0f, 0.0f, 0.0f)
    constructor(vec: Vector4f): this(vec.x, vec.y, vec.z, vec.w)

    override var x: Float = x
    override var y: Float = y
    override var z: Float = z
    override var w: Float = w

    fun set(other: Vector4f) {
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

    fun setMultiplied(mat: Matrix4f, vec: Vector4f) {
        mat.multiplyTo(vec, this)
    }

    fun add(vec: Vector4f) {
        x += vec.x
        y += vec.y
        z += vec.z
        w += vec.w
    }

    fun add(theX: Float, theY: Float, theZ: Float, theW: Float) {
        x += theX
        y += theY
        z += theZ
        w += theW
    }

    fun subtract(vec: Vector4f) {
        x -= vec.x
        y -= vec.y
        z -= vec.z
        w -= vec.w
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

    fun scale(factor: Vector4f) {
        x *= factor.x
        y *= factor.y
        z *= factor.z
        w *= factor.w
    }

    override operator fun plus(other: Vector4f) =
        MutableVector4f(x + other.x, y + other.y, z + other.z, w + other.w)

    override operator fun minus(other: Vector4f) =
        MutableVector4f(x - other.x, y - other.y, z - other.z, w - other.w)

    override operator fun times(factor: Float) =
        MutableVector4f(x * factor, y * factor, z * factor, w * factor)

    override operator fun div(divisor: Float) =
        MutableVector4f(x / divisor, y / divisor, z / divisor, w / divisor)

    operator fun plusAssign(other: Vector4f) {
        x += other.x
        y += other.y
        z += other.z
        w += other.w
    }

    operator fun minusAssign(other: Vector4f) {
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
        Vector4f(x, y, z, w)
}
