@file:Suppress("unused")

package ch.digorydoo.kutils.utils

fun <T> T.ifNull(lambda: () -> Unit): T {
    if (this == null) {
        lambda()
    }
    return this
}

fun <T> T.ifNotNull(lambda: (t: T & Any) -> Unit): T {
    if (this != null) {
        lambda(this)
    }
    return this
}
