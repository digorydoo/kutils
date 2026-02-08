package ch.digorydoo.kutils.vector

import ch.digorydoo.kutils.math.max
import kotlin.math.abs

@Suppress("unused", "MemberVisibilityCanBePrivate")
open class Vector3i(open val x: Int, open val y: Int, open val z: Int) {
    // constructor() with empty arguments intentionally not implemented -- use Vector3i.zero instead!
    constructor(vec: Vector3i): this(vec.x, vec.y, vec.z)

    operator fun component1() = x
    operator fun component2() = y
    operator fun component3() = z

    open operator fun plus(other: Vector3i) =
        Vector3i(x + other.x, y + other.y, z + other.z)

    open operator fun minus(other: Vector3i) =
        Vector3i(x - other.x, y - other.y, z - other.z)

    fun hasSameValues(vec: Vector3i) =
        x == vec.x && y == vec.y && z == vec.z

    fun maxAbsComponent() =
        max(abs(x), abs(y), abs(z))

    fun isZero() =
        x == 0 && y == 0 && z == 0

    fun toXY() =
        Vector2i(x, y)

    fun toMutable() =
        MutableVector3i(x, y, z)

    fun toMutableXY() =
        MutableVector2i(x, y)

    final override fun toString() =
        "($x, $y, $z)"

    companion object {
        val zero = Vector3i(0, 0, 0)
    }
}
