package ch.digorydoo.kutils.rect

import ch.digorydoo.kutils.vector.Vector2f
import ch.digorydoo.kutils.vector.Vector2i

@Suppress("unused")
open class Recti(
    open val left: Int,
    open val top: Int,
    open val right: Int,
    open val bottom: Int,
) {
    constructor(): this(0, 0, 0, 0)
    constructor(l: Float, t: Float, r: Float, b: Float): this(l.toInt(), t.toInt(), r.toInt(), b.toInt())
    constructor(r: Recti): this(r.left, r.top, r.right, r.bottom)

    val origin: Vector2i
        get() = Vector2i(left, top)

    val width: Int
        get() = right - left

    val height: Int
        get() = bottom - top

    val size: Vector2i
        get() = Vector2i(width, height)

    fun centre() =
        Vector2f(
            (left + right) / 2.0f,
            (top + bottom) / 2.0f
        )

    fun hasSameValues(other: Recti) =
        left == other.left && top == other.top && right == other.right && bottom == other.bottom

    fun contains(vec: Vector2i) =
        vec.x >= left && vec.x < right && vec.y >= top && vec.y < bottom

    fun overlaps(r: Recti) =
        (left >= r.left || right > r.left) &&
            (left < r.right || right < r.right) &&
            (top >= r.top || bottom > r.top) &&
            (top < r.bottom || bottom < r.bottom)

    fun newUnreversed(): Recti {
        val newLeft: Int
        val newTop: Int
        val newRight: Int
        val newBottom: Int

        if (left < right) {
            newLeft = left
            newRight = right
        } else {
            newLeft = right
            newRight = left
        }

        if (top < bottom) {
            newTop = top
            newBottom = bottom
        } else {
            newTop = bottom
            newBottom = top
        }

        return Recti(newLeft, newTop, newRight, newBottom)
    }

    fun toMutable() =
        MutableRecti(left, top, right, bottom)

    final override fun toString() =
        "($left, $top)-($right, $bottom)"
}
