package ch.digorydoo.kutils.point

@Suppress("unused")
open class Point2i(open val x: Int, open val y: Int) {
    // constructor() doesn't exist -- use Point2i.zero instead!
    constructor(pt: Point2i): this(pt.x, pt.y)

    open operator fun plus(other: Point2i) =
        Point2i(x + other.x, y + other.y)

    open operator fun minus(other: Point2i) =
        Point2i(x - other.x, y - other.y)

    fun hasSameValues(pt: Point2i) =
        x == pt.x && y == pt.y

    fun isZero() =
        x == 0 && y == 0

    fun toMutable() =
        MutablePoint2i(x, y)

    fun toPoint2f() =
        Point2f(x.toFloat(), y.toFloat())

    final override fun toString() =
        "($x, $y)"

    companion object {
        val zero = Point2i(0, 0)
    }
}
