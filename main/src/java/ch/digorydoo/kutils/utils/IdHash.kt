package ch.digorydoo.kutils.utils

/**
 * This is a cryptographically weak hash with a 1:1 relationship between value and hash and may be used to generate
 * string ids from numerical ones.
 */
class IdHash {
    class InvalidHashError: Exception("Invalid hash")

    fun encode(value: ULong): String {
        var x = value xor XOR_VALUE
        val b0 = (x and 0xffUL).toInt(); x = x shr 8
        val b1 = (x and 0xffUL).toInt(); x = x shr 8
        val b2 = (x and 0xffUL).toInt(); x = x shr 8
        val b3 = (x and 0xffUL).toInt(); x = x shr 8
        val b4 = (x and 0xffUL).toInt(); x = x shr 8
        val b5 = (x and 0xffUL).toInt(); x = x shr 8
        val b6 = (x and 0xffUL).toInt(); x = x shr 8
        val b7 = (x and 0xffUL).toInt()

        // @formatter:off
        val c0 = CHARS[((b0      ) and 0x1) or (((b1 shr 5) and 0x1) shl 1) or (((b3 shr 2) and 0x1) shl 2) or (((b4 shr 7) and 0x1) shl 3) or (((b6 shr 4) and 0x1) shl 4)]
        val c1 = CHARS[((b0 shr 1) and 0x1) or (((b1 shr 6) and 0x1) shl 1) or (((b3 shr 3) and 0x1) shl 2) or (((b5      ) and 0x1) shl 3) or (((b6 shr 5) and 0x1) shl 4)]
        val c2 = CHARS[((b0 shr 2) and 0x1) or (((b1 shr 7) and 0x1) shl 1) or (((b3 shr 4) and 0x1) shl 2) or (((b5 shr 1) and 0x1) shl 3) or (((b6 shr 6) and 0x1) shl 4)]
        val c3 = CHARS[((b0 shr 3) and 0x1) or (((b2      ) and 0x1) shl 1) or (((b3 shr 5) and 0x1) shl 2) or (((b5 shr 2) and 0x1) shl 3) or (((b6 shr 7) and 0x1) shl 4)]
        val c4 = CHARS[((b0 shr 4) and 0x1) or (((b2 shr 1) and 0x1) shl 1) or (((b3 shr 6) and 0x1) shl 2) or (((b5 shr 3) and 0x1) shl 3) or (((b7      ) and 0x1) shl 4)]
        val c5 = CHARS[((b0 shr 5) and 0x1) or (((b2 shr 2) and 0x1) shl 1) or (((b3 shr 7) and 0x1) shl 2) or (((b5 shr 4) and 0x1) shl 3) or (((b7 shr 1) and 0x1) shl 4)]
        val c6 = CHARS[((b0 shr 6) and 0x1) or (((b2 shr 3) and 0x1) shl 1) or (((b4      ) and 0x1) shl 2) or (((b5 shr 5) and 0x1) shl 3) or (((b7 shr 2) and 0x1) shl 4)]
        val c7 = CHARS[((b0 shr 7) and 0x1) or (((b2 shr 4) and 0x1) shl 1) or (((b4 shr 1) and 0x1) shl 2) or (((b5 shr 6) and 0x1) shl 3) or (((b7 shr 3) and 0x1) shl 4)]
        val c8 = CHARS[((b1      ) and 0x1) or (((b2 shr 5) and 0x1) shl 1) or (((b4 shr 2) and 0x1) shl 2) or (((b5 shr 7) and 0x1) shl 3) or (((b7 shr 4) and 0x1) shl 4)]
        val c9 = CHARS[((b1 shr 1) and 0x1) or (((b2 shr 6) and 0x1) shl 1) or (((b4 shr 3) and 0x1) shl 2) or (((b6      ) and 0x1) shl 3) or (((b7 shr 5) and 0x1) shl 4)]
        val ca = CHARS[((b1 shr 2) and 0x1) or (((b2 shr 7) and 0x1) shl 1) or (((b4 shr 4) and 0x1) shl 2) or (((b6 shr 1) and 0x1) shl 3) or (((b7 shr 6) and 0x1) shl 4)]
        val cb = CHARS[((b1 shr 3) and 0x1) or (((b3      ) and 0x1) shl 1) or (((b4 shr 5) and 0x1) shl 2) or (((b6 shr 2) and 0x1) shl 3) or (((b7 shr 7) and 0x1) shl 4)]
        val cc = CHARS[((b1 shr 4) and 0x1) or (((b3 shr 1) and 0x1) shl 1) or (((b4 shr 6) and 0x1) shl 2) or (((b6 shr 3) and 0x1) shl 3)]
        // @formatter:on

        return "$c0$c1$c2$c3$c4$c5$c6$c7$c8$c9$ca$cb$cc"
    }

