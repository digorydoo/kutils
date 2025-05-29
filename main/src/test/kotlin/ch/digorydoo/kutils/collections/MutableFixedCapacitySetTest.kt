package ch.digorydoo.kutils.collections

import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertTrue

internal class MutableFixedCapacitySetTest {
    private data class MockElement(val id: Int)

    @Test
    fun `should prevent duplicates when using the add function`() {
        val set = MutableFixedCapacitySet<MockElement>(5)
        val el1 = MockElement(1)
        val el2 = MockElement(2)

        assertTrue(set.add(el1))
        assertEquals(1, set.size)
        assertTrue(set.contains(el1))

        assertTrue(set.add(el2))
        assertEquals(2, set.size)
        assertTrue(set.contains(el2))

        assertFalse(set.add(el1)) // returns false to indicate element already exists
        assertEquals(2, set.size) // still 2
    }

    @Test
    fun `should prevent duplicates when using the addAll function`() {
        val el1 = MockElement(1)
        val el2 = MockElement(2)
        val el3 = MockElement(3)

        val set1 = MutableFixedCapacitySet<MockElement>(5)
        assertTrue(set1.add(el1))
        assertTrue(set1.add(el2))
        assertEquals(2, set1.size)

        val set2 = MutableFixedCapacitySet<MockElement>(5)
        assertTrue(set2.add(el2))
        assertTrue(set2.add(el3))
        assertEquals(2, set2.size)

        val set3 = MutableFixedCapacitySet<MockElement>(5)
        set3.addAll(set1)
        assertEquals(2, set3.size)
        set3.addAll(set2)
        assertEquals(3, set3.size) // one duplicate was eliminated

        assertEquals("MockElement(id=1), MockElement(id=2), MockElement(id=3)", set3.joinToString(", "))
    }

    @Test
    fun `should prevent duplicates when using the += operator`() {
        val set = MutableFixedCapacitySet<MockElement>(5)
        val el1 = MockElement(1)
        val el2 = MockElement(2)

        set += el1
        assertEquals(1, set.size)
        assertTrue(set.contains(el1))

        set += el2
        assertEquals(2, set.size)
        assertTrue(set.contains(el1))

        set += el1
        assertEquals(2, set.size) // still 2
    }
}
