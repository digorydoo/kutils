package ch.digorydoo.kutils.collections

import ch.digorydoo.kutils.utils.Log
import kotlin.test.*

internal class MutableFixedCapacityListTest {
    private data class MockElement(val id: Int)

    @Test
    fun `should have no elements initially`() {
        val set = MutableFixedCapacityList<MockElement>(3)
        assertTrue(set.isEmpty())
        assertEquals(0, set.size)
    }

    @Test
    fun `should add elements correctly`() {
        val set = MutableFixedCapacityList<MockElement>(3)
        val el1 = MockElement(1)
        val el2 = MockElement(2)

        assertTrue(set.add(el1))
        assertEquals(1, set.size)
        assertTrue(set.contains(el1))

        assertTrue(set.add(el2))
        assertEquals(2, set.size)

        // Adding a duplicate is not prevented
        assertTrue(set.add(el1))
        assertEquals(3, set.size)
    }

    @Test
    fun `should respect capacity limit`() {
        val set = MutableFixedCapacityList<MockElement>(3)
        val el1 = MockElement(1)
        val el2 = MockElement(2)
        val el3 = MockElement(3)

        assertTrue(set.add(el1))
        assertTrue(set.add(el2))
        assertTrue(set.add(el3))
        assertEquals(3, set.size)

        Log.enabled = false // suppress expected log message
        assertFalse(set.add(MockElement(4))) // false, because capacity is exceeded
        assertEquals(3, set.size)
        Log.enabled = true
    }

    @Test
    fun `should remove elements correctly`() {
        val set = MutableFixedCapacityList<MockElement>(3)
        val el1 = MockElement(1)
        val el2 = MockElement(2)
        val el3 = MockElement(3)

        set.add(el1)
        set.add(el2)

        assertTrue(set.remove(el1))
        assertEquals(1, set.size)
        assertFalse(set.contains(el1))
        assertTrue(set.contains(el2))

        assertFalse(set.remove(el3))
        assertEquals(1, set.size)
        assertTrue(set.contains(el2))
    }

    @Test
    fun `should properly clear the collection`() {
        val set = MutableFixedCapacityList<MockElement>(3)
        val el1 = MockElement(1)
        val el2 = MockElement(2)

        set.add(el1)
        set.add(el2)

        set.clear()
        assertTrue(set.isEmpty())
        assertEquals(0, set.size)
    }

    @Test
    fun `should properly iterate through elements`() {
        val set = MutableFixedCapacityList<MockElement>(3)
        val el1 = MockElement(1)
        val el2 = MockElement(2)

        set.add(el1)
        set.add(el2)

        val iterator = set.iterator()
        assertTrue(iterator.hasNext())
        assertEquals(el1, iterator.next())
        assertTrue(iterator.hasNext())
        assertEquals(el2, iterator.next())
        assertFalse(iterator.hasNext())
    }

    @Test
    fun `should throw when trying to add elements inside forEach`() {
        val set = MutableFixedCapacityList<MockElement>(3).apply {
            add(MockElement(1))
            add(MockElement(2))
        }

        assertFails {
            set.forEach { _ ->
                set.add(MockElement(3))
            }
        }
    }

    @Test
    fun `should throw when trying to remove elements inside forEach`() {
        val set = MutableFixedCapacityList<MockElement>(3).apply {
            add(MockElement(1))
            add(MockElement(2))
        }

        assertFails {
            set.forEach { set.remove(it) }
        }
    }

    @Test
    fun `should be able to remove elements via iterator`() {
        val set = MutableFixedCapacityList<MockElement>(3)
        val el1 = MockElement(1)
        val el2 = MockElement(2)
        val el3 = MockElement(3)

        set.add(el1)
        set.add(el2)
        set.add(el3)

        val iterator = set.iterator()
        iterator.next()
        iterator.remove()

        assertEquals(2, set.size)
        assertFalse(set.contains(el1))
        assertTrue(set.contains(el2))
        assertTrue(set.contains(el3))

        // Cannot remove without calling next first
        assertFailsWith<IllegalStateException> {
            iterator.remove()
        }
    }
}
