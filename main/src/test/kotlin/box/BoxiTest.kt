package box

import ch.digorydoo.kutils.box.Boxi
import ch.digorydoo.kutils.point.Point3f
import kotlin.test.*

internal class BoxiTest {
    @Test
    fun shouldProperlyConstructFromFloats() {
        val b = Boxi(1.1f, 2.2f, 3.3f, 4.4f, 5.5f, 6.6f)
        assertEquals(b.x0, 1)
        assertEquals(b.y0, 2)
        assertEquals(b.z0, 3)
        assertEquals(b.x1, 4)
        assertEquals(b.y1, 5)
        assertEquals(b.z1, 6)
        assertEquals(b.xsize, 3)
        assertEquals(b.ysize, 3)
        assertEquals(b.zsize, 3)
    }

    @Test
    fun shouldProperlyConstructFromAnotherBoxi() {
        val b1 = Boxi(4, 5, 6, 7, 8, 9)
        val b2 = Boxi(b1)
        assertEquals(b1.x0, b2.x0)
        assertEquals(b1.y0, b2.y0)
        assertEquals(b1.z0, b2.z0)
        assertEquals(b1.x1, b2.x1)
        assertEquals(b1.y1, b2.y1)
        assertEquals(b1.z1, b2.z1)
        assertEquals(b1.xsize, b2.xsize)
        assertEquals(b1.ysize, b2.ysize)
        assertEquals(b1.zsize, b2.zsize)
    }

    @Test
    fun shouldProperlyComputeCentre() {
        val b = Boxi(7, 3, 8, 2, 5, 9)
        val c = b.centre()
        assertEquals(c.x, 4.5f)
        assertEquals(c.y, 4.0f)
        assertEquals(c.z, 8.5f)
    }

    @Test
    fun shouldCorrectlyCheckSameValues() {
        val b1 = Boxi(1, 2, 3, 4, 5, 6)
        val b2 = Boxi(3, 7, 5, 3, 2, 1)
        val b3 = Boxi(3, 7, 5, 3, 2, 1)
        assertTrue(b1.hasSameValues(b1))
        assertTrue(b2.hasSameValues(b2))
        assertTrue(b3.hasSameValues(b3))
        assertFalse(b1.hasSameValues(b2))
        assertFalse(b2.hasSameValues(b1))
        assertFalse(b1.hasSameValues(b3))
        assertFalse(b3.hasSameValues(b1))
        assertTrue(b2.hasSameValues(b3))
        assertTrue(b3.hasSameValues(b2))

        // Boxi does not implement operator equals:
        assertNotEquals(b2, b3)
        assertNotSame(b2, b3)
    }

    @Test
    fun shouldProperlyCheckContains() {
        val b = Boxi(3, 7, 2, 6, 9, 5)
        assertEquals(b.xsize, 3)
        assertEquals(b.ysize, 2)
        assertEquals(b.zsize, 3)

        assertTrue(b.contains(Point3f(3.0f, 7.0f, 2.0f)))

        assertTrue(b.contains(Point3f(5.9f, 7.0f, 2.0f)))
        assertFalse(b.contains(Point3f(6.0f, 7.0f, 2.0f)))
        assertFalse(b.contains(Point3f(2.9f, 7.0f, 2.0f)))

        assertTrue(b.contains(Point3f(3.0f, 8.9f, 2.0f)))
        assertFalse(b.contains(Point3f(3.0f, 9.0f, 2.0f)))
        assertFalse(b.contains(Point3f(3.0f, 6.9f, 2.0f)))

        assertTrue(b.contains(Point3f(3.0f, 7.0f, 4.9f)))
        assertFalse(b.contains(Point3f(3.0f, 7.0f, 5.0f)))
        assertFalse(b.contains(Point3f(3.0f, 7.0f, 1.9f)))
    }

    @Test
    fun shouldProperlyComputeDistance() {
        assertEquals(0.0f, Boxi().distanceFrom(Point3f(0.0f, 0.0f, 0.0f)))

        assertEquals(0.0f, Boxi(10, 20, 30, 40, 50, 60).distanceFrom(Point3f(20.0f, 30.0f, 40.0f)))
        assertEquals(13.0f, Boxi(11, 12, 13, 19, 18, 17).distanceFrom(Point3f(15, 15, 0)))
        assertEquals(12.0f, Boxi(11, 12, 13, 19, 18, 17).distanceFrom(Point3f(15, 0, 15)))
        assertEquals(11.0f, Boxi(11, 12, 13, 19, 18, 17).distanceFrom(Point3f(0, 15, 15)))

        assertEquals(3.74f, Boxi(1, 2, 3, 4, 5, 6).distanceFrom(Point3f(0, 0, 0)), absoluteTolerance = 0.1f)
        assertEquals(3.74f, Boxi(1, 2, 3, 4, 5, 6).distanceFrom(Point3f(0, 0, 9)), absoluteTolerance = 0.1f)
        assertEquals(3.74f, Boxi(1, 2, 3, 4, 5, 6).distanceFrom(Point3f(0, 7, 0)), absoluteTolerance = 0.1f)
        assertEquals(3.74f, Boxi(1, 2, 3, 4, 5, 6).distanceFrom(Point3f(0, 7, 9)), absoluteTolerance = 0.1f)
        assertEquals(3.74f, Boxi(1, 2, 3, 4, 5, 6).distanceFrom(Point3f(5, 0, 0)), absoluteTolerance = 0.1f)
        assertEquals(3.74f, Boxi(1, 2, 3, 4, 5, 6).distanceFrom(Point3f(5, 0, 9)), absoluteTolerance = 0.1f)
        assertEquals(3.74f, Boxi(1, 2, 3, 4, 5, 6).distanceFrom(Point3f(5, 7, 0)), absoluteTolerance = 0.1f)
        assertEquals(3.74f, Boxi(1, 2, 3, 4, 5, 6).distanceFrom(Point3f(5, 7, 9)), absoluteTolerance = 0.1f)
        assertEquals(46.01f, Boxi(1, 2, 3, 4, 5, 6).distanceFrom(Point3f(50, 5, 5)), absoluteTolerance = 0.1f)
    }
}
