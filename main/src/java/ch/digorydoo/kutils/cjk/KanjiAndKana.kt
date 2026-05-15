package ch.digorydoo.kutils.cjk

sealed class KanjiAndKanaBase

class OkuriganaOrLiteral(val span: String): KanjiAndKanaBase() {
    override fun toString() = span
}

class KanjiAndKana(val kanji: String, val kana: String): KanjiAndKanaBase() {
    operator fun component1() = kanji
    operator fun component2() = kana
    override fun toString() = "【$kanji：$kana】"
}

fun List<KanjiAndKanaBase>.toCombinedString() =
    joinToString("") { it.toString() }
