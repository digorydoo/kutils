@file:Suppress("unused")

package ch.digorydoo.kutils.box

import ch.digorydoo.kutils.point.MutablePoint3f
import ch.digorydoo.kutils.point.Point3f
import ch.digorydoo.kutils.point.Point3i
import kotlin.math.max

open class Boxi(
    open val x0: Int,
    open val y0: Int,
    open val z0: Int,
    open val x1: Int,
    open val y1: Int,
    open val z1: Int,
) {
    constructor(): this(0, 0, 0, 0, 0, 0)

    constructor(fx0: Float, fy0: Float, fz0: Float, fx1: Float, fy1: Float, fz1: Float):
        this(fx0.toInt(), fy0.toInt(), fz0.toInt(), fx1.toInt(), fy1.toInt(), fz1.toInt())

    constructor(r: Boxi): this(r.x0, r.y0, r.z0, r.x1, r.y1, r.z1)

    val xsize get() = x1 - x0
    val ysize get() = y1 - y0
    val zsize get() = z1 - z0

    fun centre() =
        Point3f(
            (x0 + x1) / 2.0f,
            (y0 + y1) / 2.0f,
            (z0 + z1) / 2.0f,
        )

    fun centrei() =
        Point3i(
            ((x0 + x1) / 2.0f).toInt(),
            ((y0 + y1) / 2.0f).toInt(),
            ((z0 + z1) / 2.0f).toInt(),
        )

    fun hasSameValues(other: Boxi) =
        x0 == other.x0 && y0 == other.y0 && z0 == other.z0 &&
            x1 == other.x1 && y1 == other.y1 && z1 == other.z1

    fun contains(pt: Point3f) =
        pt.x >= x0 && pt.x < x1 &&
            pt.y >= y0 && pt.y < y1 &&
            pt.z >= z0 && pt.z < z1

    fun overlaps(r: Boxi) =
        (x0 >= r.x0 || x1 > r.x0) &&
            (x0 < r.x1 || x1 < r.x1) &&
            (y0 >= r.y0 || y1 > r.y0) &&
            (y0 < r.y1 || y1 < r.y1) &&
            (z0 >= r.z0 || z1 > r.z0) &&
            (z0 < r.z1 || z1 < r.z1)

    fun distanceFrom(pt: Point3f): Float {
        val xinside = pt.x >= x0 && pt.x < x1
        val yinside = pt.y >= y0 && pt.y < y1
        val zinside = pt.z >= z0 && pt.z < z1

        if (xinside && yinside && zinside) return 0.0f

        if (xinside && yinside) {
            val zout1 = z0 - pt.z
            val zout2 = pt.z - z1
            return max(zout1, zout2)
        }

        if (xinside && zinside) {
            val yout1 = y0 - pt.y
            val yout2 = pt.y - y1
            return max(yout1, yout2)
        }

        if (yinside && zinside) {
            val xout1 = x0 - pt.x
            val xout2 = pt.x - x1
            return max(xout1, xout2)
        }

        val tmp = MutablePoint3f()
        val darr = arrayOf(
            tmp.set(x0, y0, z0).distanceTo(pt),
            tmp.set(x0, y0, z1).distanceTo(pt),
            tmp.set(x0, y1, z0).distanceTo(pt),
            tmp.set(x0, y1, z1).distanceTo(pt),
            tmp.set(x1, y0, z0).distanceTo(pt),
            tmp.set(x1, y0, z1).distanceTo(pt),
            tmp.set(x1, y1, z0).distanceTo(pt),
            tmp.set(x1, y1, z1).distanceTo(pt),
        )
        return (darr.minOrNull() ?: 0.0).toFloat()
    }

    fun newUnreversed(): Boxi {
        val newX0: Int
        val newY0: Int
        val newZ0: Int
        val newX1: Int
        val newY1: Int
        val newZ1: Int

        if (x0 < x1) {
            newX0 = x0
            newX1 = x1
        } else {
            newX0 = x1
            newX1 = x0
        }

        if (y0 < y1) {
            newY0 = y0
            newY1 = y1
        } else {
            newY0 = y1
            newY1 = y0
        }

        if (z0 < z1) {
            newZ0 = z0
            newZ1 = z1
        } else {
            newZ0 = z1
            newZ1 = z0
        }

        return Boxi(newX0, newY0, newZ0, newX1, newY1, newZ1)
    }

    fun toMutable() =
        MutableBoxi(x0, y0, z0, x1, y1, z1)

    final override fun toString() =
        "($x0, $y0, $z0)-($x1, $y1, $z1)"
}
