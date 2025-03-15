package ch.digorydoo.kutils.cjk

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class HandakutenTest {
    @Test
    fun shouldRecognizeHandakuten() {
        assertTrue('ぽ'.isHandakuten())
        assertTrue('ポ'.isHandakuten())

        assertFalse('が'.isHandakuten())
        assertFalse('ガ'.isHandakuten())
        assertFalse('ゔ'.isHandakuten())
        assertFalse('ヴ'.isHandakuten())
        assertFalse('か'.isHandakuten())
        assertFalse('カ'.isHandakuten())
    }

    @Test
    fun shouldToggleHandakuten() {
        assertEquals('ば'.toggleHandakuten(), 'ぱ') // remove dakuten, add handakuten

        val s = "ハヘヒホフはへひほふあアa"
        val t = s.map { it.toggleHandakuten() }.joinToString("")
        assertEquals(t, "パペピポプぱぺぴぽぷあアa")
    }
}
