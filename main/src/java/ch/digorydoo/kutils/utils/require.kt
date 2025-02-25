@file:Suppress("unused")

package ch.digorydoo.kutils.utils

fun <T> T?.requireNotNull(ctx: String): T & Any {
    requireNotNull(this) { "$ctx: unexpectedly null" }
    return this
}

fun <T> List<T>.requireExactlyOne(ctx: String): T {
    require(this.size == 1) { "$ctx: expected exactly 1, but got ${this.size}" }
    return this[0]
}

fun String.requireHash(ctx: String): String {
    require(this.startsWith("#")) { "$ctx: Expected # prefix, but got: $this" }
    return this.substring(1)
}
