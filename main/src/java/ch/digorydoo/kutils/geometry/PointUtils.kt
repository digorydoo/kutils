package ch.digorydoo.kutils.geometry

import ch.digorydoo.kutils.point.MutablePoint2f
import ch.digorydoo.kutils.point.MutablePoint3f
import ch.digorydoo.kutils.point.Point2f
import ch.digorydoo.kutils.point.Point3f

fun FloatArray.forEachPoint3f(lambda: (pt: Point3f) -> Unit) {
    val sz = size
    require(sz % 3 == 0) { "forEachPoint3f: size ($sz) not divisible by 3" }

    val pt = MutablePoint3f()

    var i = 0

    while (i < sz) {
        pt.x = this[i++]
        pt.y = this[i++]
        pt.z = this[i++]
        lambda(pt)
    }
}

fun FloatArray.forEachPoint2f(lambda: (pt: Point2f) -> Unit) {
    val sz = size
    require(sz % 2 == 0) { "forEachPoint2f: size ($sz) not divisible by 2" }

    val pt = MutablePoint2f()

    var i = 0

    while (i < sz) {
        pt.x = this[i++]
        pt.y = this[i++]
        lambda(pt)
    }
}
