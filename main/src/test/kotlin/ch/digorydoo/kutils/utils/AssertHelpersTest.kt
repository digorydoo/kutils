package ch.digorydoo.kutils.utils

import kotlin.test.Test
import kotlin.test.assertFails

internal class AssertHelpersTest {
    @Test
    fun `should properly handle assertLessThan with Float`() {
        assertLessThan(4.2f, 8.0f)
        assertFails { assertLessThan(8.2f, 4.0f) }
        assertFails { assertLessThan(5.0f, 5.0f) }
    }

    @Test
    fun `should properly handle assertLessThan with Int`() {
        assertLessThan(4, 8)
        assertFails { assertLessThan(8, 4) }
        assertFails { assertLessThan(5, 5) }
    }

    @Test
    fun `should properly handle assertGreaterThan with Float`() {
        assertGreaterThan(8.2f, 4.0f)
        assertFails { assertGreaterThan(4.2f, 8.0f) }
        assertFails { assertGreaterThan(5.0f, 5.0f) }
    }

    @Test
    fun `should properly handle assertGreaterThan with Int`() {
        assertGreaterThan(8, 4)
        assertFails { assertGreaterThan(4, 8) }
        assertFails { assertGreaterThan(5, 5) }
    }

    @Test
    fun `should properly handle assertLessOrEqual with Float`() {
        assertLessOrEqual(4.2f, 8.0f)
        assertFails { assertLessOrEqual(8.2f, 4.0f) }
        assertLessOrEqual(5.0f, 5.0f)
    }

    @Test
    fun `should properly handle assertLessOrEqual with Int`() {
        assertLessOrEqual(4, 8)
        assertFails { assertLessOrEqual(8, 4) }
        assertLessOrEqual(5, 5)
    }

    @Test
    fun `should properly handle assertGreaterOrEqual with Float`() {
        assertGreaterOrEqual(8.2f, 4.0f)
        assertFails { assertGreaterOrEqual(4.2f, 8.0f) }
        assertGreaterOrEqual(5.0f, 5.0f)
    }

    @Test
    fun `should properly handle assertGreaterOrEqual with Int`() {
        assertGreaterOrEqual(8, 4)
        assertFails { assertGreaterOrEqual(4, 8) }
        assertGreaterOrEqual(5, 5)
    }
}
