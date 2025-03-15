package ch.digorydoo.kutils.json

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

internal class JsonOrNullTest {
    @Test
    fun shouldProperlyTransformNonEmptyString() {
        jsonOrNull("hello", "World").let { assertEquals("\"hello\":\"World\"", it) }
        jsonOrNull("sin'gle", "quo'tes").let { assertEquals("\"sin'gle\":\"quo'tes\"", it) }
        jsonOrNull("dou\"ble", "quo\"tes").let { assertEquals("\"dou\\\"ble\":\"quo\\\"tes\"", it) }
        jsonOrNull("ne\nw", "li\nne").let { assertEquals("\"ne\\nw\":\"li\\nne\"", it) }
        jsonOrNull("carr\riage", "ret\rurn").let { assertEquals("\"carr\\riage\":\"ret\\rurn\"", it) }
        jsonOrNull("ta\tb", "ula\ttor").let { assertEquals("\"ta\\tb\":\"ula\\ttor\"", it) }
        assertFails("should throw on empty key") { jsonOrNull("", "value") }
    }

    @Test
    fun shouldProperlyTransformEmptyString() {
        jsonOrNull("hello", "").let { assertEquals(null, it) }
        jsonOrNull("sin'gle", "").let { assertEquals(null, it) }
        jsonOrNull("dou\"ble", "").let { assertEquals(null, it) }
        jsonOrNull("ne\nw", "").let { assertEquals(null, it) }
        jsonOrNull("carr\riage", "").let { assertEquals(null, it) }
        jsonOrNull("ta\tb", "").let { assertEquals(null, it) }
        assertFails("should throw on empty key") { jsonOrNull("", "not null") }
    }

    @Test
    fun shouldProperlyTransformBoolean() {
        jsonOrNull("hello", true).let { assertEquals("\"hello\":true", it) }
        jsonOrNull("hello", false).let { assertEquals(null, it) }
        jsonOrNull("sin'gle", true).let { assertEquals("\"sin'gle\":true", it) }
        jsonOrNull("dou\"ble", true).let { assertEquals("\"dou\\\"ble\":true", it) }
        jsonOrNull("ne\nw", true).let { assertEquals("\"ne\\nw\":true", it) }
        jsonOrNull("carr\riage", true).let { assertEquals("\"carr\\riage\":true", it) }
        jsonOrNull("ta\tb", true).let { assertEquals("\"ta\\tb\":true", it) }
        assertFails("should throw on empty key") { jsonOrNull("", true) }
        assertFails("should throw on empty key") { jsonOrNull("", false) }
    }

    @Test
    fun shouldProperlyTransformSetOfString() {
        jsonOrNull("hello", setOf()).let { assertEquals(null, it) }
        jsonOrNull("hello", setOf("one"), singleAsString = false).let { assertEquals("\"hello\":[\"one\"]", it) }
        jsonOrNull("hello", setOf("one"), singleAsString = true).let { assertEquals("\"hello\":\"one\"", it) }
        jsonOrNull("multi", setOf("a", "b"), singleAsString = false).let { assertEquals("\"multi\":[\"a\",\"b\"]", it) }
        jsonOrNull("multi", setOf("a", "b"), singleAsString = true).let { assertEquals("\"multi\":[\"a\",\"b\"]", it) }
        assertFails("should throw on empty key") { jsonOrNull("", setOf("a", "b")) }
        assertFails("should throw on empty key") { jsonOrNull("", setOf()) }
    }
}
