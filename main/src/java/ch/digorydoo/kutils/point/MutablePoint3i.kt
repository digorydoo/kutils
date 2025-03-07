package ch.digorydoo.kutils.point

@Suppress("unused", "MemberVisibilityCanBePrivate")
class MutablePoint3i(theX: Int, theY: Int, theZ: Int): Point3i(theX, theY, theZ) {
    constructor(): this(0, 0, 0)
    constructor(pt: Point3i): this(pt.x, pt.y, pt.z)

    override var x: Int = theX
    override var y: Int = theY
    override var z: Int = theZ

    fun set(other: Point3i): MutablePoint3i {
        x = other.x
        y = other.y
        z = other.z
        return this
    }

    fun set(theX: Int, theY: Int, theZ: Int): MutablePoint3i {
        x = theX
        y = theY
        z = theZ
        return this
    }

    fun setXY(other: Point2i): MutablePoint3i {
        x = other.x
        y = other.y
        return this
    }

    fun setXY(theX: Int, theY: Int): MutablePoint3i {
        x = theX
        y = theY
        return this
    }

    fun add(pt: Point3i): MutablePoint3i {
        x += pt.x
        y += pt.y
        z += pt.z
        return this
    }

    fun add(theX: Int, theY: Int, theZ: Int): MutablePoint3i {
        x += theX
        y += theY
        z += theZ
        return this
    }

    fun subtract(pt: Point3i): MutablePoint3i {
        x -= pt.x
        y -= pt.y
        z -= pt.z
        return this
    }

    fun subtract(theX: Int, theY: Int, theZ: Int): MutablePoint3i {
        x -= theX
        y -= theY
        z -= theZ
        return this
    }

    override operator fun plus(other: Point3i) =
        MutablePoint3i(x + other.x, y + other.y, z + other.z)

    override operator fun minus(other: Point3i) =
        MutablePoint3i(x - other.x, y - other.y, z - other.z)

    operator fun plusAssign(other: Point3i) {
        x += other.x
        y += other.y
        z += other.z
    }

    operator fun minusAssign(other: Point3i) {
        x -= other.x
        y -= other.y
        z -= other.z
    }

    fun toImmutable() =
        Point3i(x, y, z)
}
