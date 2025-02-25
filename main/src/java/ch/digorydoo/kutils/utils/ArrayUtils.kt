@file:Suppress("unused")

package ch.digorydoo.kutils.utils

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
