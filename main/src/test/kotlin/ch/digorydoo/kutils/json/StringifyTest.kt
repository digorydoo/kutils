package ch.digorydoo.kutils.json

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFailsWith

internal class StringifyTest {
    private class SomeClass

    @Test
    fun `should properly stringify String`() {
        stringify("Hello, World!").let { assertEquals("\"Hello, World!\"", it) }
        stringify("With 'single quotes'").let { assertEquals("\"With 'single quotes'\"", it) }
        stringify("With \"double quotes\"").let { assertEquals("\"With \\\"double quotes\\\"\"", it) }
        stringify("With\nnewline").let { assertEquals("\"With\\nnewline\"", it) }
        stringify("With\rcarriage return").let { assertEquals("\"With\\rcarriage return\"", it) }
        stringify("With\ttab").let { assertEquals("\"With\\ttab\"", it) }
    }

    @Test
    fun `should properly stringify Char`() {
        stringify('a').let { assertEquals("\"a\"", it) }
        stringify('\'').let { assertEquals("\"'\"", it) }
        stringify('"').let { assertEquals("\"\\\"\"", it) }
        stringify('\n').let { assertEquals("\"\\n\"", it) }
        stringify('\r').let { assertEquals("\"\\r\"", it) }
        stringify('\t').let { assertEquals("\"\\t\"", it) }
        stringify('羽').let { assertEquals("\"羽\"", it) }
    }

    @Test
    fun `should properly stringify Boolean`() {
        stringify(true).let { assertEquals("true", it) }
        stringify(false).let { assertEquals("false", it) }
    }

    @Test
    fun `should properly stringify Int`() {
        stringify(42).let { assertEquals("42", it) }
        stringify(0).let { assertEquals("0", it) }
        stringify(-1).let { assertEquals("-1", it) }
        stringify(Int.MAX_VALUE).let { assertEquals("2147483647", it) }
        stringify(Int.MIN_VALUE).let { assertEquals("-2147483648", it) }
    }

    @Test
    fun `should properly stringify Long`() {
        stringify(42L).let { assertEquals("42", it) }
        stringify(0L).let { assertEquals("0", it) }
        stringify(-1L).let { assertEquals("-1", it) }
        stringify(Long.MAX_VALUE).let { assertEquals("9223372036854775807", it) }
        stringify(Long.MIN_VALUE).let { assertEquals("-9223372036854775808", it) }
    }

    @Test
    fun `should properly stringify Float`() {
        stringify(4.2f).let { assertEquals("4.2", it) }
        stringify(0.0f).let { assertEquals("0.0", it) }
        stringify((-1.0f)).let { assertEquals("-1.0", it) }
        stringify(Float.MAX_VALUE).let { assertEquals("3.4028235E38", it) }
        stringify(Float.MIN_VALUE).let { assertEquals("1.4E-45", it) }
        stringify(Float.NaN).let { assertEquals("null", it) }
        stringify(Float.NEGATIVE_INFINITY).let { assertEquals("null", it) }
        stringify(Float.POSITIVE_INFINITY).let { assertEquals("null", it) }
    }

    @Test
    fun `should properly stringify Double`() {
        stringify(4.2).let { assertEquals("4.2", it) }
        stringify(0.0).let { assertEquals("0.0", it) }
        stringify((-1.0)).let { assertEquals("-1.0", it) }
        stringify(Double.MAX_VALUE).let { assertEquals("1.7976931348623157E308", it) }
        stringify(Double.MIN_VALUE).let { assertEquals("4.9E-324", it) }
        stringify(Double.NaN).let { assertEquals("null", it) }
        stringify(Double.NEGATIVE_INFINITY).let { assertEquals("null", it) }
        stringify(Double.POSITIVE_INFINITY).let { assertEquals("null", it) }
    }

    @Test
    fun `should properly stringify an array of String`() {
        stringify(arrayOf<String>()).let { assertEquals("[]", it) }
        stringify(arrayOf("blah")).let { assertEquals("[\"blah\"]", it) }
        stringify(arrayOf("ichi", "ni", "san")).let { assertEquals("[\"ichi\",\"ni\",\"san\"]", it) }
        stringify(arrayOf("With 'single quotes'")).let { assertEquals("[\"With 'single quotes'\"]", it) }
        stringify(arrayOf("With \"double quotes\"")).let { assertEquals("[\"With \\\"double quotes\\\"\"]", it) }
    }

