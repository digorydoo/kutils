package ch.digorydoo.kutils.string

import kotlin.test.Test
import kotlin.test.assertEquals

internal class IndexOfAnyExceptTest {
    @Test
    fun `should return minus one if input is empty`() {
        val idx1 = "".indexOfAnyExcept(arrayOf<Char>().toCharArray())
        assertEquals(-1, idx1)

        val idx2 = "".indexOfAnyExcept(arrayOf('a').toCharArray())
        assertEquals(-1, idx2)
    }

    @Test
    fun `should return minus one if all characters are from exception`() {
        val idx = "hello".indexOfAnyExcept(arrayOf('h', 'l', 'o', 'e').toCharArray())
        assertEquals(-1, idx)
    }

    @Test
    fun `should return zero if none of the characters are excepted`() {
        val idx1 = "hello".indexOfAnyExcept(arrayOf<Char>().toCharArray())
        assertEquals(0, idx1)

        val idx2 = "hello".indexOfAnyExcept(arrayOf('g', 'f', 'm', 'p').toCharArray())
        assertEquals(0, idx2)
    }

    @Test
    fun `should return the expected index if some characters are excepted`() {
        val idx = "The quick brown fox jumped over the lazy dog.".indexOfAnyExcept(
            arrayOf('h', 'e', ' ', 'q', 'T', 'x', 'y', 'z').toCharArray()
        )
        assertEquals(5, idx)
    }

    @Test
    fun `should return minus one if the start index is beyond the string length`() {
        val idx = "Ans zwa".indexOfAnyExcept(arrayOf('z').toCharArray(), 24)
        assertEquals(-1, idx)
    }

    @Test
    fun `should return the expected index if the start index is before the occurrence`() {
        val idx = "Ans zwa".indexOfAnyExcept(arrayOf('n', 'A').toCharArray(), 2)
        assertEquals(2, idx)
    }

    @Test
    fun `should return the expected index if the start index is between occurrences`() {
        val idx = "Hello Josephine".indexOfAnyExcept(arrayOf('H', 'l', 'o', ' ', 'J', 's', 'p').toCharArray(), 4)
        assertEquals(9, idx)
    }

    @Test
    fun `should not ignore case`() {
        val idx = "This or that".indexOfAnyExcept(arrayOf('T', 'h', 'i', 's', ' ', 'o', 'r', 'a').toCharArray())
        assertEquals(8, idx)
    }
}
