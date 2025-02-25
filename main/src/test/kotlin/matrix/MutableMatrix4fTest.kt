package matrix

import ch.digorydoo.kutils.matrix.Matrix4f
import ch.digorydoo.kutils.matrix.MutableMatrix4f
import ch.digorydoo.kutils.point.Point3f
import ch.digorydoo.kutils.string.toPrecision
import ch.digorydoo.kutils.utils.mapInplace
import ch.digorydoo.kutils.utils.newFloatBuffer
import ch.digorydoo.kutils.utils.toList
import kotlin.test.*

internal class MutableMatrix4fTest {
    @Test
    fun shouldReturnIdentityOnEmptyConstructor() {
        val m = MutableMatrix4f()

        assertTrue(m.buffer.isReadOnly)
        assertFalse(m.writableBuffer.isReadOnly)
        assertEquals(m.buffer.limit(), 16)

        assertEquals(
            m.toString(),
            """
            | 1.00 0.00 0.00 0.00 |
            | 0.00 1.00 0.00 0.00 |
            | 0.00 0.00 1.00 0.00 |
            | 0.00 0.00 0.00 1.00 |
            """.trimIndent()
        )
    }

    @Test
    fun shouldReturnTheSameBufferInstanceEachTime() {
        val m = MutableMatrix4f { it.toFloat() * 2.1f }
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
    fun shouldProperlyConstructMatrixFromLambda() {
        val m = MutableMatrix4f { it.toFloat() * 2.1f }

        assertTrue(m.buffer.isReadOnly)
        assertFalse(m.writableBuffer.isReadOnly)
        assertEquals(m.buffer.limit(), 16)

        // Indices are expected to increase column-first.
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
        val m = MutableMatrix4f(
            floatArrayOf(
                1.1f, 7.2f, 5.4f, 8.2f, // column!
                4.2f, 6.7f, 4.7f, 1.7f,
                8.5f, 3.4f, 3.9f, 7.8f,
                2.5f, 6.3f, 2.0f, 5.1f,
            )
        )

        assertTrue(m.buffer.isReadOnly)
        assertFalse(m.writableBuffer.isReadOnly)
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
            MutableMatrix4f(floatArrayOf(1.1f, 7.2f, 5.4f, 8.2f))
        }
    }

    @Test
    fun shouldProperlyConstructMatrixFromBuffer() {
        val buf = newFloatBuffer(16)
        buf.mapInplace { i, _ -> i.toFloat() * 7.7f }
        val m = MutableMatrix4f(buf)

        assertTrue(m.buffer.isReadOnly)
        assertFalse(m.writableBuffer.isReadOnly)

        buf.mapInplace { i, _ -> i.toFloat() * 2.1f } // this will also change m's content

        assertEquals(
            m.toString(),
            """
            | 0.00  8.40 16.80 25.20 |
            | 2.10 10.50 18.90 27.30 |
            | 4.20 12.60 21.00 29.40 |
            | 6.30 14.70 23.10 31.50 |
            """.trimIndent()
        )

        // Passing a read-only buffer to the c'tor is expected to fail
        assertFails { MutableMatrix4f(buf.asReadOnlyBuffer()) }
    }

    @Test
    fun shouldProperlySetIdentity() {
        val m = MutableMatrix4f { it.toFloat() * 2.1f }

        assertTrue(m.buffer.isReadOnly)
        assertFalse(m.writableBuffer.isReadOnly)

        m.setIdentity()
        assertEquals(
            m.toString(),
            """
            | 1.00 0.00 0.00 0.00 |
            | 0.00 1.00 0.00 0.00 |
            | 0.00 0.00 1.00 0.00 |
            | 0.00 0.00 0.00 1.00 |
            """.trimIndent()
        )
    }

