package ch.digorydoo.kutils.point

import ch.digorydoo.kutils.math.max
import kotlin.math.abs

@Suppress("unused", "MemberVisibilityCanBePrivate")
open class Point3i(
    open val x: Int,
    open val y: Int,
    open val z: Int,
) {
    constructor(): this(0, 0, 0)
    constructor(pt: Point3i): this(pt.x, pt.y, pt.z)

    open operator fun plus(other: Point3i) =
        Point3i(x + other.x, y + other.y, z + other.z)

    open operator fun minus(other: Point3i) =
        Point3i(x - other.x, y - other.y, z - other.z)

    fun hasSameValues(pt: Point3i) =
        x == pt.x && y == pt.y && z == pt.z

    fun maxAbsComponent() =
        max(abs(x), abs(y), abs(z))

    fun isZero() =
        x == 0 && y == 0 && z == 0

    fun toXY() =
        Point2i(x, y)

    fun toMutable() =
        MutablePoint3i(x, y, z)

    fun toMutableXY() =
        MutablePoint2i(x, y)

    final override fun toString() =
        "($x, $y, $z)"
}
