package ch.digorydoo.kutils.rect

import ch.digorydoo.kutils.point.Point2f

@Suppress("unused", "MemberVisibilityCanBePrivate")
class MutableRectf(
    theLeft: Float,
    theTop: Float,
    theRight: Float,
    theBottom: Float,
): Rectf() {
    constructor(): this(0.0f, 0.0f, 0.0f, 0.0f)
    constructor(l: Int, t: Int, r: Int, b: Int): this(l.toFloat(), t.toFloat(), r.toFloat(), b.toFloat())
    constructor(r: Rectf): this(r.left, r.top, r.right, r.bottom)

    override var left = theLeft
    override var top = theTop
    override var right = theRight
    override var bottom = theBottom

    fun set(l: Float, t: Float, r: Float, b: Float): MutableRectf {
        left = l
        top = t
        right = r
        bottom = b
        return this
    }

    fun set(other: Rectf): MutableRectf {
        left = other.left
        top = other.top
        right = other.right
        bottom = other.bottom
        return this
    }

    fun set(origin: Point2f, size: Point2f): MutableRectf {
        left = origin.x
        top = origin.y
        right = origin.x + size.x
        bottom = origin.y + size.y
        return this
    }

    fun offset(dx: Float, dy: Float): MutableRectf {
        left += dx
        top += dy
        right += dx
        bottom += dy
        return this
    }

    fun scale(factor: Float): MutableRectf {
        // Origin for scaling is the rect's centre.
        val aw = (width * factor).toInt()
        val ah = (height * factor).toInt()
        val cx = ((left + right) / 2.0f).toInt()
        val cy = ((top + bottom) / 2.0f).toInt()
        left = cx - (aw / 2.0f)
        right = left + aw
        top = cy - (ah / 2.0f)
        bottom = top + ah
        return this
    }
}
