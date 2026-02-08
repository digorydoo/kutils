package ch.digorydoo.kutils.vector

@Suppress("unused", "MemberVisibilityCanBePrivate")
class MutableVector3i(x: Int, y: Int, z: Int): Vector3i(x, y, z) {
    constructor(): this(0, 0, 0)
    constructor(vec: Vector3i): this(vec.x, vec.y, vec.z)

    override var x: Int = x
    override var y: Int = y
    override var z: Int = z

    fun set(other: Vector3i): MutableVector3i {
        x = other.x
        y = other.y
        z = other.z
        return this
    }

    fun set(theX: Int, theY: Int, theZ: Int): MutableVector3i {
        x = theX
        y = theY
        z = theZ
        return this
    }

    fun setXY(other: Vector2i): MutableVector3i {
        x = other.x
        y = other.y
        return this
    }

    fun setXY(theX: Int, theY: Int): MutableVector3i {
        x = theX
        y = theY
        return this
    }

    fun add(vec: Vector3i): MutableVector3i {
        x += vec.x
        y += vec.y
        z += vec.z
        return this
    }

    fun add(theX: Int, theY: Int, theZ: Int): MutableVector3i {
        x += theX
        y += theY
        z += theZ
        return this
    }

    fun subtract(vec: Vector3i): MutableVector3i {
        x -= vec.x
        y -= vec.y
        z -= vec.z
        return this
    }

    fun subtract(theX: Int, theY: Int, theZ: Int): MutableVector3i {
        x -= theX
        y -= theY
        z -= theZ
        return this
    }

    override operator fun plus(other: Vector3i) =
        MutableVector3i(x + other.x, y + other.y, z + other.z)

    override operator fun minus(other: Vector3i) =
        MutableVector3i(x - other.x, y - other.y, z - other.z)

    operator fun plusAssign(other: Vector3i) {
        x += other.x
        y += other.y
        z += other.z
    }

    operator fun minusAssign(other: Vector3i) {
        x -= other.x
        y -= other.y
        z -= other.z
    }

    fun toImmutable() =
        Vector3i(x, y, z)
}
