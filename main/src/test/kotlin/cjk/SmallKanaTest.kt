package cjk

import ch.digorydoo.kutils.cjk.isSmallKana
import ch.digorydoo.kutils.cjk.toNormalSizedKana
import ch.digorydoo.kutils.cjk.toSmallKana
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class SmallKanaTest {
    @Test
    fun shouldRecognizeSmallKana() {
        assertTrue('ゃ'.isSmallKana())
        assertTrue('ヶ'.isSmallKana())

        assertFalse('や'.isSmallKana())
        assertFalse('ケ'.isSmallKana())
    }

    @Test
    fun shouldConvertToSmallKana() {
        assertEquals('や'.toSmallKana(), 'ゃ')
        assertEquals("あいうえおつやゆよ".toSmallKana(), "ぁぃぅぇぉっゃゅょ")
        assertEquals("アイウエオツヤユヨ".toSmallKana(), "ァィゥェォッャュョ")
        "blahBLAH123ゃッ".let { assertEquals(it.toSmallKana(), it) }
    }

    @Test
    fun shouldConvertToNormalSizedKana() {
        assertEquals('ゃ'.toNormalSizedKana(), 'や')
        assertEquals("ぁぃぅぇぉっゃゅょ".toNormalSizedKana(), "あいうえおつやゆよ")
        assertEquals("ァィゥェォッャュョ".toNormalSizedKana(), "アイウエオツヤユヨ")
        "blahBLAH123やツ".let { assertEquals(it.toNormalSizedKana(), it) }
    }
}
