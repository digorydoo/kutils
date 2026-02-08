package ch.digorydoo.kutils.vector

import kotlin.test.*

internal class Vector3fSetTest {
    @Test
    fun `should create proper empty set`() {
        val set = Vector3fSet()
        assertEquals(0, set.size)
        assertTrue(set.isEmpty())
        assertEquals(set.toString(), "{}")
    }

    @Test
    fun `should add distinct vectors`() {
        val set = Vector3fSet()

        set.add(Vector3f(3.5f, 5.9f, 1.1f))
        assertEquals(set.toString(), "{(3.5, 5.9, 1.1)}")

        set.add(Vector3f(5.9f, 3.5f, 1.1f))
        assertEquals(set.toString(), "{(3.5, 5.9, 1.1), (5.9, 3.5, 1.1)}")

        set.add(Vector3f(3.5f, 5.9f, 1.1f))
        assertEquals(set.toString(), "{(3.5, 5.9, 1.1), (5.9, 3.5, 1.1)}")

        set.add(Vector3f(5.9f, 3.5f, 1.1f))
        assertEquals(set.toString(), "{(3.5, 5.9, 1.1), (5.9, 3.5, 1.1)}")

        set.add(Vector3f(0.0f, 0.0f, 0.0f))
        assertEquals(set.toString(), "{(3.5, 5.9, 1.1), (5.9, 3.5, 1.1), (0.0, 0.0, 0.0)}")
    }

    @Test
    fun `should iterate over distinct members`() {
        val arr = arrayOf(
            Vector3f(1.1f, 2.2f, 0.0f),
            Vector3f(3.3f, 4.2f, 1.0f),
            Vector3f(5.5f, 6.6f, 2.0f),
            Vector3f(3.3f, 4.2f, 1.0f),
        )
        val set = Vector3fSet()
        set.add(arr[0])
        set.add(arr[1])
        set.add(arr[2])
        set.add(arr[3])

        var count = 0

        set.forEach {
            val vec = arr[count++]
            assertTrue(vec.hasSameValues(it))
        }

        assertEquals(count, 3) // because the fourth vec is not a distinct vec
    }

    @Test
    fun `should add new instances`() {
        val set = Vector3fSet()

        val vec = Vector3f(8.2f, 4.2f, 1.1f)
        set.add(vec)

        var count = 0

        set.forEach {
            assertNotSame(vec, it)
            assertTrue(vec.hasSameValues(it))
            assertTrue(it.hasSameValues(vec))
            count++
        }

        assertEquals(count, 1)
    }

    @Test
    fun `should properly implement clear`() {
        val set = Vector3fSet()
        set.add(Vector3f(1.1f, 2.2f, 1.0f))
        set.add(Vector3f(3.3f, 4.2f, 2.0f))
        set.add(Vector3f(5.5f, 6.6f, 3.0f))
        assertEquals(set.size, 3)
        assertFalse(set.isEmpty())
        assertEquals(set.toString(), "{(1.1, 2.2, 1.0), (3.3, 4.2, 2.0), (5.5, 6.6, 3.0)}")

        set.clear()
        assertEquals(set.size, 0)
        assertTrue(set.isEmpty())
        assertEquals(set.toString(), "{}")
    }

    @Test
    fun `should add all from FloatArray`() {
        val arr = floatArrayOf(
            1.1f, 2.2f, 9.0f,
            3.3f, 4.2f, 8.0f,
            5.5f, 6.6f, 7.0f,
            1.1f, 2.2f, 9.0f, // same!
            7.0f, 8.0f, 6.0f,
            5.5f, 6.6f, 7.0f, // same!
        )

        val set = Vector3fSet()
        set.addAll(arr)
        assertEquals(set.size, 4)
        assertFalse(set.isEmpty())
        assertEquals(set.toString(), "{(1.1, 2.2, 9.0), (3.3, 4.2, 8.0), (5.5, 6.6, 7.0), (7.0, 8.0, 6.0)}")

        assertFails {
            // This should fail, because the number of floats is not a multiple of 3
            Vector3fSet().addAll(floatArrayOf(1.1f, 2.2f, 3.3f, 4.2f))
        }
    }

    @Test
    fun `should find all indices`() {
        val set = Vector3fSet().apply {
            add(Vector3f(1.1f, 5.0f, 6.0f))
            add(Vector3f(2.2f, 4.2f, 7.0f))
            add(Vector3f(3.3f, 3.9f, 8.0f))
        }

        val l0 = set.findIndices(floatArrayOf())
        assertTrue(l0.isEmpty())

        val l1 = set.findIndices(floatArrayOf(2.2f, 4.2f, 7.0f))
        assertEquals(l1.size, 1)
        assertEquals(l1[0], 1)

        val l2 = set.findIndices(floatArrayOf(3.3f, 3.9f, 8.0f, 1.1f, 5.0f, 6.0f))
        assertEquals(l2.size, 2)
        assertEquals(l2[0], 2)
        assertEquals(l2[1], 0)

        val l3 = set.findIndices(floatArrayOf(3.3f, 3.9f, 8.0f, 2.2f, 4.2f, 7.0f, 3.3f, 3.9f, 8.0f, 1.1f, 5.0f, 6.0f))
        assertEquals(l3.size, 4)
        assertEquals(l3[0], 2)
        assertEquals(l3[1], 1)
        assertEquals(l3[2], 2)
        assertEquals(l3[3], 0)

        assertFails {
            // This should fail, because there is no such member
            set.findIndices(floatArrayOf(42.0f, 99.0f, 11.0f))
        }

        assertFails {
            // This should fail, because the number of floats is not a multiple of 3
            set.findIndices(floatArrayOf(3.3f, 3.9f, 2.2f, 4.2f))
        }
    }

    @Test
    fun `should convert to MutableList`() {
        val set = Vector3fSet().apply {
            add(Vector3f(1.1f, 5.0f, 1.0f))
            add(Vector3f(2.2f, 4.2f, 2.0f))
            add(Vector3f(3.3f, 3.9f, 3.0f))
            add(Vector3f(1.1f, 5.0f, 1.0f))
        }
        val list = set.toMutableList()
        assertIs<MutableList<Vector3f>>(list)
        assertEquals(list.size, 3)
        assertEquals(list.joinToString(", "), "(1.1, 5.0, 1.0), (2.2, 4.2, 2.0), (3.3, 3.9, 3.0)")
    }

    @Test
    fun `should convert to FloatArray`() {
        val set = Vector3fSet().apply {
            add(Vector3f(1.1f, 5.0f, 9.0f))
            add(Vector3f(2.2f, 4.2f, 8.0f))
            add(Vector3f(3.3f, 3.9f, 7.0f))
            add(Vector3f(1.1f, 5.0f, 9.0f))
        }
        val arr = set.toFloatArray()
        assertIs<FloatArray>(arr)
        assertEquals(arr.size, 9)
        assertEquals(arr.joinToString(", "), "1.1, 5.0, 9.0, 2.2, 4.2, 8.0, 3.3, 3.9, 7.0")
    }
}