    @Test
    fun `should properly stringify an array of Int`() {
        assertEquals("[42]", stringify(arrayOf(42)))
        assertEquals("[-10,20]", stringify(arrayOf(-10, 20)))

        assertEquals(
            """
            [
                -10,
                20
            ]
            """.trimIndent(),
            stringify(arrayOf(-10, 20), 4)
        )
    }

    @Test
    fun `should properly stringify a list of String`() {
        stringify(listOf<String>()).let { assertEquals("[]", it) }
        stringify(listOf("blah")).let { assertEquals("[\"blah\"]", it) }
        stringify(listOf("ichi", "ni", "san")).let { assertEquals("[\"ichi\",\"ni\",\"san\"]", it) }
        stringify(listOf("With 'single quotes'")).let { assertEquals("[\"With 'single quotes'\"]", it) }
        stringify(listOf("With \"double quotes\"")).let { assertEquals("[\"With \\\"double quotes\\\"\"]", it) }
    }

    @Test
    fun `should properly stringify a set of String`() {
        stringify(setOf<String>()).let { assertEquals("[]", it) }
        stringify(setOf("blah")).let { assertEquals("[\"blah\"]", it) }
        stringify(setOf("ichi", "ni", "san")).let { assertEquals("[\"ichi\",\"ni\",\"san\"]", it) }
        stringify(setOf("With 'single quotes'")).let { assertEquals("[\"With 'single quotes'\"]", it) }
        stringify(setOf("With \"double quotes\"")).let { assertEquals("[\"With \\\"double quotes\\\"\"]", it) }
    }

    @Test
    fun `should properly stringify a map of String to String`() {
        assertEquals("{}", stringify(mapOf<String, String>()))
        assertEquals("{\"one\":\"ichi\"}", stringify(mapOf("one" to "ichi")))

        assertEquals(
            """
            {
              "one": "ichi",
              "two": "ni"
            }
            """.trimIndent(),
            // We expect mapOf creates a LinkedHashMap-backed Map, so ordering is expected to be preserved.
            stringify(mapOf("one" to "ichi", "two" to "ni"), 2)
        )
    }

    @Test
    fun `should properly stringify a complex structure`() {
        assertEquals(
            """{"nestedMap":{"null":null,"int":9,"string":"hello","wasChar":"x"}""" +
                ""","nestedList":[null,9,"hello","x"]}""",
            stringify(
                mapOf(
                    "nestedMap" to mapOf(
                        "null" to null,
                        "int" to 9,
                        "string" to "hello",
                        "wasChar" to 'x',
                    ),
                    "nestedList" to listOf(null, 9, "hello", 'x'),
                ),
                indent = 0 // minified
            )
        )
        assertEquals(
            """
            {
            "nestedMap":{
            "null":null,
            "int":9,
            "string":"hello",
            "wasChar":"x"
            },
            "nestedList":[
            null,
            9,
            "hello",
            "x"
            ]
            }
            """.trimIndent(),
            stringify(
                mapOf(
                    "nestedMap" to mapOf(
                        "null" to null,
                        "int" to 9,
                        "string" to "hello",
                        "wasChar" to 'x',
                    ),
                    "nestedList" to listOf(null, 9, "hello", 'x'),
                ),
                indent = -1 // flat
            )
        )
        assertEquals(
            """
            {
               "nestedMap": {
                  "null": null,
                  "int": 9,
                  "string": "hello",
                  "wasChar": "x"
               },
               "nestedList": [
                  null,
                  9,
                  "hello",
                  "x"
               ]
            }
            """.trimIndent(),
            stringify(
                mapOf(
                    "nestedMap" to mapOf(
                        "null" to null,
                        "int" to 9,
                        "string" to "hello",
                        "wasChar" to 'x',
                    ),
                    "nestedList" to listOf(null, 9, "hello", 'x'),
                ),
                indent = 3
            )
        )
    }

    @Test
    fun `should fail with the expected error if stringify is passed an object it cannot handle`() {
        val exception = assertFailsWith<StringifyException> {
            stringify(SomeClass())
        }
        assertEquals(
            $$"Don't know how to serialize ch.digorydoo.kutils.json.StringifyTest$SomeClass",
            exception.message
        )
    }
}
