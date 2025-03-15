package ch.digorydoo.kutils.matrix

import ch.digorydoo.kutils.string.toPrecision
import ch.digorydoo.kutils.utils.mapInplace
import ch.digorydoo.kutils.utils.newFloatBuffer
import ch.digorydoo.kutils.utils.toFloatBuffer
import ch.digorydoo.kutils.utils.toList
import kotlin.test.*

internal class Matrix4fTest {
    @Test
    fun shouldProperlyConstructMatrixFromLambda() {
        val m = Matrix4f { it.toFloat() * 2.1f }
        assertTrue(m.buffer.isReadOnly)
        assertEquals(m.buffer.limit(), 16)
        assertEquals(
            m.toString(),
            """
            | 0.00  8.40 16.80 25.20 |
            | 2.10 10.50 18.90 27.30 |
            | 4.20 12.60 21.00 29.40 |
            | 6.30 14.70 23.10 31.50 |
            """.trimIndent()
        )
    }

    @Test
    fun shouldProperlyConstructMatrixFromArray() {
        val m = Matrix4f(
            floatArrayOf(
                1.1f, 7.2f, 5.4f, 8.2f, // column!
                4.2f, 6.7f, 4.7f, 1.7f,
                8.5f, 3.4f, 3.9f, 7.8f,
                2.5f, 6.3f, 2.0f, 5.1f,
            )
        )
        assertTrue(m.buffer.isReadOnly)
        assertEquals(m.buffer.limit(), 16)
        assertEquals(
            m.toString(),
            """
            | 1.10 4.20 8.50 2.50 |
            | 7.20 6.70 3.40 6.30 |
            | 5.40 4.70 3.90 2.00 |
            | 8.20 1.70 7.80 5.10 |
            """.trimIndent()
        )

        // The constructor is expected to throw if the size of the array is not 16
        assertFails {
            Matrix4f(floatArrayOf(1.1f, 7.2f, 5.4f, 8.2f))
        }
    }

    @Test
    fun shouldReturnTheSameBufferInstanceEachTime() {
        val m = Matrix4f { it.toFloat() * 2.1f }
        val b1 = m.buffer
        val b2 = m.buffer
        assertEquals(b1, b2)
        assertSame(b1, b2)
        b1.position(2)
        assertEquals(b2.get(), 4.2f, absoluteTolerance = 0.0001f)
        b1.position(6)
        assertEquals(b2.get(), 12.60f, absoluteTolerance = 0.0001f)
    }

    @Test
    fun shouldProperlyConstructMatrixFromBuffer() {
        val buf = newFloatBuffer(16)
        buf.mapInplace { i, _ -> i.toFloat() * 7.7f }
        val m = Matrix4f(buf.asReadOnlyBuffer())
        assertTrue(m.buffer.isReadOnly)

        // Even though the buffer we passed to Matrix4f is read-only, it is expected to share its
        // contents with the original buffer, so the following will change m's contents as well!
        buf.mapInplace { i, _ -> i.toFloat() * 2.1f }

        assertEquals(
            m.toString(),
            """
            | 0.00  8.40 16.80 25.20 |
            | 2.10 10.50 18.90 27.30 |
            | 4.20 12.60 21.00 29.40 |
            | 6.30 14.70 23.10 31.50 |
            """.trimIndent()
        )

        // Passing a read-write buffer to the c'tor is expected to fail
        assertFails { Matrix4f(buf) }
    }

    @Test
    fun shouldProperlyConstructMatrixFromAnother() {
        val m1 = MutableMatrix4f { it.toFloat() * 2.1f }
        val m2 = Matrix4f(m1) // copies the values

        assertTrue(m1.buffer.isReadOnly)
        assertFalse(m1.writableBuffer.isReadOnly)
        assertTrue(m2.buffer.isReadOnly)

        assertEquals(m1.buffer.limit(), 16)
        assertEquals(m2.buffer.limit(), 16)
        m1.setIdentity() // resets m1

        assertEquals(
            m1.toString(),
            """
            | 1.00 0.00 0.00 0.00 |
            | 0.00 1.00 0.00 0.00 |
            | 0.00 0.00 1.00 0.00 |
            | 0.00 0.00 0.00 1.00 |
            """.trimIndent()
        )
        assertEquals(
            m2.toString(),
            """
            | 0.00  8.40 16.80 25.20 |
            | 2.10 10.50 18.90 27.30 |
            | 4.20 12.60 21.00 29.40 |
            | 6.30 14.70 23.10 31.50 |
            """.trimIndent()
        )
    }

    @Test
    fun shouldHaveACorrectIdentity() {
        assertTrue(Matrix4f.identity.buffer.isReadOnly)
        assertEquals(Matrix4f.identity.buffer.limit(), 16)

        assertEquals(
            Matrix4f.identity.toString(),
            """
            | 1.00 0.00 0.00 0.00 |
            | 0.00 1.00 0.00 0.00 |
            | 0.00 0.00 1.00 0.00 |
            | 0.00 0.00 0.00 1.00 |
            """.trimIndent()
        )
    }

