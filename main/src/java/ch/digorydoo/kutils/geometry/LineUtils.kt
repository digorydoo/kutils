@file:Suppress("unused")

package ch.digorydoo.kutils.geometry

import ch.digorydoo.kutils.vector.MutableVector2f
import ch.digorydoo.kutils.vector.Vector2f

// UNTESTED
fun checkIntersection(
    startOfLineA: Vector2f,
    endOfLineA: Vector2f,
    startOfLineB: Vector2f,
    endOfLineB: Vector2f,
    intersection: MutableVector2f? = null,
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
    intersection: MutableVector2f? = null,
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
