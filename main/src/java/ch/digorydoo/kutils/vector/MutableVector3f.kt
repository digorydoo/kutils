package ch.digorydoo.kutils.vector

import ch.digorydoo.kutils.math.clamp
import ch.digorydoo.kutils.math.lerp
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Suppress("unused", "MemberVisibilityCanBePrivate")
class MutableVector3f(x: Float, y: Float, z: Float): Vector3f(x, y, z) {
    constructor(): this(0.0f, 0.0f, 0.0f)
    constructor(ix: Int, iy: Int, iz: Int): this(ix.toFloat(), iy.toFloat(), iz.toFloat())
    constructor(vec: Vector3f): this(vec.x, vec.y, vec.z)

    override var x: Float = x
    override var y: Float = y
    override var z: Float = z

    fun set(other: Vector3f): MutableVector3f {
        x = other.x
        y = other.y
        z = other.z
        return this
    }

    fun set(theX: Int, theY: Int, theZ: Int): MutableVector3f {
        x = theX.toFloat()
        y = theY.toFloat()
        z = theZ.toFloat()
        return this
    }

    fun set(theX: Float, theY: Float, theZ: Float): MutableVector3f {
        x = theX
        y = theY
        z = theZ
        return this
    }

    fun setXY(other: Vector2f): MutableVector3f {
        x = other.x
        y = other.y
        return this
    }

    fun setXY(theX: Int, theY: Int): MutableVector3f {
        x = theX.toFloat()
        y = theY.toFloat()
        return this
    }

    fun setXY(theX: Float, theY: Float): MutableVector3f {
        x = theX
        y = theY
        return this
    }

    fun setLerped(pt1: Vector3f, pt2: Vector3f, rel: Float) {
        x = lerp(pt1.x, pt2.x, rel)
        y = lerp(pt1.y, pt2.y, rel)
        z = lerp(pt1.z, pt2.z, rel)
    }

    fun setToNormal(p0: Vector3f, p1: Vector3f, p2: Vector3f) {
        set(p1.x - p0.x, p1.y - p0.y, p1.z - p0.z).cross(p2.x - p0.x, p2.y - p0.y, p2.z - p0.z).normalize()
    }

    fun add(vec: Vector3f): MutableVector3f {
        x += vec.x
        y += vec.y
        z += vec.z
        return this
    }

    fun add(theX: Float, theY: Float, theZ: Float): MutableVector3f {
        x += theX
        y += theY
        z += theZ
        return this
    }

    fun add(theX: Int, theY: Int, theZ: Int): MutableVector3f {
        x += theX
        y += theY
        z += theZ
        return this
    }

    fun subtract(vec: Vector3f): MutableVector3f {
        x -= vec.x
        y -= vec.y
        z -= vec.z
        return this
    }

    fun subtract(theX: Float, theY: Float, theZ: Float): MutableVector3f {
        x -= theX
        y -= theY
        z -= theZ
        return this
    }

    fun addScaled(vec: Vector3f, factor: Float): MutableVector3f {
        x += vec.x * factor
        y += vec.y * factor
        z += vec.z * factor
        return this
    }

    fun scale(byX: Float, byY: Float, byZ: Float): MutableVector3f {
        x *= byX
        y *= byY
        z *= byZ
        return this
    }

    fun scale(factor: Float): MutableVector3f {
        x *= factor
        y *= factor
        z *= factor
        return this
    }

    fun scale(factor: Vector3f): MutableVector3f {
        x *= factor.x
        y *= factor.y
        z *= factor.z
        return this
    }

    operator fun plusAssign(other: Vector3f) {
        x += other.x
        y += other.y
        z += other.z
    }

    operator fun minusAssign(other: Vector3f) {
        x -= other.x
        y -= other.y
        z -= other.z
    }

    operator fun timesAssign(factor: Float) {
        x *= factor
        y *= factor
        z *= factor
    }

    operator fun divAssign(divisor: Float) {
        x /= divisor
        y /= divisor
        z /= divisor
    }

    fun clampXYTo(left: Float, top: Float, right: Float, bottom: Float): MutableVector3f {
        x = clamp(x, left, right)
        y = clamp(y, top, bottom)
        return this
    }

    fun roundXYToGrid(xgrid: Int, ygrid: Int) =
        roundXYToGrid(xgrid.toFloat(), ygrid.toFloat())

    fun roundXYToGrid(xgrid: Float, ygrid: Float): MutableVector3f {
        x = (x / xgrid).roundToInt() * xgrid
        y = (y / ygrid).roundToInt() * ygrid
        return this
    }

    fun normalize(): MutableVector3f {
        val len = length()

        if (len > 0) {
            x /= len
            y /= len
            z /= len
        } else {
            x = 0.0f
            y = 0.0f
            z = 0.0f
        }

        return this
    }

    fun cross(vec: Vector3f): MutableVector3f {
        val u = y * vec.z - z * vec.y
        val v = z * vec.x - x * vec.z
        val w = x * vec.y - y * vec.x
        x = u
        y = v
        z = w
        return this
    }

    fun cross(x2: Float, y2: Float, z2: Float): MutableVector3f {
        val u = y * z2 - z * y2
        val v = z * x2 - x * z2
        val w = x * y2 - y * x2
        x = u
        y = v
        z = w
        return this
    }

    fun rotateXY(alpha: Float) =
        rotateXY(alpha.toDouble())

    fun rotateXY(alpha: Double): MutableVector3f {
        val c = cos(alpha)
        val s = sin(alpha)
        val newX = x * c - y * s
        val newY = x * s + y * c
        x = newX.toFloat()
        y = newY.toFloat()
        return this
    }

    fun rotateXZ(alpha: Float) =
        rotateXZ(alpha.toDouble())

    fun rotateXZ(alpha: Double): MutableVector3f {
        val c = cos(alpha)
        val s = sin(alpha)
        val newX = x * c - z * s
        val newZ = x * s + z * c
        x = newX.toFloat()
        z = newZ.toFloat()
        return this
    }

    fun rotateYZ(alpha: Float) =
        rotateYZ(alpha.toDouble())

    fun rotateYZ(alpha: Double): MutableVector3f {
        val c = cos(alpha)
        val s = sin(alpha)
        val newY = y * c - z * s
        val newZ = y * s + z * c
        y = newY.toFloat()
        z = newZ.toFloat()
        return this
    }

    fun toImmutable() =
        Vector3f(x, y, z)
}
