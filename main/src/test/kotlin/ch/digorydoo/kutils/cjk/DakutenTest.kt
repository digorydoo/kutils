package ch.digorydoo.kutils.cjk

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class DakutenTest {
    @Test
    fun shouldRecognizeDakuten() {
        assertTrue('が'.isDakuten())
        assertTrue('ガ'.isDakuten())
        assertTrue('ゔ'.isDakuten())
        assertTrue('ヴ'.isDakuten())

        assertFalse('か'.isDakuten())
        assertFalse('カ'.isDakuten())
        assertFalse('ぽ'.isDakuten())
        assertFalse('ポ'.isDakuten())
    }

    @Test
    fun shouldToggleDakuten() {
        assertEquals('は'.toggleDakuten(), 'ば')
        assertEquals('ば'.toggleDakuten(), 'は')
        assertEquals('ハ'.toggleDakuten(), 'バ')
        assertEquals('バ'.toggleDakuten(), 'ハ')
        assertEquals('ゔ'.toggleDakuten(), 'ヴ')
        assertEquals('ヴ'.toggleDakuten(), 'ゔ')
        assertEquals('a'.toggleDakuten(), 'a')
        assertEquals('ぱ'.toggleDakuten(), 'ば') // remove handakuten, add dakuten
    }
}
