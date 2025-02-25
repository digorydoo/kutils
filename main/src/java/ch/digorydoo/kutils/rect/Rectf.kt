package ch.digorydoo.kutils.rect

import ch.digorydoo.kutils.point.Point2f

@Suppress("unused", "MemberVisibilityCanBePrivate")
open class Rectf(
    open val left: Float,
    open val top: Float,
    open val right: Float,
    open val bottom: Float,
) {
    constructor(): this(0.0f, 0.0f, 0.0f, 0.0f)
    constructor(l: Int, t: Int, r: Int, b: Int): this(l.toFloat(), t.toFloat(), r.toFloat(), b.toFloat())
    constructor(r: Rectf): this(r.left, r.top, r.right, r.bottom)

    val origin: Point2f
        get() = Point2f(left, top)

    val width: Float
        get() = right - left

    val height: Float
        get() = bottom - top

    val size: Point2f
        get() = Point2f(width, height)

    fun centre() =
        Point2f(
            (left + right) / 2.0f,
            (top + bottom) / 2.0f
        )

    fun hasSameValues(other: Rectf) =
        left == other.left && top == other.top && right == other.right && bottom == other.bottom

    fun contains(pt: Point2f) =
        pt.x >= left && pt.x < right && pt.y >= top && pt.y < bottom

    fun overlaps(r: Rectf) =
        (left >= r.left || right > r.left) &&
            (left < r.right || right < r.right) &&
            (top >= r.top || bottom > r.top) &&
            (top < r.bottom || bottom < r.bottom)

    final override fun toString() =
        "($left, $top)-($right, $bottom)"
}
