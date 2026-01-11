package ch.digorydoo.kutils.string

import kotlin.test.Test
import kotlin.test.assertEquals

internal class PlainTextFormatterTest {
    @Test
    fun `should return an empty string when an empty string is given`() {
        val f = PlainTextFormatter(maxWidth = 10)
        assertEquals("", f.formatOrNull(""))
    }

    @Test
    fun `should return the word itself if only a word is given that fits into the width`() {
        val f = PlainTextFormatter(maxWidth = 10)
        assertEquals("a", f.formatOrNull("a"))
        assertEquals("Spock", f.formatOrNull("Spock"))
    }

    @Test
    fun `should return the given text if it barely fits into maxWidth`() {
        val f = PlainTextFormatter(maxWidth = 13)
        assertEquals("One two three", f.formatOrNull("One two three"))
    }

    @Test
    fun `should correctly wrap on the next line if the text is just one character longer than maxWidth`() {
        val f = PlainTextFormatter(maxWidth = 12)
        assertEquals("One two\nthree", f.formatOrNull("One two three"))
    }

    @Test
    fun `should treat a single newline as normal whitespace`() {
        val f = PlainTextFormatter(maxWidth = 20)
        assertEquals("One two three", f.formatOrNull("One\ntwo\nthree"))
    }

    @Test
    fun `should treat multiple newlines as a new paragraph`() {
        val f = PlainTextFormatter(maxWidth = 20)
        assertEquals("One\ntwo\nthree", f.formatOrNull("One\n\ntwo\n\n\nthree"))
    }

    @Test
    fun `should ignore leading newlines and superfluous whitespace`() {
        val f = PlainTextFormatter(maxWidth = 20)
        assertEquals("There's a duck", f.formatOrNull("\n \n  \n\n\nThere's     a   duck    "))
    }

    @Test
    fun `should force a break if a word is too long`() {
        val f = PlainTextFormatter(maxWidth = 12)
        assertEquals(
            arrayOf(
                "There is one",
                "ultralengthy",
                "ish word in",
                "this",
                "sentence.",
            ).joinToString("\n"),
            f.formatOrNull("There is one ultralengthyish word in this sentence.")
        )
    }

    @Test
    fun `should break at dash and other word delimiters`() {
        assertEquals("Here is a dash-\nsign", PlainTextFormatter(maxWidth = 17).formatOrNull("Here is a dash-sign"))
        assertEquals("Here is a dash-\nsign", PlainTextFormatter(maxWidth = 15).formatOrNull("Here is a dash-sign"))
        assertEquals("Here is a\ndash-sign", PlainTextFormatter(maxWidth = 14).formatOrNull("Here is a dash-sign"))
        assertEquals(
            "The+same+\napplies+\nfor+plus",
            PlainTextFormatter(maxWidth = 12).formatOrNull("The+same+applies+for+plus")
        )
        assertEquals(
            "The/slash/\nsign/\nbehaves/\nsimilarly",
            PlainTextFormatter(maxWidth = 12).formatOrNull("The/slash/sign/behaves/similarly")
        )
    }

    @Test
    fun `should return null if there is not enough room`() {
        val text = "   One two three four   "

        assertEquals("One two three four", PlainTextFormatter(maxWidth = 20).formatOrNull(text))
        assertEquals(null, PlainTextFormatter(maxWidth = 4).formatOrNull(text))

        // The format() method simply returns the unformatted text instead
        assertEquals("   One two three four   ", PlainTextFormatter(maxWidth = 4).format(text))
    }

    @Test
    fun `should correctly format a longer passage`() {
        val text =
            """
            The official MouseFleet punk band ('The Trashed Registers'
            playing their hit single 'You May Have Misaligned Words, but I
            Have an Offset for You') welcomed bank after bank of delegates
            from here and abroad. Eager journalists from Blazing Computing,
            Amiga Whirl and other such rags jostled each other for the best
            posture behind the barricades. Then there was the frenzied throng
            of groupies, curiosity seekers and autograph hunters hoping for
            the chance to catch a mere glimpse of greatness.
            """.trimIndent()

        assertEquals(
            arrayOf(
                "The official MouseFleet punk",
                "band ('The Trashed",
                "Registers' playing their hit",
                "single 'You May Have",
                "Misaligned Words, but I Have",
                "an Offset for You') welcomed",
                "bank after bank of delegates",
                "from here and abroad. Eager",
                "journalists from Blazing",
                "Computing, Amiga Whirl and",
                "other such rags jostled each",
                "other for the best posture",
                "behind the barricades. Then",
                "there was the frenzied",
                "throng of groupies,",
                "curiosity seekers and",
                "autograph hunters hoping for",
                "the chance to catch a mere",
                "glimpse of greatness.",
            ).joinToString("\n"),
            PlainTextFormatter(maxWidth = 28).formatOrNull(text)
        )
        assertEquals(
            arrayOf(
                "The official MouseFleet punk band",
                "('The Trashed Registers' playing",
                "their hit single 'You May Have",
                "Misaligned Words, but I Have an",
                "Offset for You') welcomed bank after",
                "bank of delegates from here and",
                "abroad. Eager journalists from",
                "Blazing Computing, Amiga Whirl and",
                "other such rags jostled each other",
                "for the best posture behind the",
                "barricades. Then there was the",
                "frenzied throng of groupies,",
                "curiosity seekers and autograph",
                "hunters hoping for the chance to",
                "catch a mere glimpse of greatness.",
            ).joinToString("\n"),
            PlainTextFormatter(maxWidth = 36).formatOrNull(text)
        )
        assertEquals(text, PlainTextFormatter(maxWidth = 65).formatOrNull(text))
        assertEquals(
            arrayOf(
                "The official MouseFleet punk band ('The Trashed Registers' playing their hit",
                "single 'You May Have Misaligned Words, but I Have an Offset for You') welcomed",
                "bank after bank of delegates from here and abroad. Eager journalists from",
                "Blazing Computing, Amiga Whirl and other such rags jostled each other for the",
                "best posture behind the barricades. Then there was the frenzied throng of",
                "groupies, curiosity seekers and autograph hunters hoping for the chance to catch",
                "a mere glimpse of greatness.",
            ).joinToString("\n"),
            PlainTextFormatter(maxWidth = 80).formatOrNull(text)
        )
        assertEquals(
            arrayOf(
                "The official MouseFleet punk band ('The Trashed Registers' playing their hit single 'You May Have",
                "Misaligned Words, but I Have an Offset for You') welcomed bank after bank of delegates from here and",
                "abroad. Eager journalists from Blazing Computing, Amiga Whirl and other such rags jostled each other",
                "for the best posture behind the barricades. Then there was the frenzied throng of groupies,",
                "curiosity seekers and autograph hunters hoping for the chance to catch a mere glimpse of greatness.",
            ).joinToString("\n"),
            PlainTextFormatter(maxWidth = 100).formatOrNull(text)
        )
        assertEquals(
            arrayOf(
                "        The official MouseFleet punk band ('The Trashed Registers' playing their hit single 'You May",
                "        Have Misaligned Words, but I Have an Offset for You') welcomed bank after bank of delegates",
                "        from here and abroad. Eager journalists from Blazing Computing, Amiga Whirl and other such",
                "        rags jostled each other for the best posture behind the barricades. Then there was the",
                "        frenzied throng of groupies, curiosity seekers and autograph hunters hoping for the chance",
                "        to catch a mere glimpse of greatness.",
            ).joinToString("\n"),
            PlainTextFormatter(maxWidth = 100).formatOrNull(text, indent = 8)
        )
    }
}
