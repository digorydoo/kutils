package string

import ch.digorydoo.kutils.string.snakeCase
import kotlin.test.Test
import kotlin.test.assertEquals

internal class SnakeCaseTest {
    @Test
    fun `should return an empty string when an empty string is given`() {
        assertEquals("", snakeCase(""))
    }

    @Test
    fun `should return the unmodified string when no upper-case letters are used`() {
        assertEquals("blahblupp", snakeCase("blahblupp"))
    }

    @Test
    fun `should return the lower-case string when no lower-case letters are used`() {
        assertEquals("blahblupp", snakeCase("BLAHBLUPP"))
    }

    @Test
    fun `should properly generate the snake-case from PascalCase`() {
        assertEquals("the-pascal-case", snakeCase("ThePascalCase"))
    }

    @Test
    fun `should properly generate the snake-case from javaCase`() {
        assertEquals("the-java-case", snakeCase("theJavaCase"))
    }

    @Test
    fun `should properly generate the snake-case from c_case`() {
        assertEquals("the-c-case", snakeCase("the_c_case"))
    }

    @Test
    fun `should return a number as is`() {
        assertEquals("12345", snakeCase("12345"))
    }

    @Test
    fun `should properly handle a number inside a lower-case string`() {
        assertEquals("blah12345-blupp", snakeCase("blah12345blupp"))
    }

    @Test
    fun `should properly handle a number inside an upper-case string`() {
        assertEquals("blah12345-blupp", snakeCase("BLAH12345BLUPP"))
    }

    @Test
    fun `should properly handle a number inside a PascalCase string`() {
        assertEquals("blah12345-blupp", snakeCase("Blah12345Blupp"))
    }
}
