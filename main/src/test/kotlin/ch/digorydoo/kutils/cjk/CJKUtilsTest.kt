package ch.digorydoo.kutils.cjk

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class CJKUtilsTest {
    @Test
    fun `should recognize cjk chars`() {
        assertTrue("子供".isCJKNotKana())
        assertTrue("々〜".isCJKNotKana())
        assertFalse("広さ".isCJKNotKana())
        assertFalse("子供x".isCJKNotKana())
        assertFalse("abcd".isCJKNotKana())
        assertFalse("ABCD".isCJKNotKana())
        assertFalse("1234".isCJKNotKana())
        assertFalse("あいうえお".isCJKNotKana())
        assertFalse("アイウエオ".isCJKNotKana())
        assertFalse("がぎぐげご".isCJKNotKana())
        assertFalse("ガギグゲゴ".isCJKNotKana())
        assertFalse("ぁぃぅぇぉ".isCJKNotKana())
        assertFalse("ァィゥェォ".isCJKNotKana())
        assertFalse("１２３４５".isCJKNotKana())

        assertTrue("好き".isCJKOrKana())
        assertTrue("私".isCJKOrKana())
        assertTrue("そう".isCJKOrKana())
        assertFalse("4個".isCJKOrKana())

        assertTrue("割引".hasCJKIgnoringKana())
        assertTrue("多くの".hasCJKIgnoringKana())
        assertTrue("子供x".hasCJKIgnoringKana())
        assertFalse("abcd".hasCJKIgnoringKana())
    }

    @Test
    fun `should recognize hiragana`() {
        assertTrue('か'.isHiragana())
        assertTrue('が'.isHiragana())
        assertTrue('ぁ'.isHiragana())
        assertTrue("けげぇ".isHiragana())

        assertTrue("アあ".hasHiragana())
        assertFalse("aA".hasHiragana())

        assertFalse('ア'.isHiragana())
        assertFalse('a'.isHiragana())
        assertFalse('〜'.isHiragana())
        assertFalse('１'.isHiragana())
    }

    @Test
    fun `should recognize katakana`() {
        assertTrue('カ'.isKatakana())
        assertTrue('ガ'.isKatakana())
        assertTrue('ァ'.isKatakana())
        assertTrue("ケゲヶ".isKatakana())

        assertTrue("アあ".hasKatakana())
        assertFalse("aA".hasKatakana())

        assertFalse('あ'.isKatakana())
        assertFalse('A'.isKatakana())
        assertFalse('々'.isKatakana())
        assertFalse('２'.isKatakana())
    }

    @Test
    fun `should recognize any kana`() {
        assertTrue('あ'.isKana())
        assertTrue('ア'.isKana())
        assertTrue('ゅ'.isKana())
        assertTrue('ュ'.isKana())

        assertTrue("いい".isKana())
        assertTrue("イイ".isKana())
        assertTrue("にゃ".isKana())
        assertTrue("ニャ".isKana())

        assertFalse('a'.isKana())
        assertFalse('私'.isKana())
        assertFalse('？'.isKana())
        assertFalse('０'.isKana())

        assertFalse("あ・いうえお".isKana())
        assertFalse("私は".isKana())

        assertTrue("りゅうぐう".hasKana())
        assertTrue("テニス".hasKana())
        assertTrue("飛び出す".hasKana())
    }

    @Test
    fun `should recognize small kana`() {
        assertTrue('ぁ'.isSmallKana())
        assertFalse("aぁ".isSmallKana())

        assertTrue("aぁ".hasSmallKana())
        assertFalse("aA".hasSmallKana())

        assertTrue("ぁぇぃぉぅァェィォゥ".isSmallKana())
        assertTrue("ゃゅょャュョ".isSmallKana())
        assertTrue("ヵヶっ".isSmallKana())
    }

    @Test
    fun `should recognize one-stroke kanji`() {
        assertTrue('一'.isOneStrokeKanji())
        assertFalse('人'.isOneStrokeKanji())
    }

    @Test
    fun `should recognize two-stroke kanji`() {
        assertTrue('了'.isTwoStrokeKanji())
        assertFalse('乙'.isTwoStrokeKanji())
    }

    @Test
    fun `should recognize punctuation`() {
        assertTrue("。、：；？！".isPunctuation())
        assertTrue(".,:;?!".isPunctuation())

        assertTrue("何？".hasPunctuation())
        assertFalse("何も".hasPunctuation())

        assertFalse('・'.isPunctuation())
        assertFalse('…'.isPunctuation())
        assertFalse('('.isPunctuation())
        assertFalse('（'.isPunctuation())
        assertFalse('【'.isPunctuation())
        assertFalse('「'.isPunctuation())
    }

    @Test
    fun `should recognize brackets`() {
        assertTrue("(){}[]【】〔〕".isBracket())
        assertFalse("「」".isBracket())

        assertTrue("【子】".hasBracket())
        assertFalse("子供".hasBracket())
    }

    @Test
    fun `shoud correctly convert to hiragana`() {
        assertEquals("アァカハンヲヴッ".toHiragana(), "あぁかはんをゔっ")
        assertEquals("オ1xお１何イ".toHiragana(), "お1xお１何い")
    }

    @Test
    fun `should correctly convert to katakana`() {
        assertEquals("あぁかはんをゔっ".toKatakana(), "アァカハンヲヴッ")
        assertEquals("お1xオ１何い".toKatakana(), "オ1xオ１何イ")
    }

    @Test
    fun `should recognize when digits are the same`() {
        // normal width vs. normal width
        assertTrue(isSameDigit('1', '1'), "@1")
        assertTrue(isSameDigit('9', '9'), "@2")
        assertFalse(isSameDigit('1', '9'), "@3")

        // wide width vs. wide width
        assertTrue(isSameDigit('１', '１'), "@4")
        assertTrue(isSameDigit('９', '９'), "@5")
        assertFalse(isSameDigit('１', '９'), "@6")

        // normal width vs. wide width
        assertTrue(isSameDigit('1', '１'), "@7")
        assertTrue(isSameDigit('9', '９'), "@8")
        assertFalse(isSameDigit('1', '９'), "@9")

        // wide width vs. normal width
        assertTrue(isSameDigit('１', '1'), "@10")
        assertTrue(isSameDigit('９', '9'), "@11")
        assertFalse(isSameDigit('１', '9'), "@12")
    }
}