    @Test
    fun shouldProperlyMultiplyTwoTranslations() {
        val m1 = Matrix4f(MutableMatrix4f().apply { setTranslation(0.42f, 0.33f, 0.12f) })
        val m2 = Matrix4f(MutableMatrix4f().apply { setTranslation(0.94f, 0.25f, 0.56f) })
        val m3 = m1 * m2

        assertTrue(m1.buffer.isReadOnly)
        assertTrue(m2.buffer.isReadOnly)
        assertTrue(m3.buffer.isReadOnly)
        assertFalse(m3.writableBuffer.isReadOnly)

        assertEquals(
            m1.toString(),
            """
            | 1.00 0.00 0.00 0.42 |
            | 0.00 1.00 0.00 0.33 |
            | 0.00 0.00 1.00 0.12 |
            | 0.00 0.00 0.00 1.00 |
            """.trimIndent()
        )
        assertEquals(
            m2.toString(),
            """
            | 1.00 0.00 0.00 0.94 |
            | 0.00 1.00 0.00 0.25 |
            | 0.00 0.00 1.00 0.56 |
            | 0.00 0.00 0.00 1.00 |
            """.trimIndent()
        )
        assertEquals(
            m3.toString(),
            """
            | 1.00 0.00 0.00 1.36 |
            | 0.00 1.00 0.00 0.58 |
            | 0.00 0.00 1.00 0.68 |
            | 0.00 0.00 0.00 1.00 |
            """.trimIndent()
        )
    }

    @Test
    fun shouldProperlyMultiplyTwoScalings() {
        val m1 = Matrix4f(MutableMatrix4f().apply { setScaling(0.42f, 0.33f, 0.12f) })
        val m2 = Matrix4f(MutableMatrix4f().apply { setScaling(0.94f, 0.25f, 0.56f) })
        val m3 = m1.newMultiplied(m2)

        assertTrue(m1.buffer.isReadOnly)
        assertTrue(m2.buffer.isReadOnly)
        assertTrue(m3.buffer.isReadOnly)
        assertFalse(m3.writableBuffer.isReadOnly)

        assertEquals(
            m1.toString(),
            """
            | 0.42 0.00 0.00 0.00 |
            | 0.00 0.33 0.00 0.00 |
            | 0.00 0.00 0.12 0.00 |
            | 0.00 0.00 0.00 1.00 |
            """.trimIndent()
        )
        assertEquals(
            m2.toString(),
            """
            | 0.94 0.00 0.00 0.00 |
            | 0.00 0.25 0.00 0.00 |
            | 0.00 0.00 0.56 0.00 |
            | 0.00 0.00 0.00 1.00 |
            """.trimIndent()
        )
        assertEquals(
            m3.toString(),
            """
            | 0.39 0.00 0.00 0.00 |
            | 0.00 0.08 0.00 0.00 |
            | 0.00 0.00 0.07 0.00 |
            | 0.00 0.00 0.00 1.00 |
            """.trimIndent()
        )
    }

    @Test
    fun shouldProperlyCopyFloatBuffer() {
        val m = Matrix4f { it.toFloat() * 2.1f }
        val buf = m.toNewFloatBuffer()
        assertTrue(m.buffer.isReadOnly)
        assertFalse(buf.isReadOnly)

        buf.mapInplace { i, value -> value * 10.0f + i * 0.1f }

        assertEquals(
            m.buffer.toList().joinToString(", ") { it.toPrecision(1) },
            "0.0, 2.1, 4.2, 6.3, 8.4, 10.5, 12.6, 14.7, 16.8, 18.9, 21.0, 23.1, 25.2, 27.3, 29.4, 31.5"
        )

        assertEquals(
            buf.toList().joinToString(", ") { it.toPrecision(1) },
            "0.0, 21.1, 42.2, 63.3, 84.4, 105.5, 126.6, 147.7, 168.8, 189.9, 211.0, 232.1, 253.2, 274.3, 295.4, 316.5"
        )
    }

    @Test
    fun shouldProperlyCallEachElement() {
        val m = Matrix4f { it.toFloat() * 2.1f }
        val list = mutableListOf<String>()

        m.forEachElement { i, value ->
            list.add("i=$i, value=${value.toPrecision(1)}")
        }

        assertEquals(list.size, 16)
        assertEquals(
            list.joinToString("\n"),
            """
            i=0, value=0.0
            i=1, value=2.1
            i=2, value=4.2
            i=3, value=6.3
            i=4, value=8.4
            i=5, value=10.5
            i=6, value=12.6
            i=7, value=14.7
            i=8, value=16.8
            i=9, value=18.9
            i=10, value=21.0
            i=11, value=23.1
            i=12, value=25.2
            i=13, value=27.3
            i=14, value=29.4
            i=15, value=31.5
            """.trimIndent()
        )
    }

    @Test
    fun shouldCorrectlyCheckIdentity() {
        assertTrue(Matrix4f.identity.isIdentity())

        val m1 = Matrix4f { if (it % 5 == 0) 1.0f else 0.0f }
        assertTrue(m1.isIdentity())

        val m2 = Matrix4f { if (it % 5 == 0 && it != 15) 1.0f else 0.0f }
        assertFalse(m2.isIdentity())

        val fb1 = floatArrayOf(
            1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f
        ).toFloatBuffer()
        assertTrue(Matrix4f.isIdentity(fb1))

        val fb2 = floatArrayOf(
            1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.0f, 0.0f, 0.0f, 0.0f, 0.0f, 1.1f
        ).toFloatBuffer()
        assertFalse(Matrix4f.isIdentity(fb2))

        // isIdentity is expected not to fail if buffer has incorrect size, but it should return false
        val fb3 = floatArrayOf(1.0f, 0.0f, 0.0f).toFloatBuffer()
        assertFalse(Matrix4f.isIdentity(fb3))
    }
}
