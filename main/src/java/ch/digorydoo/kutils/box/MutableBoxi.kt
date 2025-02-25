package ch.digorydoo.kutils.box

@Suppress("unused")
class MutableBoxi(
    theX0: Int,
    theY0: Int,
    theZ0: Int,
    theX1: Int,
    theY1: Int,
    theZ1: Int,
): Boxi() {
    constructor(): this(0, 0, 0, 0, 0, 0)

    constructor(fx0: Float, fy0: Float, fz0: Float, fx1: Float, fy1: Float, fz1: Float):
        this(fx0.toInt(), fy0.toInt(), fz0.toInt(), fx1.toInt(), fy1.toInt(), fz1.toInt())

    constructor(r: Boxi): this(r.x0, r.y0, r.z0, r.x1, r.y1, r.z1)

    override var x0 = theX0
    override var y0 = theY0
    override var z0 = theZ0

    override var x1 = theX1
    override var y1 = theY1
    override var z1 = theZ1

    fun set(newX0: Int, newY0: Int, newZ0: Int, newX1: Int, newY1: Int, newZ1: Int): MutableBoxi {
        x0 = newX0
        y0 = newY0
        z0 = newZ0

        x1 = newX1
        y1 = newY1
        z1 = newZ1

        return this
    }

    fun set(other: Boxi): MutableBoxi {
        x0 = other.x0
        y0 = other.y0
        z0 = other.z0

        x1 = other.x1
        y1 = other.y1
        z1 = other.z1

        return this
    }

    fun offset(dx: Int, dy: Int, dz: Int): MutableBoxi {
        x0 += dx
        y0 += dy
        z0 += dz

        x1 += dx
        y1 += dy
        z1 += dz

        return this
    }

    fun inset(dx: Int, dy: Int, dz: Int): MutableBoxi {
        x0 += dx
        y0 += dy
        z0 += dz

        x1 -= dx
        y1 -= dy
        z1 -= dz

        return this
    }

    fun toImmutable() =
        Boxi(x0, y0, z0, x1, y1, z1)
}
