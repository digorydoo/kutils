package ch.digorydoo.kutils.cjk

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class FuriganaIteratorTest {
    @Test
    fun `should iterate over furigana in text`() {
        val text = "【思：おも】い【出：だ】す"
        val iterator = FuriganaIterator(text)

        assertTrue(iterator.hasNext())
        val first = iterator.next()
        assertEquals("【思：おも】", text.slice(first.range))
        assertEquals("思", first.primaryText.toString())
        assertEquals("おも", first.secondaryText.toString())

        assertTrue(iterator.hasNext())
        val second = iterator.next()
        assertEquals("【出：だ】", text.slice(second.range))
        assertEquals("出", second.primaryText.toString())
        assertEquals("だ", second.secondaryText.toString())

        assertFalse(iterator.hasNext())
    }

    @Test
    fun `should correctly execute forEachPart with furigana and surrounding text`() {
        val text = "【思：おも】い【出：だ】す"
        val iterator = FuriganaIterator(text)
        val notes = mutableListOf<String>()

        iterator.forEachPart(
            furigana = { kanji, kana -> notes.add("furigana($kanji, $kana)") },
            between = { part -> notes.add("between($part)") }
        )

        assertEquals(
            """
            furigana(思, おも)
            between(い)
            furigana(出, だ)
            between(す)
            """.trimIndent(),
            notes.joinToString("\n")
        )
    }

    @Test
    fun `should handle text with no furigana`() {
        val iterator = FuriganaIterator("すき")
        assertFalse(iterator.hasNext())
    }

    @Test
    fun `should handle empty text`() {
        val iterator = FuriganaIterator("")
        assertFalse(iterator.hasNext())
    }

    @Test
    fun `should correctly execute forEachPart with plain text only`() {
        val iterator = FuriganaIterator("hello")
        val notes = mutableListOf<String>()

        iterator.forEachPart(
            furigana = { kanji, kana -> notes.add("furigana($kanji, $kana)") },
            between = { part -> notes.add("between($part)") }
        )

        assertEquals(
            """
            between(hello)
            """.trimIndent(),
            notes.joinToString("\n")
        )
    }

    @Test
    fun `should throw MalformedFuriganaException when primary text contains brackets`() {
        assertFailsWith<FuriganaIterator.MalformedFuriganaException> {
            val iterator = FuriganaIterator("【【大：だい】")
            iterator.hasNext()
        }
    }

    @Test
    fun `should throw MalformedFuriganaException when secondary text contains brackets`() {
        assertFailsWith<FuriganaIterator.MalformedFuriganaException> {
            val iterator = FuriganaIterator("【大：だ【い】")
            iterator.hasNext()
        }
    }

    @Test
    fun `should ignore unclosed or missing separator furigana tags`() {
        // These are actually bugs FIXME should throw

        val iteratorUnclosed = FuriganaIterator("【大：だい")
        assertFalse(iteratorUnclosed.hasNext())

        val iteratorNoSeparator = FuriganaIterator("【大だい】")
        assertFalse(iteratorNoSeparator.hasNext())
    }
}
