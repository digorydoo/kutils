@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package ch.digorydoo.kutils.cjk

enum class JLPTLevel {
    // Nx means the word is known to not be part of a JLPT words list.
    // null means the JLPT level is unknown.
    N5, N4, N3, N2, N1, Nx;

    override fun toString() = when (this) {
        N5 -> "n5"
        N4 -> "n4"
        N3 -> "n3"
        N2 -> "n2"
        N1 -> "n1"
        Nx -> "-"
    }

    fun toPrettyString() = when (this) {
        N5 -> "JLPT N5"
        N4 -> "JLPT N4"
        N3 -> "JLPT N3"
        N2 -> "JLPT N2"
        N1 -> "JLPT N1"
        Nx -> "Not in JLPT"
    }

    fun toInt() = when (this) {
        N5 -> 5
        N4 -> 4
        N3 -> 3
        N2 -> 2
        N1 -> 1
        Nx -> 0
    }

    fun isEasierThan(other: JLPTLevel) =
        toInt() > other.toInt()

    fun isMoreDifficultThan(other: JLPTLevel) =
        toInt() < other.toInt()

    fun isMoreDifficultThanAllOf(others: Collection<JLPTLevel>) =
        toInt().let { myInt -> others.all { myInt < it.toInt() } }

    companion object {
        fun fromIntOrNull(i: Int) = when (i) {
            5 -> N5
            4 -> N4
            3 -> N3
            2 -> N2
            1 -> N1
            0 -> Nx
            else -> null
        }

        fun Int.toJLPTLevelOrNull() =
            fromIntOrNull(this)

        fun fromInt(i: Int) =
            fromIntOrNull(i) ?: throw Exception("Illegal JLPTLevel int value: $i")

        fun fromString(s: String): JLPTLevel? = when (s) {
            "n5" -> N5
            "n4" -> N4
            "n3" -> N3
            "n2" -> N2
            "n1" -> N1
            "-" -> Nx
            "" -> null
            else -> throw Exception("Illegal JLPTLevel value: $s")
        }

        fun fromStringNotNull(s: String): JLPTLevel =
            fromString(s) ?: throw Exception("JLPT level cannot be null here!")
    }
}
