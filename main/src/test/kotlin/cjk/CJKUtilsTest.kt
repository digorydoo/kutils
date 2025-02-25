package cjk

import ch.digorydoo.kutils.cjk.*
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class CJKUtilsTest {
    @Test
    fun shouldRecognizeCJKChars() {
        assertTrue("子供".isCJK())
        assertTrue("割引".hasCJK())
        assertTrue("々〜".isCJK())

        assertFalse("子供x".isCJK())
        assertTrue("子供x".hasCJK())

        assertFalse("abcd".isCJK())
        assertFalse("abcd".hasCJK())
        assertFalse("ABCD".isCJK())
        assertFalse("1234".isCJK())
        assertFalse("あいうえお".isCJK())
        assertFalse("アイウエオ".isCJK())
        assertFalse("がぎぐげご".isCJK())
        assertFalse("ガギグゲゴ".isCJK())
        assertFalse("ぁぃぅぇぉ".isCJK())
        assertFalse("ァィゥェォ".isCJK())
        assertFalse("１２３４５".isCJK())
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
