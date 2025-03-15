package ch.digorydoo.kutils.json

import kotlin.test.Test
import kotlin.test.assertEquals

internal class ToJSONTest {
    @Test
    fun shouldProperlyEncodeString() {
        "Hello, World!".toJSON().let { assertEquals("\"Hello, World!\"", it) }
        "With 'single quotes'".toJSON().let { assertEquals("\"With 'single quotes'\"", it) }
        "With \"double quotes\"".toJSON().let { assertEquals("\"With \\\"double quotes\\\"\"", it) }
        "With\nnewline".toJSON().let { assertEquals("\"With\\nnewline\"", it) }
        "With\rcarriage return".toJSON().let { assertEquals("\"With\\rcarriage return\"", it) }
        "With\ttab".toJSON().let { assertEquals("\"With\\ttab\"", it) }
    }

    @Test
    fun shouldProperlyEncodeInt() {
        42.toJSON().let { assertEquals("42", it) }
        0.toJSON().let { assertEquals("0", it) }
        (-1).toJSON().let { assertEquals("-1", it) }
        Int.MAX_VALUE.toJSON().let { assertEquals("2147483647", it) }
        Int.MIN_VALUE.toJSON().let { assertEquals("-2147483648", it) }
    }

    @Test
    fun shouldProperlyEncodeFloat() {
        4.2f.toJSON().let { assertEquals("4.2", it) }
        0.0f.toJSON().let { assertEquals("0.0", it) }
        (-1.0f).toJSON().let { assertEquals("-1.0", it) }
        Float.MAX_VALUE.toJSON().let { assertEquals("3.4028235E38", it) }
        Float.MIN_VALUE.toJSON().let { assertEquals("1.4E-45", it) }
        Float.NaN.toJSON().let { assertEquals("null", it) }
        Float.NEGATIVE_INFINITY.toJSON().let { assertEquals("null", it) }
        Float.POSITIVE_INFINITY.toJSON().let { assertEquals("null", it) }
    }

    @Test
    fun shouldProperlyEncodeDouble() {
        4.2.toJSON().let { assertEquals("4.2", it) }
        0.0.toJSON().let { assertEquals("0.0", it) }
        (-1.0).toJSON().let { assertEquals("-1.0", it) }
        Double.MAX_VALUE.toJSON().let { assertEquals("1.7976931348623157E308", it) }
        Double.MIN_VALUE.toJSON().let { assertEquals("4.9E-324", it) }
        Double.NaN.toJSON().let { assertEquals("null", it) }
        Double.NEGATIVE_INFINITY.toJSON().let { assertEquals("null", it) }
        Double.POSITIVE_INFINITY.toJSON().let { assertEquals("null", it) }
    }

    @Test
    fun shouldProperlyEncodeArrayOfString() {
        arrayOf<String>().toJSON().let { assertEquals("[]", it) }
        arrayOf("blah").toJSON().let { assertEquals("[\"blah\"]", it) }
        arrayOf("ichi", "ni", "san").toJSON().let { assertEquals("[\"ichi\",\"ni\",\"san\"]", it) }
        arrayOf("With 'single quotes'").toJSON().let { assertEquals("[\"With 'single quotes'\"]", it) }
        arrayOf("With \"double quotes\"").toJSON().let { assertEquals("[\"With \\\"double quotes\\\"\"]", it) }
    }

    @Test
    fun shouldProperlyEncodeListOfString() {
        listOf<String>().toJSON().let { assertEquals("[]", it) }
        listOf("blah").toJSON().let { assertEquals("[\"blah\"]", it) }
        listOf("ichi", "ni", "san").toJSON().let { assertEquals("[\"ichi\",\"ni\",\"san\"]", it) }
        listOf("With 'single quotes'").toJSON().let { assertEquals("[\"With 'single quotes'\"]", it) }
        listOf("With \"double quotes\"").toJSON().let { assertEquals("[\"With \\\"double quotes\\\"\"]", it) }
    }

    @Test
    fun shouldProperlyEncodeSetOfString() {
        setOf<String>().toJSON().let { assertEquals("[]", it) }
        setOf("blah").toJSON().let { assertEquals("[\"blah\"]", it) }
        setOf("ichi", "ni", "san").toJSON().let { assertEquals("[\"ichi\",\"ni\",\"san\"]", it) }
        setOf("With 'single quotes'").toJSON().let { assertEquals("[\"With 'single quotes'\"]", it) }
        setOf("With \"double quotes\"").toJSON().let { assertEquals("[\"With \\\"double quotes\\\"\"]", it) }
    }
}
