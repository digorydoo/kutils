package ch.digorydoo.kutils.vector

@Suppress("unused")
open class Vector2i(open val x: Int, open val y: Int) {
    // constructor() with empty arguments intentionally not implemented -- use Vector2i.zero instead!
    constructor(vec: Vector2i): this(vec.x, vec.y)

    operator fun component1() = x
    operator fun component2() = y

    open operator fun plus(other: Vector2i) =
        Vector2i(x + other.x, y + other.y)

    open operator fun minus(other: Vector2i) =
        Vector2i(x - other.x, y - other.y)

    fun hasSameValues(vec: Vector2i) =
        x == vec.x && y == vec.y

    fun isZero() =
        x == 0 && y == 0

    fun toMutable() =
        MutableVector2i(x, y)

    fun toVector2f() =
        Vector2f(x.toFloat(), y.toFloat())

    final override fun toString() =
        "($x, $y)"

    companion object {
        val zero = Vector2i(0, 0)
    }
}
