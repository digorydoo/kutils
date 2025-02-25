@file:Suppress("unused")

package ch.digorydoo.kutils.geometry

import ch.digorydoo.kutils.point.Point2f
import ch.digorydoo.kutils.point.Point3f

private const val EPSILON = 0.0000001

fun signedAreaOfTriangle(a: Point2f, b: Point2f, c: Point2f) =
    signedAreaOfTriangle(a.x, a.y, b.x, b.y, c.x, c.y)

fun signedAreaOfTriangle(ax: Float, ay: Float, bx: Float, by: Float, cx: Float, cy: Float) =
    0.5f * (-by * cx + ay * (-bx + cx) + ax * (by - cy) + bx * cy)

// https://stackoverflow.com/questions/2049582/how-to-determine-if-a-point-is-in-a-2d-triangle
fun isPtInTriangle(px: Float, py: Float, ax: Float, ay: Float, bx: Float, by: Float, cx: Float, cy: Float): Boolean {
    val area = signedAreaOfTriangle(ax, ay, bx, by, cx, cy)
    val s = 1 / (2 * area) * (ay * cx - ax * cy + (cy - ay) * px + (ax - cx) * py)
    if (s < 0 || s > 1) return false
    val t = 1 / (2 * area) * (ax * by - ay * bx + (ay - by) * px + (bx - ax) * py)
    return t >= 0 && t <= 1 && s + t <= 1
}

class CheckRayIntersectionResult {
    var intersects = false
    var rayLength = 0.0
}

// https://en.wikipedia.org/wiki/M%C3%B6ller%E2%80%93Trumbore_intersection_algorithm
fun checkRayIntersectsTriangle(
    rayOrigin: Point3f,
    rayDir: Point3f, // needs to be normalized
    vertex0: Point3f,
    vertex1: Point3f,
    vertex2: Point3f,
    result: CheckRayIntersectionResult,
) {
    val edge1 = vertex1 - vertex0
    val edge2 = vertex2 - vertex0
    val h = rayDir.newCrossed(edge2)
    val a: Double = edge1.dotProduct(h).toDouble()

    if (a > -EPSILON && a < EPSILON) {
        // the ray is parallel to the triangle
        result.intersects = false
        return
    }

    val f = 1.0 / a
    val s = rayOrigin - vertex0
    val u = f * s.dotProduct(h)

    if (u < 0.0 || u > 1.0) {
        result.intersects = false
        return
    }

    val q = s.newCrossed(edge1)
    val v = f * rayDir.dotProduct(q)

    if (v < 0.0 || u + v > 1.0) {
        result.intersects = false
        return
    }

    val t: Double = f * edge2.dotProduct(q)

    if (t <= EPSILON) {
        result.intersects = false
        return
    }

    result.intersects = true
    result.rayLength = t
}
