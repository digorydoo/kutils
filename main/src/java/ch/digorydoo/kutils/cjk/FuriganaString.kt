@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package ch.digorydoo.kutils.cjk

// FuriganaStrings are immutable
class FuriganaString(val raw: String = "") {
    var _kanji: String? = null
    var _kana: String? = null
    var _romaji: String? = null
    var _readings: List<KanjiAndKana>? = null

    val kanji get() = _kanji ?: buildKanji(raw).also { _kanji = it }
    val kana get() = _kana ?: buildKana(raw).also { _kana = it }
    val romaji get() = _romaji ?: RomajiBuilder().build(raw).also { _romaji = it }
    val readings get() = _readings ?: buildReadings(raw).also { _readings = it }

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
