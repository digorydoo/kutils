package ch.digorydoo.kutils.vector

@Suppress("unused")
class MutableVector2i(x: Int, y: Int): Vector2i(x, y) {
    constructor(): this(0, 0)
    constructor(vec: Vector2i): this(vec.x, vec.y)

    override var x: Int = x
    override var y: Int = y

    fun set(other: Vector2i): MutableVector2i {
        x = other.x
        y = other.y
        return this
    }

    fun set(theX: Int, theY: Int): MutableVector2i {
        x = theX
        y = theY
        return this
    }

    fun add(vec: Vector2i): MutableVector2i {
        x += vec.x
        y += vec.y
        return this
    }

    fun add(dx: Int, dy: Int): MutableVector2i {
        x += dx
        y += dy
        return this
    }

    fun subtract(vec: Vector2i): MutableVector2i {
        x -= vec.x
        y -= vec.y
        return this
    }

    fun subtract(dx: Int, dy: Int): MutableVector2i {
        x -= dx
        y -= dy
        return this
    }

    override operator fun plus(other: Vector2i) =
        MutableVector2i(x + other.x, y + other.y)

    override operator fun minus(other: Vector2i) =
        MutableVector2i(x - other.x, y - other.y)

    operator fun plusAssign(other: Vector2i) {
        x += other.x
        y += other.y
    }

    operator fun minusAssign(other: Vector2i) {
        x -= other.x
        y -= other.y
    }

    fun toImmutable() =
        Vector2i(x, y)
}

