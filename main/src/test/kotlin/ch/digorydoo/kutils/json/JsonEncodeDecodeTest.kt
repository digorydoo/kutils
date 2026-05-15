package ch.digorydoo.kutils.json

import kotlin.test.Test
import kotlin.test.assertEquals

internal class JsonEncodeDecodeTest {
    @Test
    fun `should properly encode a given String`() {
        assertEquals("", jsonEncode(""))
        assertEquals("abcABC123,.-", jsonEncode("abcABC123,.-"))
        assertEquals("テレビを見る", jsonEncode("テレビを見る"))
        assertEquals("With 'single' quotes", jsonEncode("With 'single' quotes"))
        assertEquals("With \\\"double\\\" quotes", jsonEncode("With \"double\" quotes"))
        assertEquals("With new\\nline", jsonEncode("With new\nline"))
        assertEquals("With carriage\\rreturn", jsonEncode("With carriage\rreturn"))
        assertEquals("With tab\\tulator", jsonEncode("With tab\tulator"))
    }

    @Test
    fun `should properly decode a given String`() {
        assertEquals("", jsonDecode(""))
        assertEquals("abcABC123,.-", jsonDecode("abcABC123,.-"))
        assertEquals("テレビを見る", jsonDecode("テレビを見る"))
        assertEquals("With 'single' quotes", jsonDecode("With 'single' quotes"))
        assertEquals("With \"double\" quotes", jsonDecode("With \\\"double\\\" quotes"))
        assertEquals("With new\nline", jsonDecode("With new\\nline"))
        assertEquals("With carriage\rreturn", jsonDecode("With carriage\\rreturn"))
        assertEquals("With tab\tulator", jsonDecode("With tab\\tulator"))
    }
}
