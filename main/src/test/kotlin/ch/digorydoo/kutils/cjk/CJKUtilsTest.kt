package ch.digorydoo.kutils.cjk

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class CJKUtilsTest {
    @Test
    fun shouldRecognizeCJKChars() {
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
    fun shouldRecognizeHiragana() {
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
    fun shouldRecognizeKatakana() {
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
    fun shouldRecognizeAnyKana() {
        assertTrue("りゅうぐう".hasKana())
        assertTrue("テニス".hasKana())
        assertTrue("飛び出す".hasKana())
    }

    @Test
    fun shouldRecognizeSmallKana() {
        assertTrue('ぁ'.isSmallKana())
        assertFalse("aぁ".isSmallKana())

        assertTrue("aぁ".hasSmallKana())
        assertFalse("aA".hasSmallKana())

        assertTrue("ぁぇぃぉぅァェィォゥ".isSmallKana())
        assertTrue("ゃゅょャュョ".isSmallKana())
        assertTrue("ヵヶっ".isSmallKana())
    }

    @Test
    fun shouldRecognizeOneStrokeKanji() {
        assertTrue('一'.isOneStrokeKanji())
        assertFalse('人'.isOneStrokeKanji())
    }

    @Test
    fun shouldRecognizeTwoStrokeKanji() {
        assertTrue('了'.isTwoStrokeKanji())
        assertFalse('乙'.isTwoStrokeKanji())
    }

    @Test
    fun shouldRecognizePunctuation() {
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
    fun shouldRecognizeBrackets() {
        assertTrue("(){}[]【】〔〕".isBracket())
        assertFalse("「」".isBracket())

        assertTrue("【子】".hasBracket())
        assertFalse("子供".hasBracket())
    }

    @Test
    fun shouldCorrectlyConvertToHiragana() {
        assertEquals("アァカハンヲヴッ".toHiragana(), "あぁかはんをゔっ")
        assertEquals("オ1xお１何イ".toHiragana(), "お1xお１何い")
    }

    @Test
    fun shouldCorrectlyConvertToKatakana() {
        assertEquals("あぁかはんをゔっ".toKatakana(), "アァカハンヲヴッ")
        assertEquals("お1xオ１何い".toKatakana(), "オ1xオ１何イ")
    }
}
