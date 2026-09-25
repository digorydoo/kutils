package ch.digorydoo.kutils.string

import kotlin.test.Test
import kotlin.test.assertEquals

internal class NumberToStringTest {
    @Test
    fun `should return the expected result from withPercent`() {
        assertEquals("200 (78%)", withPercent(n = 200, total = 256, padding = 0))
        assertEquals("  200 (78%)", withPercent(n = 200, total = 256, padding = 5))
    }

    @Test
    fun `should return the expected result from toPercent`() {
        assertEquals("12.4%", 0.12375f.toPercent())
        assertEquals("12.4%", 0.12375.toPercent())

        assertEquals("0%", 0.0f.toPercent())
        assertEquals("0%", 0.0.toPercent())

        assertEquals("100%", 1.0f.toPercent())
        assertEquals("100%", 1.0.toPercent())
    }

    @Test
    fun `should return the expected result from toFixed`() {
        assertEquals("987.13", 987.1274f.toFixed(2))
        assertEquals("987.13", 987.1274.toFixed(2))
    }

    @Test
    fun `should return the expected result from toDelimited`() {
        assertEquals("123'456'789", 123456789.toDelimited())
    }
}