    @Test
    fun shouldProperlyConstructMatrixFromAnother() {
        val m1 = MutableMatrix4f { it.toFloat() * 2.1f }
        val m2 = MutableMatrix4f(m1) // copies the values

        assertTrue(m1.buffer.isReadOnly)
        assertTrue(m2.buffer.isReadOnly)
        assertFalse(m1.writableBuffer.isReadOnly)
        assertFalse(m2.writableBuffer.isReadOnly)
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
    fun shouldProperlySetScaling() {
        val m = MutableMatrix4f { it.toFloat() * 2.1f }
        m.setScaling(101.1f, -42.0f, 1.5f) // expected to overwrite non-scaling members
        assertEquals(
            m.toString(),
            """
            | 101.10   0.00 0.00 0.00 |
            |   0.00 -42.00 0.00 0.00 |
            |   0.00   0.00 1.50 0.00 |
            |   0.00   0.00 0.00 1.00 |
            """.trimIndent()
        )

        m.setScaling(-3.0, 12.0, 900.0) // here we test Doubles
        assertEquals(
            m.toString(),
            """
            | -3.00  0.00   0.00 0.00 |
            |  0.00 12.00   0.00 0.00 |
            |  0.00  0.00 900.00 0.00 |
            |  0.00  0.00   0.00 1.00 |
            """.trimIndent()
        )

        m.setScaling(Point3f(5, 6, 7)) // here we test Point3f
        assertEquals(
            m.toString(),
            """
            | 5.00 0.00 0.00 0.00 |
            | 0.00 6.00 0.00 0.00 |
            | 0.00 0.00 7.00 0.00 |
            | 0.00 0.00 0.00 1.00 |
            """.trimIndent()
        )
    }

    @Test
    fun shouldProperlySetTranslation() {
        val m = MutableMatrix4f { it.toFloat() * 2.1f }
        m.setTranslation(101.2f, -42.2f, 1.2f) // expected to overwrite non-translation members
        assertEquals(
            m.toString(),
            """
            | 1.00 0.00 0.00 101.20 |
            | 0.00 1.00 0.00 -42.20 |
            | 0.00 0.00 1.00   1.20 |
            | 0.00 0.00 0.00   1.00 |
            """.trimIndent()
        )

        m.setTranslation(42.0, 7.0, -50.5) // here we test Doubles
        assertEquals(
            m.toString(),
            """
            | 1.00 0.00 0.00  42.00 |
            | 0.00 1.00 0.00   7.00 |
            | 0.00 0.00 1.00 -50.50 |
            | 0.00 0.00 0.00   1.00 |
            """.trimIndent()
        )

        m.setTranslation(Point3f(9, 8, 7)) // here we test Point3f
        assertEquals(
            m.toString(),
            """
            | 1.00 0.00 0.00 9.00 |
            | 0.00 1.00 0.00 8.00 |
            | 0.00 0.00 1.00 7.00 |
            | 0.00 0.00 0.00 1.00 |
            """.trimIndent()
        )
    }

    @Test
    fun shouldProperlySetRotationX() {
        val m = MutableMatrix4f { it.toFloat() * 2.1f }
        m.setRotationX(0.42f) // expected to overwrite non-rotation members
        assertEquals(
            m.toString(),
            """
            | 1.00  0.00 0.00 0.00 |
            | 0.00  0.91 0.41 0.00 |
            | 0.00 -0.41 0.91 0.00 |
            | 0.00  0.00 0.00 1.00 |
            """.trimIndent()
        )

        m.setRotationX(0.77) // here we test Double
        assertEquals(
            m.toString(),
            """
            | 1.00  0.00 0.00 0.00 |
            | 0.00  0.72 0.70 0.00 |
            | 0.00 -0.70 0.72 0.00 |
            | 0.00  0.00 0.00 1.00 |
            """.trimIndent()
        )
    }

    @Test
    fun shouldProperlySetRotationY() {
        val m = MutableMatrix4f { it.toFloat() * 2.1f }
        m.setRotationY(0.42f) // expected to overwrite non-rotation members
        assertEquals(
            m.toString(),
            """
            | 0.91 0.00 -0.41 0.00 |
            | 0.00 1.00  0.00 0.00 |
            | 0.41 0.00  0.91 0.00 |
            | 0.00 0.00  0.00 1.00 |
            """.trimIndent()
        )

        m.setRotationY(0.77) // here we test Double
        assertEquals(
            m.toString(),
            """
            | 0.72 0.00 -0.70 0.00 |
            | 0.00 1.00  0.00 0.00 |
            | 0.70 0.00  0.72 0.00 |
            | 0.00 0.00  0.00 1.00 |
            """.trimIndent()
        )
    }

    @Test
    fun shouldProperlySetRotationZ() {
        val m = MutableMatrix4f { it.toFloat() * 2.1f }
        m.setRotationZ(0.42f) // expected to overwrite non-rotation members
        assertEquals(
            m.toString(),
            """
            | 0.91 -0.41 0.00 0.00 |
            | 0.41  0.91 0.00 0.00 |
            | 0.00  0.00 1.00 0.00 |
            | 0.00  0.00 0.00 1.00 |
            """.trimIndent()
        )

        m.setRotationZ(0.77) // here we test Double
        assertEquals(
            m.toString(),
            """
            | 0.72 -0.70 0.00 0.00 |
            | 0.70  0.72 0.00 0.00 |
            | 0.00  0.00 1.00 0.00 |
            | 0.00  0.00 0.00 1.00 |
            """.trimIndent()
        )
    }

    @Test
    fun shouldProperlyMultiplyTwoTranslations() {
        val m1 = MutableMatrix4f().apply { setTranslation(0.42f, 0.33f, 0.12f) }
        val m2 = MutableMatrix4f().apply { setTranslation(0.94f, 0.25f, 0.56f) }
        val m3 = m1 * m2
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
        val m1 = MutableMatrix4f().apply { setScaling(0.42f, 0.33f, 0.12f) }
        val m2 = MutableMatrix4f().apply { setScaling(0.94f, 0.25f, 0.56f) }
        val m3 = m1.newMultiplied(m2)
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
    fun shouldProperlyMultiplyInplace() {
        val m1 = MutableMatrix4f().apply { setScaling(0.42f, 0.33f, 0.12f) }
        val m2 = MutableMatrix4f().apply { setScaling(0.94f, 0.25f, 0.56f) }
        m1.multiply(m2) // modifies m1, leaves m2 untouched
        assertEquals(
            m1.toString(),
            """
            | 0.39 0.00 0.00 0.00 |
            | 0.00 0.08 0.00 0.00 |
            | 0.00 0.00 0.07 0.00 |
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
    }

    @Test
    fun shouldProperlyImplementTimesAssign() {
        val m1 = MutableMatrix4f(
            floatArrayOf(
                1.1f, 7.2f, 5.4f, 8.2f, // column!
                4.2f, 6.7f, 4.7f, 1.7f,
                8.5f, 3.4f, 3.9f, 7.8f,
                2.5f, 6.3f, 2.0f, 5.1f,
            )
        )
        m1 *= Matrix4f(
            floatArrayOf(
                9.2f, 9.8f, 4.3f, 5.8f,
                5.2f, 8.6f, 1.9f, 2.9f,
                2.4f, 0.9f, 2.6f, 6.6f,
                4.8f, 1.4f, 5.1f, 7.2f,
            )
        )

        // Verified this result with an online matrix calculator
        assertEquals(
            m1.toString(),
            """
            | 102.33  65.24 45.02  72.51 |
            | 183.06 119.79 73.73 106.64 |
            | 124.11  81.71 40.53  66.79 |
            | 155.22  86.87 75.15 118.24 |
            """.trimIndent()
        )
    }

    @Test
    fun shouldProperlyCopyFloatBuffer() {
        val m = MutableMatrix4f { it.toFloat() * 2.1f }
        val buf = m.toNewFloatBuffer()
        m.setIdentity() // modifies m.buffer

        assertEquals(
            m.buffer.toList().joinToString(", ") { it.toPrecision(1) },
            "1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, 0.0, 0.0, 0.0, 1.0"
        )

        assertEquals(
            buf.toList().joinToString(", ") { it.toPrecision(1) },
            "0.0, 2.1, 4.2, 6.3, 8.4, 10.5, 12.6, 14.7, 16.8, 18.9, 21.0, 23.1, 25.2, 27.3, 29.4, 31.5"
        )
    }

    @Test
    fun shouldProperlyCallEachElement() {
        val m = MutableMatrix4f { it.toFloat() * 2.1f }
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
    fun shouldProperlyMapInplace() {
        val m = MutableMatrix4f { it.toFloat() * 2.1f }
        m.mapInplace { i, value ->
            1000.0f * i + value
        }
        assertEquals(
            m.toString(),
            """
            |    0.00 4008.40  8016.80 12025.20 |
            | 1002.10 5010.50  9018.90 13027.30 |
            | 2004.20 6012.60 10021.00 14029.40 |
            | 3006.30 7014.70 11023.10 15031.50 |
            """.trimIndent()
        )
    }

    @Test
    fun shouldProperlyScaleTranslate() {
        val translation = Point3f(2.0f, 3.0f, 7.0f)
        val scaling = Point3f(9.0f, 11.0f, 13.0f)

        // First we do it one by one.

        val m1 = MutableMatrix4f().apply { setTranslation(translation) }
        val m2 = MutableMatrix4f().apply { setScaling(scaling) }
        m1 *= m2 // this will first scale, then translate
        val result1 = m1.toString()

        assertEquals(
            result1,
            """
            | 9.00  0.00  0.00 2.00 |
            | 0.00 11.00  0.00 3.00 |
            | 0.00  0.00 13.00 7.00 |
            | 0.00  0.00  0.00 1.00 |
            """.trimIndent()
        )

        // Then we check that setScaleTranslation yields the same result.

        val m3 = MutableMatrix4f().apply { setScaleTranslation(scaling, translation) }
        assertEquals(result1, m3.toString())
    }

    @Test
    fun shouldProperlyComputeTheLerpedValues() {
        val m1 = MutableMatrix4f(
            floatArrayOf(
                1.1f, 7.2f, 5.4f, 8.2f,
                4.2f, 6.7f, 4.7f, 1.7f,
                8.5f, 3.4f, 3.9f, 7.8f,
                2.5f, 6.3f, 2.0f, 5.1f,
            )
        )
        val m2 = MutableMatrix4f(
            floatArrayOf(
                9.2f, 9.8f, 4.3f, 5.8f,
                5.2f, 8.6f, 1.9f, 2.9f,
                2.4f, 0.9f, 2.6f, 6.6f,
                4.8f, 1.4f, 5.1f, 7.2f,
            )
        )
        val m3 = MutableMatrix4f().apply { setLerped(m1, m2, 0.5f) }
        assertEquals(
            m3.toString(),
            """
            | 5.15 4.70 5.45 3.65 |
            | 8.50 7.65 2.15 3.85 |
            | 4.85 3.30 3.25 3.55 |
            | 7.00 2.30 7.20 6.15 |
            """.trimIndent()
        )

        // Here we check that it's OK to set the first argument to the destination.
        val m4 = MutableMatrix4f(m1) // backup
        m1.setLerped(m1, m2, 0.5f)
        assertEquals(
            m1.toString(),
            """
            | 5.15 4.70 5.45 3.65 |
            | 8.50 7.65 2.15 3.85 |
            | 4.85 3.30 3.25 3.55 |
            | 7.00 2.30 7.20 6.15 |
            """.trimIndent()
        )

        // Here we check that it's OK to set the second argument to the destination.
        m1.set(m4) // restore
        m4.set(m2) // backup
        m2.setLerped(m1, m2, 0.5f)
        assertEquals(
            m2.toString(),
            """
            | 5.15 4.70 5.45 3.65 |
            | 8.50 7.65 2.15 3.85 |
            | 4.85 3.30 3.25 3.55 |
            | 7.00 2.30 7.20 6.15 |
            """.trimIndent()
        )

        m2.set(m4) // restore
        m3.setLerped(m1, m2, 0.0f) // sets m3 to m1
        assertEquals(
            m3.toString(),
            """
            | 1.10 4.20 8.50 2.50 |
            | 7.20 6.70 3.40 6.30 |
            | 5.40 4.70 3.90 2.00 |
            | 8.20 1.70 7.80 5.10 |
            """.trimIndent()
        )

        m3.setLerped(m1, m2, 1.0f) // sets m3 to m2
        assertEquals(
            m3.toString(),
            """
            | 9.20 5.20 2.40 4.80 |
            | 9.80 8.60 0.90 1.40 |
            | 4.30 1.90 2.60 5.10 |
            | 5.80 2.90 6.60 7.20 |
            """.trimIndent()
        )
    }

    @Test
    fun shouldProperlyMergeTwoMatrices() {
        val m1 = Matrix4f(
            floatArrayOf(
                1.1f, 7.2f, 5.4f, 8.2f,
                4.2f, 6.7f, 4.7f, 1.7f,
                8.5f, 3.4f, 3.9f, 7.8f,
                2.5f, 6.3f, 2.0f, 5.1f,
            )
        )
        val m2 = Matrix4f(
            floatArrayOf(
                9.2f, 9.8f, 4.3f, 5.8f,
                5.2f, 8.6f, 1.9f, 2.9f,
                2.4f, 0.9f, 2.6f, 6.6f,
                4.8f, 1.4f, 5.1f, 7.2f,
            )
        )
        val m3 = MutableMatrix4f()

        m3.setMerged(m1, m2) { v1, v2 ->
            v1 * 1000.0f + v2
        }

        assertEquals(
            m3.toString(),
            """
            | 1109.20 4205.20 8502.40 2504.80 |
            | 7209.80 6708.60 3400.90 6301.40 |
            | 5404.30 4701.90 3902.60 2005.10 |
            | 8205.80 1702.90 7806.60 5107.20 |
            """.trimIndent()
        )
    }

    @Test
    fun shouldProperlyTranspose() {
        val m = MutableMatrix4f(
            floatArrayOf(
                1.1f, 7.2f, 5.4f, 8.2f,
                4.2f, 6.7f, 4.7f, 1.7f,
                8.5f, 3.4f, 3.9f, 7.8f,
                2.5f, 6.3f, 2.0f, 5.1f,
            )
        )
        m.transpose()
        assertEquals(
            m.toString(),
            """
            | 1.10 7.20 5.40 8.20 |
            | 4.20 6.70 4.70 1.70 |
            | 8.50 3.40 3.90 7.80 |
            | 2.50 6.30 2.00 5.10 |
            """.trimIndent()
        )
        m.transpose()
        assertEquals(
            m.toString(),
            """
            | 1.10 4.20 8.50 2.50 |
            | 7.20 6.70 3.40 6.30 |
            | 5.40 4.70 3.90 2.00 |
            | 8.20 1.70 7.80 5.10 |
            """.trimIndent()
        )
    }
}
