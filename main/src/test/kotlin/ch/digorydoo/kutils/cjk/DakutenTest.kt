package ch.digorydoo.kutils.cjk

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue

internal class DakutenTest {
    @Test
    fun `should properly handle isDakuten`() {
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
    fun `should properly handle isHandakuten`() {
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
    fun `should properly handle hasDakuten`() {
        assertTrue("abcばdef".hasDakuten())
        assertTrue("abcバdef".hasDakuten())
        assertFalse("abcぱdef".hasDakuten())
        assertFalse("abcパdef".hasDakuten())
    }

    @Test
    fun `should properly handle hasHandakuten`() {
        assertFalse("abcばdef".hasHandakuten())
        assertFalse("abcバdef".hasHandakuten())
        assertTrue("abcぱdef".hasHandakuten())
        assertTrue("abcパdef".hasHandakuten())
    }

    @Test
    fun `should properly handle toDakuten`() {
        // Result is always dakuten, no matter whether base, dakuten or handakuten was given

        assertEquals('ば', 'は'.toDakuten())
        assertEquals('ば', 'ば'.toDakuten())
        assertEquals('ば', 'ぱ'.toDakuten())

        assertEquals('バ', 'ハ'.toDakuten())
        assertEquals('バ', 'バ'.toDakuten())
        assertEquals('バ', 'パ'.toDakuten())
    }

    @Test
    fun `should properly handle toHandakuten`() {
        // Result is always handakuten, no matter whether base, dakuten or handakuten was given

        assertEquals('ぱ', 'は'.toHandakuten())
        assertEquals('ぱ', 'ば'.toHandakuten())
        assertEquals('ぱ', 'ぱ'.toHandakuten())

        assertEquals('パ', 'ハ'.toHandakuten())
        assertEquals('パ', 'バ'.toHandakuten())
        assertEquals('パ', 'パ'.toHandakuten())
    }

    @Test
    fun `should properly handle removeDakutenHandakuten`() {
        assertEquals('は', 'は'.removeDakutenHandakuten())
        assertEquals('は', 'ば'.removeDakutenHandakuten())
        assertEquals('は', 'ぱ'.removeDakutenHandakuten())

        assertEquals('ハ', 'ハ'.removeDakutenHandakuten())
        assertEquals('ハ', 'バ'.removeDakutenHandakuten())
        assertEquals('ハ', 'パ'.removeDakutenHandakuten())
    }

    @Test
    fun `should be able to toggle dakuten for all Hiragana`() {
        for (c in Unicode.HIRAGANA.range) {
            if (c.isDakuten() || c.isHandakuten()) {
                val msg = "Toggling $c"
                val base = c.removeDakutenHandakuten()
                assertNotEquals(c, base, msg)
                assertFalse(base.isDakuten(), msg)
                assertFalse(base.isHandakuten(), msg)
                assertTrue(base.isKana(), msg)
            } else {
                val dakuten = c.toDakuten()
                assertEquals(c, dakuten.removeDakutenHandakuten(), "'$c' to dakuten and remove again")

                val handakuten = c.toHandakuten()
                assertEquals(c, handakuten.removeDakutenHandakuten(), "'$c' to handakuten and remove again")
            }
        }
    }

    @Test
    fun `should be able to toggle dakuten for all Katakana`() {
        for (c in Unicode.KATAKANA.range) {
            if (c.isDakuten() || c.isHandakuten()) {
                val msg = "Toggling $c"
                val base = c.removeDakutenHandakuten()
                assertNotEquals(c, base, msg)
                assertFalse(base.isDakuten(), msg)
                assertFalse(base.isHandakuten(), msg)
                assertTrue(base.isKana(), msg)
            } else {
                val dakuten = c.toDakuten()
                assertEquals(c, dakuten.removeDakutenHandakuten(), "'$c' to dakuten and remove again")

                val handakuten = c.toHandakuten()
                assertEquals(c, handakuten.removeDakutenHandakuten(), "'$c' to handakuten and remove again")
            }
        }
    }

    @Test
    fun `should properly handle toggleDakutenHandakuten`() {
        // When c cannot have dakuten nor handakuten: A -> A
        assertEquals('A', 'A'.toggleDakutenHandakuten())

        // When c cannot have handakuten: た -> だ -> た
        assertEquals('だ', 'た'.toggleDakutenHandakuten())
        assertEquals('た', 'だ'.toggleDakutenHandakuten())

        // When c can have both dakuten and handakuten: ふ -> ぶ -> ぷ -> ふ
        assertEquals('ぶ', 'ふ'.toggleDakutenHandakuten())
        assertEquals('ぷ', 'ぶ'.toggleDakutenHandakuten())
        assertEquals('ふ', 'ぷ'.toggleDakutenHandakuten())
    }

    @Test
    fun `should return the expected results from Char equalsExceptDakutenHandakuten`() {
        // When same char
        assertTrue('a'.equalsExceptDakutenHandakuten('a'))
        assertTrue('う'.equalsExceptDakutenHandakuten('う'))
        assertTrue('ゔ'.equalsExceptDakutenHandakuten('ゔ'))
        assertTrue('カ'.equalsExceptDakutenHandakuten('カ'))
        assertTrue('ガ'.equalsExceptDakutenHandakuten('ガ'))

        // When left is base and right has dakuten
        assertTrue('う'.equalsExceptDakutenHandakuten('ゔ'))
        assertTrue('カ'.equalsExceptDakutenHandakuten('ガ'))

        // When right is base and left has dakuten
        assertTrue('ば'.equalsExceptDakutenHandakuten('は'))
        assertTrue('バ'.equalsExceptDakutenHandakuten('ハ'))

        // When left is base and right has handakuten
        assertTrue('は'.equalsExceptDakutenHandakuten('ぱ'))
        assertTrue('ハ'.equalsExceptDakutenHandakuten('パ'))

        // When right is base and left has handakuten
        assertTrue('ぱ'.equalsExceptDakutenHandakuten('は'))
        assertTrue('パ'.equalsExceptDakutenHandakuten('ハ'))

        // Not when different non-kana char
        assertFalse('a'.equalsExceptDakutenHandakuten('A'))
        assertFalse('子'.equalsExceptDakutenHandakuten('女'))

        // Not when different base
        assertFalse('う'.equalsExceptDakutenHandakuten('え'))
        assertFalse('ば'.equalsExceptDakutenHandakuten('バ'))
        assertFalse('ぽ'.equalsExceptDakutenHandakuten('ポ'))
    }

    @Test
    fun `should return the expected results from String equalsExceptDakutenHandakuten`() {
        // When they're identical
        assertTrue("".equalsExceptDakutenHandakuten(""))
        assertTrue("abcABC".equalsExceptDakutenHandakuten("abcABC"))
        assertTrue("はひふばびぶぱぴぷ".equalsExceptDakutenHandakuten("はひふばびぶぱぴぷ"))
        assertTrue("ハヒフバビブパピプ".equalsExceptDakutenHandakuten("ハヒフバビブパピプ"))

        // When one of them contains dakuten and the other the bases
        assertTrue("ばびぶ".equalsExceptDakutenHandakuten("はひふ"))
        assertTrue("はひふ".equalsExceptDakutenHandakuten("ばびぶ"))

        // When one of them contains handakuten and the other the bases
        assertTrue("ぱぴぷ".equalsExceptDakutenHandakuten("はひふ"))
        assertTrue("はひふ".equalsExceptDakutenHandakuten("ぱぴぷ"))

        // When both use dakuten and handakuten inversely
        assertTrue("はびぷハビプ".equalsExceptDakutenHandakuten("ぱひぶパヒブ"))

        // When one of them is empty
        assertFalse("x".equalsExceptDakutenHandakuten(""))
        assertFalse("".equalsExceptDakutenHandakuten("x"))

        // When they differ in length
        assertFalse("はばぱひ".equalsExceptDakutenHandakuten("はばぱ"))
        assertFalse("はばぱ".equalsExceptDakutenHandakuten("はばぱひ"))
    }
}

