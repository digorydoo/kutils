@file:Suppress("unused")

package ch.digorydoo.kutils.utils

import ch.digorydoo.kutils.vector.MutableVector2f
import ch.digorydoo.kutils.vector.MutableVector3f
import ch.digorydoo.kutils.vector.Vector2f
import ch.digorydoo.kutils.vector.Vector3f

fun <T> Array<T>.mapInplace(transform: (value: T) -> T) {
    forEachIndexed { i, value ->
        this[i] = transform(value)
    }
}

fun <T> Array<T>.mapInplaceIndexed(transform: (index: Int, value: T) -> T) {
    forEachIndexed { i, value ->
        this[i] = transform(i, value)
    }
}

fun FloatArray.forEachVector3f(lambda: (Vector3f) -> Unit) {
    val sz = size
    require(sz % 3 == 0) { "forEachVector3f: size ($sz) not divisible by 3" }

    val vec = MutableVector3f()

    var i = 0

    while (i < sz) {
        vec.x = this[i++]
        vec.y = this[i++]
        vec.z = this[i++]
        lambda(vec)
    }
}

fun FloatArray.forEachVector2f(lambda: (Vector2f) -> Unit) {
    val sz = size
    require(sz % 2 == 0) { "forEachVector2f: size ($sz) not divisible by 2" }

    val vec = MutableVector2f()

    var i = 0

    while (i < sz) {
        vec.x = this[i++]
        vec.y = this[i++]
        lambda(vec)
    }
}
