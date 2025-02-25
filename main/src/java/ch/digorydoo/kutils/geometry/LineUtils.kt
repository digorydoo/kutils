@file:Suppress("unused")

package ch.digorydoo.kutils.geometry

import ch.digorydoo.kutils.point.MutablePoint2f
import ch.digorydoo.kutils.point.Point2f

// UNTESTED
fun checkIntersection(
    startOfLineA: Point2f,
    endOfLineA: Point2f,
    startOfLineB: Point2f,
    endOfLineB: Point2f,
    intersection: MutablePoint2f? = null,
) = checkIntersection(
    startOfLineA.x, startOfLineA.y, endOfLineA.x, endOfLineA.y,
    startOfLineB.x, startOfLineB.y, endOfLineB.x, endOfLineB.y,
    intersection
)

// UNTESTED
fun checkIntersection(
    x1: Float,
    y1: Float,
    x2: Float,
    y2: Float,
    x3: Float,
    y3: Float,
    x4: Float,
    y4: Float,
    intersection: MutablePoint2f? = null,
): Boolean {
    val ta = ((x4 - x3) * (y1 - y3) - (y4 - y3) * (x1 - x3)) / ((y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1))
    val tb = ((x2 - x1) * (y1 - y3) - (y2 - y1) * (x1 - x3)) / ((y4 - y3) * (x2 - x1) - (x4 - x3) * (y2 - y1))

    if (ta < 0 || ta > 1 || tb < 0 || tb > 1) {
        return false
    }

    intersection?.set(
        x1 + ta * (x2 - x1),
        y1 + ta * (y2 - y1),
    )
    return true
}
