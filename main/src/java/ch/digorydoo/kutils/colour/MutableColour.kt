package ch.digorydoo.kutils.colour

import ch.digorydoo.kutils.math.lerp

@Suppress("unused")
class MutableColour(
    theRed: Float,
    theGreen: Float,
    theBlue: Float,
    theAlpha: Float,
): Colour() {
    constructor(): this(0.0f, 0.0f, 0.0f, 1.0f)
    constructor(r: Float, g: Float, b: Float): this(r, g, b, 1.0f)
    constructor(other: Colour): this(other.red, other.green, other.blue, other.alpha)

    fun set(other: Colour): MutableColour {
        red = other.red
        green = other.green
        blue = other.blue
        alpha = other.alpha
        return this
    }

    fun set(r: Float, g: Float, b: Float, a: Float): MutableColour {
        red = r
        green = g
        blue = b
        alpha = a
        return this
    }

    fun set(r: Byte, g: Byte, b: Byte, a: Byte): MutableColour {
        red = (r.toUInt() and 255u).toFloat() / 255.0f
        green = (g.toUInt() and 255u).toFloat() / 255.0f
        blue = (b.toUInt() and 255u).toFloat() / 255.0f
        alpha = (a.toUInt() and 255u).toFloat() / 255.0f
        return this
    }

    override var red: Float = theRed
    override var green: Float = theGreen
    override var blue: Float = theBlue
    override var alpha: Float = theAlpha

    override fun toARGB(): Int {
        cachedARGB = null // may have changed
        return super.toARGB()
    }

    fun toImmutable() =
        Colour(red, green, blue, alpha)

    fun mix(other: Colour, rel: Float) {
        red = lerp(red, other.red, rel)
        green = lerp(green, other.green, rel)
        blue = lerp(blue, other.blue, rel)
        alpha = lerp(alpha, other.alpha, rel)
    }

    fun setMixed(c1: Colour, c2: Colour, rel: Float) {
        red = lerp(c1.red, c2.red, rel)
        green = lerp(c1.green, c2.green, rel)
        blue = lerp(c1.blue, c2.blue, rel)
        alpha = lerp(c1.alpha, c2.alpha, rel)
    }

    override fun newMixed(other: Colour, rel: Float) =
        MutableColour(
            lerp(red, other.red, rel),
            lerp(green, other.green, rel),
            lerp(blue, other.blue, rel),
            lerp(alpha, other.alpha, rel),
        )

    override fun newRGBMultiplied(factor: Float) =
        MutableColour(red * factor, green * factor, blue * factor, alpha)

    companion object {
        fun fromBytes(r: Byte, g: Byte, b: Byte, a: Byte) =
            MutableColour().set(r, g, b, a)

        fun fromString(s: String) =
            Colour.fromString(s).toMutable()
    }
}
