package ch.digorydoo.kutils.point

import ch.digorydoo.kutils.math.clamp
import ch.digorydoo.kutils.math.lerp
import kotlin.math.cos
import kotlin.math.roundToInt
import kotlin.math.sin

@Suppress("unused", "MemberVisibilityCanBePrivate")
class MutablePoint3f(theX: Float, theY: Float, theZ: Float): Point3f(theX, theY, theZ) {
    constructor(): this(0.0f, 0.0f, 0.0f)
    constructor(ix: Int, iy: Int, iz: Int): this(ix.toFloat(), iy.toFloat(), iz.toFloat())
    constructor(pt: Point3f): this(pt.x, pt.y, pt.z)

    override var x: Float = theX
    override var y: Float = theY
    override var z: Float = theZ

    fun set(other: Point3f): MutablePoint3f {
        x = other.x
        y = other.y
        z = other.z
        return this
    }

    fun set(theX: Int, theY: Int, theZ: Int): MutablePoint3f {
        x = theX.toFloat()
        y = theY.toFloat()
        z = theZ.toFloat()
        return this
    }

    fun set(theX: Float, theY: Float, theZ: Float): MutablePoint3f {
        x = theX
        y = theY
        z = theZ
        return this
    }

    fun setXY(other: Point2f): MutablePoint3f {
        x = other.x
        y = other.y
        return this
    }

    fun setXY(theX: Int, theY: Int): MutablePoint3f {
        x = theX.toFloat()
        y = theY.toFloat()
        return this
    }

    fun setXY(theX: Float, theY: Float): MutablePoint3f {
        x = theX
        y = theY
        return this
    }

    fun setLerped(pt1: Point3f, pt2: Point3f, rel: Float) {
        x = lerp(pt1.x, pt2.x, rel)
        y = lerp(pt1.y, pt2.y, rel)
        z = lerp(pt1.z, pt2.z, rel)
    }

    fun add(pt: Point3f): MutablePoint3f {
        x += pt.x
        y += pt.y
        z += pt.z
        return this
    }

    fun add(theX: Float, theY: Float, theZ: Float): MutablePoint3f {
        x += theX
        y += theY
        z += theZ
        return this
    }

    fun add(theX: Int, theY: Int, theZ: Int): MutablePoint3f {
        x += theX
        y += theY
        z += theZ
        return this
    }

    fun subtract(pt: Point3f): MutablePoint3f {
        x -= pt.x
        y -= pt.y
        z -= pt.z
        return this
    }

    fun subtract(theX: Float, theY: Float, theZ: Float): MutablePoint3f {
        x -= theX
        y -= theY
        z -= theZ
        return this
    }

    fun addScaled(pt: Point3f, factor: Float): MutablePoint3f {
        x += pt.x * factor
        y += pt.y * factor
        z += pt.z * factor
        return this
    }

    fun scale(byX: Float, byY: Float, byZ: Float): MutablePoint3f {
        x *= byX
        y *= byY
        z *= byZ
        return this
    }

    fun scale(factor: Float): MutablePoint3f {
        x *= factor
        y *= factor
        z *= factor
        return this
    }

    fun scale(factor: Point3f): MutablePoint3f {
        x *= factor.x
        y *= factor.y
        z *= factor.z
        return this
    }

    operator fun plusAssign(other: Point3f) {
        x += other.x
        y += other.y
        z += other.z
    }

    operator fun minusAssign(other: Point3f) {
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

    fun clampXYTo(left: Float, top: Float, right: Float, bottom: Float): MutablePoint3f {
        x = clamp(x, left, right)
        y = clamp(y, top, bottom)
        return this
    }

    fun roundXYToGrid(xgrid: Int, ygrid: Int) =
        roundXYToGrid(xgrid.toFloat(), ygrid.toFloat())

    fun roundXYToGrid(xgrid: Float, ygrid: Float): MutablePoint3f {
        x = (x / xgrid).roundToInt() * xgrid
        y = (y / ygrid).roundToInt() * ygrid
        return this
    }

    fun normalize(): MutablePoint3f {
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

    fun cross(pt: Point3f): MutablePoint3f {
        val u = y * pt.z - z * pt.y
        val v = z * pt.x - x * pt.z
        val w = x * pt.y - y * pt.x
        x = u
        y = v
        z = w
        return this
    }

    fun rotateXY(alpha: Float) =
        rotateXY(alpha.toDouble())

    fun rotateXY(alpha: Double): MutablePoint3f {
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

    fun rotateXZ(alpha: Double): MutablePoint3f {
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

    fun rotateYZ(alpha: Double): MutablePoint3f {
        val c = cos(alpha)
        val s = sin(alpha)
        val newY = y * c - z * s
        val newZ = y * s + z * c
        y = newY.toFloat()
        z = newZ.toFloat()
        return this
    }

    fun toImmutable() =
        Point3f(x, y, z)
}
