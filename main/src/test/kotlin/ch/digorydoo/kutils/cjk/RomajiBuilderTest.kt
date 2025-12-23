package ch.digorydoo.kutils.cjk

import kotlin.test.Test
import kotlin.test.assertEquals

/**
 * compile-goigoi itself is an extensive test of the FuriganaBuilder, so we only check some odd special cases here.
 */
internal class RomajiBuilderTest {
    @Test
    fun `should return an empty string when given an empty string`() {
        val result = RomajiBuilder().build("")
        assertEquals("", result)
    }

    @Test
    fun `should properly add a space after particle wo`() {
        // This test proves that the linter warning AssignedValueIsNeverRead is wrong (see implementation)
        val result = RomajiBuilder().build("【勉：べん】【強：きょう】を【始：はじ】める")
        assertEquals("benkyōo hajimeru", result)
    }
}
