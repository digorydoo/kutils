package ch.digorydoo.kutils.cjk

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs

internal class FuriganaAlignerTest {
    @Test
    fun `should return an empty list if both primary and secondary are empty`() {
        val result = FuriganaAligner().align("", "")
        assertEquals(0, result?.size ?: -1)
    }

    @Test
    fun `should return a list of a single literal span if both primary and secondary are the same kana`() {
        val result = FuriganaAligner().align("テレビをみる", "テレビをみる")
        assertEquals(1, result?.size ?: -1)
        val part = result!![0]
        assertIs<OkuriganaOrLiteral>(part, "should be OkuriganaOrLiteral")
        assertEquals("テレビをみる", part.span)
    }

    @Test
    fun `should return a list of a single literal span if both primary and secondary are the non-kana string`() {
        val result = FuriganaAligner().align("abcABCあいうアイウ私、123１２３äöü{}", "abcABCあいうアイウ私、123１２３äöü{}")
        assertEquals(1, result?.size ?: -1)
        val part = result!![0]
        assertIs<OkuriganaOrLiteral>(part, "should be OkuriganaOrLiteral")
        assertEquals("abcABCあいうアイウ私、123１２３äöü{}", part.span)
    }

    @Test
    fun `should correctly align an example sentence`() {
        val result = FuriganaAligner().align("写真を見て笑う", "しゃしんをみてわらう")
        assertEquals(6, result?.size ?: -1)
        val joined = result!!.joinToString("・")
        assertEquals("【写真：しゃしん】・を・【見：み】・て・【笑：わら】・う", joined)
    }

    @Test
    fun `should return null if alignment is not possible`() {
        val result = FuriganaAligner().align("写真を見て笑った", "しゃしんをみてわらう")
        assertEquals(null, result)
    }
}
