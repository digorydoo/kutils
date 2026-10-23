package ch.digorydoo.kutils.cjk

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class SmallKanaTest {
    @Test
    fun `should recognize small kana`() {
        assertTrue('ゃ'.isSmallKana())
        assertTrue('ヶ'.isSmallKana())
        assertTrue('ゖ'.isSmallKana())
        assertTrue('ゎ'.isSmallKana())
        assertTrue('ヮ'.isSmallKana())

        assertFalse('や'.isSmallKana())
        assertFalse('ケ'.isSmallKana())
        assertFalse('け'.isSmallKana())
        assertFalse('わ'.isSmallKana())
        assertFalse('ワ'.isSmallKana())
    }

    @Test
    fun shouldConvertToSmallKana() {
        assertEquals('ゃ', 'や'.toSmallKana())
        assertEquals("ぁぃぅぇぉっゃゅょ", "あいうえおつやゆよ".toSmallKana())
        assertEquals("ァィゥェォッャュョ", "アイウエオツヤユヨ".toSmallKana())
        "blahBLAH123ゃッ".let { assertEquals(it.toSmallKana(), it) }
    }

    @Test
    fun shouldConvertToNormalSizedKana() {
        assertEquals('や', 'ゃ'.toNormalSizedKana())
        assertEquals("あいうえおつやゆよ", "ぁぃぅぇぉっゃゅょ".toNormalSizedKana())
        assertEquals("アイウエオツヤユヨ", "ァィゥェォッャュョ".toNormalSizedKana())
        "blahBLAH123やツ".let { assertEquals(it.toNormalSizedKana(), it) }
    }
}
