package ch.digorydoo.kutils.utils

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertIs
import kotlin.test.assertIsNot

internal class GivenTest {
    open class Mock(val value: Int)

    class Mock1(sideEffect: (who: String) -> Unit): Mock(1) {
        init {
            sideEffect("Mock1")
        }
    }

    class Mock2(sideEffect: (who: String) -> Unit): Mock(2) {
        init {
            sideEffect("Mock2")
        }
    }

    @Test
    fun `should work as expected when left and right are both of the same type`() {
        val m0 = { Mock(10) } given false otherwise { Mock(20) }
        assertEquals(20, m0.value) // condition was false, we get the right value

        val m1 = { Mock(30) } given true otherwise { Mock(40) }
        assertEquals(30, m1.value) // condition was true, we get the left value
    }

    @Test
    fun `should work as expected when left and right are both of different types`() {
        val called = mutableListOf<String>()
        val sideEffect = { who: String -> called.add(who); Unit }

        // Unfortunately, Kotlin can't infer that the supertype Mock satisfies the constraints
        // without an explicit cast.
        val m1 = { Mock1(sideEffect) as Mock } given true otherwise { Mock2(sideEffect) }
        assertIs<Mock1>(m1) // condition was true, we get the left value
        assertIsNot<Mock2>(m1)
        assertEquals("Mock1", called.joinToString(", ")) // Mock2 wasn't evaluated as expected

        called.clear()

        val m0 = { Mock1(sideEffect) as Mock } given false otherwise { Mock2(sideEffect) }
        assertIsNot<Mock1>(m0) // condition was true, we get the right value
        assertIs<Mock2>(m0)
        assertEquals("Mock2", called.joinToString(", ")) // Mock1 wasn't evaluated as expected
    }
}
