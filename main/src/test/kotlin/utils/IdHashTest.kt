package utils

import ch.digorydoo.kutils.utils.IdHash
import kotlin.test.Test
import kotlin.test.assertEquals

internal class IdHashTest {
    @Test
    fun shouldProperlyEncode() {
        val h = IdHash().encode(0xa299b271c876d43fUL)
        assertEquals("xzic5tpgld3k5", h)
    }

    @Test
    fun shouldProperlyDecode() {
        val v = IdHash().decode("xzic5tpgld3k5")
        assertEquals(0xa299b271c876d43fUL, v)
    }
}
