package ch.digorydoo.kutils.cjk

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertNotNull

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

    @Test
    fun `should drop the furigana over a digit if it's just the same digit in wide chars`() {
        FuriganaAligner().align(
            "私の道具箱の中には金づち一本と、ドライバーが２～３本しか入っていません。",
            "わたしのどうぐばこのなかにはかなづちいっぽんと、ドライバーが2～3ぼんしかはいっていません。"
        ).let {
            assertNotNull(it)
            assertEquals(
                "【私：わたし】の【道具箱：どうぐばこ】の【中：なか】には【金：かな】づち【一本：いっぽん】と、" +
                    "ドライバーが２～３【本：ぼん】しか【入：はい】っていません。",
                it.toCombinedString()
            )
        }
    }

    @Test
    fun `should allow furigana over digit if furigana is not a digit`() {
        FuriganaAligner().align(
            "そのボトルには２リットルのコーラが入っている。",
            "そのボトルにはにリットルのコーラがはいっている。"
        ).let {
            assertNotNull(it)
            assertEquals(
                "そのボトルには【２：に】リットルのコーラが【入：はい】っている。",
                it.toCombinedString()
            )
        }
    }

    @Test
    fun `should correctly handle certain special cases`() {
        FuriganaAligner().align(
            "女性が断崖を登っている。",
            "じょせいがだんがいをのぼっている。"
        ).let {
            assertNotNull(it)
            assertEquals(
                "【女性：じょせい】が【断崖：だんがい】を【登：のぼ】っている。",
                it.toCombinedString()
            )
        }
        FuriganaAligner().align(
            "怖い映画が嫌いです。",
            "こわいえいががきらいです。"
        ).let {
            assertNotNull(it)
            assertEquals(
                "【怖：こわ】い【映画：えいが】が【嫌：きら】いです。",
                it.toCombinedString()
            )
        }
    }
}
