package json

import ch.digorydoo.kutils.json.jsonify
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFails

internal class JsonifyTest {
    @Test
    fun shouldProperlyJsonifyString() {
        jsonify("hello", "World").let { assertEquals("\"hello\":\"World\"", it) }
        jsonify("sin'gle", "quo'tes").let { assertEquals("\"sin'gle\":\"quo'tes\"", it) }
        jsonify("dou\"ble", "quo\"tes").let { assertEquals("\"dou\\\"ble\":\"quo\\\"tes\"", it) }
        jsonify("ne\nw", "li\nne").let { assertEquals("\"ne\\nw\":\"li\\nne\"", it) }
        jsonify("carr\riage", "ret\rurn").let { assertEquals("\"carr\\riage\":\"ret\\rurn\"", it) }
        jsonify("ta\tb", "ula\ttor").let { assertEquals("\"ta\\tb\":\"ula\\ttor\"", it) }
        jsonify("empty-val", "").let { assertEquals("\"empty-val\":\"\"", it) }
        assertFails("should throw on empty key") { jsonify("", "value") }
    }

    @Test
    fun shouldProperlyJsonifyBoolean() {
        jsonify("hello", true).let { assertEquals("\"hello\":true", it) }
        jsonify("quo\"tes", false).let { assertEquals("\"quo\\\"tes\":false", it) }
        assertFails("should throw on empty key") { jsonify("", true) }
    }

    @Test
    fun shouldProperlyJsonifyInt() {
        jsonify("hello", 42).let { assertEquals("\"hello\":42", it) }
        jsonify("quo\"tes", -42).let { assertEquals("\"quo\\\"tes\":-42", it) }
        assertFails("should throw on empty key") { jsonify("", 42) }
    }

    @Test
    fun shouldProperlyJsonifyFloat() {
        jsonify("hello", 4.2f).let { assertEquals("\"hello\":4.2", it) }
        jsonify("quo\"tes", -4.2f).let { assertEquals("\"quo\\\"tes\":-4.2", it) }
        assertFails("should throw on empty key") { jsonify("", 4.2f) }
    }

    @Test
    fun shouldProperlyJsonifyDouble() {
        jsonify("hello", 4.2).let { assertEquals("\"hello\":4.2", it) }
        jsonify("quo\"tes", -4.2).let { assertEquals("\"quo\\\"tes\":-4.2", it) }
        assertFails("should throw on empty key") { jsonify("", 4.2) }
    }

    @Test
    fun shouldProperlyJsonifyArrayOfString() {
        jsonify("hello", arrayOf()).let { assertEquals("\"hello\":[]", it) }
        jsonify("single", arrayOf("entry"), singleAsString = false).let { assertEquals("\"single\":[\"entry\"]", it) }
        jsonify("single", arrayOf("entry"), singleAsString = true).let { assertEquals("\"single\":\"entry\"", it) }
        jsonify("multi", arrayOf("a", "b"), singleAsString = false).let { assertEquals("\"multi\":[\"a\",\"b\"]", it) }
        jsonify("multi", arrayOf("a", "b"), singleAsString = true).let { assertEquals("\"multi\":[\"a\",\"b\"]", it) }
        jsonify("quo\"tes", arrayOf("dou\"ble")).let { assertEquals("\"quo\\\"tes\":[\"dou\\\"ble\"]", it) }
        assertFails("should throw on empty key") { jsonify("", arrayOf("hello")) }
    }

    @Test
    fun shouldProperlyJsonifyListOfString() {
        jsonify("hello", listOf()).let { assertEquals("\"hello\":[]", it) }
        jsonify("single", listOf("entry"), singleAsString = false).let { assertEquals("\"single\":[\"entry\"]", it) }
        jsonify("single", listOf("entry"), singleAsString = true).let { assertEquals("\"single\":\"entry\"", it) }
        jsonify("multi", listOf("a", "b"), singleAsString = false).let { assertEquals("\"multi\":[\"a\",\"b\"]", it) }
        jsonify("multi", listOf("a", "b"), singleAsString = true).let { assertEquals("\"multi\":[\"a\",\"b\"]", it) }
        jsonify("quo\"tes", listOf("dou\"ble")).let { assertEquals("\"quo\\\"tes\":[\"dou\\\"ble\"]", it) }
        assertFails("should throw on empty key") { jsonify("", listOf("hello")) }
    }

    @Test
    fun shouldProperlyJsonifySetOfString() {
        jsonify("hello", setOf()).let { assertEquals("\"hello\":[]", it) }
        jsonify("single", setOf("entry"), singleAsString = false).let { assertEquals("\"single\":[\"entry\"]", it) }
        jsonify("single", setOf("entry"), singleAsString = true).let { assertEquals("\"single\":\"entry\"", it) }
        jsonify("multi", setOf("a", "b"), singleAsString = false).let { assertEquals("\"multi\":[\"a\",\"b\"]", it) }
        jsonify("multi", setOf("a", "b"), singleAsString = true).let { assertEquals("\"multi\":[\"a\",\"b\"]", it) }
        jsonify("quo\"tes", setOf("dou\"ble")).let { assertEquals("\"quo\\\"tes\":[\"dou\\\"ble\"]", it) }
        assertFails("should throw on empty key") { jsonify("", setOf("hello")) }
    }
}
