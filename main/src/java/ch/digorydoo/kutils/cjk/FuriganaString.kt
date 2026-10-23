package ch.digorydoo.kutils.cjk

/**
 * FuriganaStrings are immutable. The raw String is expected to define furigana like this:
 * raw="【思：おも】い【出：だ】す" will be parsed as kanji="思い出す" and kana="おもいだす".
 * Normally, each kanji should have its own furigana group, e.g. raw=【必：ひっ】【死：し】.
 * Sometimes it's necessary to span multiple kanjis, e.g. raw="【今日：きょう】".
 * It's relevant for the kanji rendering whether okurigana is put inside or outside brackets,
 * e.g. raw="【真：ま】っ【白：しろ】" becomes kanji="真っ白", kana="まっしろ";
 * whereas raw="【真：まっ】【白：しろ】" becomes kanji="真白", kana="まっしろ".
 */
class FuriganaString(val raw: String = "") {
    var _kanji: String? = null
    var _kana: String? = null
    var _romaji: String? = null
    var _readings: List<KanjiAndKana>? = null

    val kanji get() = _kanji ?: buildKanji(raw).also { _kanji = it }
    val kana get() = _kana ?: buildKana(raw).also { _kana = it }
    val romaji get() = _romaji ?: RomajiGenerator().generate(raw).also { _romaji = it }
    val readings get() = _readings ?: buildReadings(raw).also { _readings = it }
    val hasFurigana get() = kanji != kana

    fun isEmpty() = raw.isEmpty()
    fun isNotEmpty() = raw.isNotEmpty()
    fun contains(needle: String) = raw.contains(needle)
    override fun toString() = raw

    companion object {
        private fun buildKanji(raw: String): String {
            var result = ""
            FuriganaIterator(raw).forEachPart(
                { kanji, _ -> result += kanji },
                { between -> result += between }
            )
            return result
        }

        private fun buildKana(raw: String): String {
            var result = ""
            FuriganaIterator(raw).forEachPart(
                { _, kana -> result += kana },
                { between -> result += between }
            )
            return result
        }

        private fun buildReadings(raw: String): List<KanjiAndKana> {
            val result = mutableListOf<KanjiAndKana>()
            FuriganaIterator(raw).forEachPart(
                { kanji, kana -> result.add(KanjiAndKana(kanji = kanji.toString(), kana = kana.toString())) },
                { }
            )
            return result
        }
    }
}
