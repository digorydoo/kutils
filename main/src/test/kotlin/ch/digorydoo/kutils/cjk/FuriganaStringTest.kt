package ch.digorydoo.kutils.cjk

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class FuriganaStringTest {
    @Test
    fun shouldProperlyConstructEmpty() {
        val mt = FuriganaString()
        assertEquals(mt.kanji, "")
        assertEquals(mt.kana, "")
        assertEquals(mt.romaji, "")
        assertTrue(mt.readings.isEmpty())
        assertTrue(mt.isEmpty())
        assertFalse(mt.isNotEmpty())
        assertEquals(mt.toString(), "")
    }

    @Test
    fun shouldProperlyParseFurigana() {
        val fs = FuriganaString("【差：さ】し【出：だ】す")
        assertEquals(fs.kanji, "差し出す")
        assertEquals(fs.kana, "さしだす")
        assertEquals(fs.romaji, "sashidasu")

        val readings = listOf(KanjiAndKana("差", "さ"), KanjiAndKana("出", "だ"))
        assertTrue(fs.readings.all { kak -> readings.any { kak.kana == it.kana && kak.kanji == it.kanji } })
        assertTrue(readings.all { kak -> fs.readings.any { kak.kana == it.kana && kak.kanji == it.kanji } })

        assertFalse(fs.isEmpty())
        assertTrue(fs.isNotEmpty())
        assertEquals(fs.toString(), "【差：さ】し【出：だ】す")
    }
}
