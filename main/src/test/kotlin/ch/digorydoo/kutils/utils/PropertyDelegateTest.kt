package ch.digorydoo.kutils.utils

import kotlin.test.Test
import kotlin.test.assertEquals

internal class PropertyDelegateTest {
    @Test
    fun `should return the same value as the delegated member`() {
        class A(var value: Int)

        class B(val a: A) {
            var delegatedValue by PropertyDelegate(a::value)
        }

        val a = A(42)
        val b = B(a)
        assertEquals(42, b.delegatedValue)

        a.value = 33
        assertEquals(33, b.delegatedValue) // b sees changed value

        b.delegatedValue = 72 // b sets value of a
        assertEquals(72, a.value)
    }
}