    fun decode(hash: String): ULong {
        if (hash.length != 13) throw InvalidHashError()
        val i0 = indexOf(hash[0])
        val i1 = indexOf(hash[1])
        val i2 = indexOf(hash[2])
        val i3 = indexOf(hash[3])
        val i4 = indexOf(hash[4])
        val i5 = indexOf(hash[5])
        val i6 = indexOf(hash[6])
        val i7 = indexOf(hash[7])
        val i8 = indexOf(hash[8])
        val i9 = indexOf(hash[9])
        val ia = indexOf(hash[10])
        val ib = indexOf(hash[11])
        val ic = indexOf(hash[12])

        // @formatter:off
        val b0 = ((i0      ) and 0x1) or (((i1      ) and 0x1) shl 1) or (((i2      ) and 0x1) shl 2) or (((i3      ) and 0x1) shl 3) or (((i4      ) and 0x1) shl 4) or (((i5      ) and 0x1) shl 5) or (((i6      ) and 0x1) shl 6) or (((i7      ) and 0x1) shl 7)
        val b1 = ((i8      ) and 0x1) or (((i9      ) and 0x1) shl 1) or (((ia      ) and 0x1) shl 2) or (((ib      ) and 0x1) shl 3) or (((ic      ) and 0x1) shl 4) or (((i0 shr 1) and 0x1) shl 5) or (((i1 shr 1) and 0x1) shl 6) or (((i2 shr 1) and 0x1) shl 7)
        val b2 = ((i3 shr 1) and 0x1) or (((i4 shr 1) and 0x1) shl 1) or (((i5 shr 1) and 0x1) shl 2) or (((i6 shr 1) and 0x1) shl 3) or (((i7 shr 1) and 0x1) shl 4) or (((i8 shr 1) and 0x1) shl 5) or (((i9 shr 1) and 0x1) shl 6) or (((ia shr 1) and 0x1) shl 7)
        val b3 = ((ib shr 1) and 0x1) or (((ic shr 1) and 0x1) shl 1) or (((i0 shr 2) and 0x1) shl 2) or (((i1 shr 2) and 0x1) shl 3) or (((i2 shr 2) and 0x1) shl 4) or (((i3 shr 2) and 0x1) shl 5) or (((i4 shr 2) and 0x1) shl 6) or (((i5 shr 2) and 0x1) shl 7)
        val b4 = ((i6 shr 2) and 0x1) or (((i7 shr 2) and 0x1) shl 1) or (((i8 shr 2) and 0x1) shl 2) or (((i9 shr 2) and 0x1) shl 3) or (((ia shr 2) and 0x1) shl 4) or (((ib shr 2) and 0x1) shl 5) or (((ic shr 2) and 0x1) shl 6) or (((i0 shr 3) and 0x1) shl 7)
        val b5 = ((i1 shr 3) and 0x1) or (((i2 shr 3) and 0x1) shl 1) or (((i3 shr 3) and 0x1) shl 2) or (((i4 shr 3) and 0x1) shl 3) or (((i5 shr 3) and 0x1) shl 4) or (((i6 shr 3) and 0x1) shl 5) or (((i7 shr 3) and 0x1) shl 6) or (((i8 shr 3) and 0x1) shl 7)
        val b6 = ((i9 shr 3) and 0x1) or (((ia shr 3) and 0x1) shl 1) or (((ib shr 3) and 0x1) shl 2) or (((ic shr 3) and 0x1) shl 3) or (((i0 shr 4) and 0x1) shl 4) or (((i1 shr 4) and 0x1) shl 5) or (((i2 shr 4) and 0x1) shl 6) or (((i3 shr 4) and 0x1) shl 7)
        val b7 = ((i4 shr 4) and 0x1) or (((i5 shr 4) and 0x1) shl 1) or (((i6 shr 4) and 0x1) shl 2) or (((i7 shr 4) and 0x1) shl 3) or (((i8 shr 4) and 0x1) shl 4) or (((i9 shr 4) and 0x1) shl 5) or (((ia shr 4) and 0x1) shl 6) or (((ib shr 4) and 0x1) shl 7)
        // @formatter:on

        return (b0.toULong() or
            ((b1.toULong()) shl 8) or
            ((b2.toULong()) shl 16) or
            ((b3.toULong()) shl 24) or
            ((b4.toULong()) shl 32) or
            ((b5.toULong()) shl 40) or
            ((b6.toULong()) shl 48) or
            ((b7.toULong()) shl 56)) xor XOR_VALUE
    }

    // Hopefully this is faster than CHARS.indexOf, but it should be the same
    private fun indexOf(c: Char): Int = when (c.code) {
        in CODE_1 .. CODE_6 -> c.code - CODE_1
        in CODE_A .. CODE_Z -> 6 + c.code - CODE_A
        else -> throw InvalidHashError()
    }

    companion object {
        private const val XOR_VALUE: ULong = 0x34a8e5b294f6175cu
        private val CHARS = "123456abcdefghijklmnopqrstuvwxyz".also { require(it.length == 32) }
        private val CODE_A = 'a'.code
        private val CODE_Z = 'z'.code
        private val CODE_1 = '1'.code
        private val CODE_6 = '6'.code
    }
}
