@file:Suppress("unused")

package ch.digorydoo.kutils.json

internal fun jsonEncode(s: String) =
    s.replace("\\", "\\\\")
        .replace("\"", "\\\"")
        .replace("\n", "\\n")
        .replace("\r", "\\r")
        .replace("\t", "\\t")

internal fun jsonDecode(s: String) =
    s.replace("\\t", "\t")
        .replace("\\r", "\r")
        .replace("\\n", "\n")
        .replace("\\\"", "\"")
        .replace("\\\\", "\\")

fun String.toJSON() = "\"${jsonEncode(this)}\""
fun Boolean.toJSON() = if (this) "true" else "false"
fun Int.toJSON() = "$this"
fun Float.toJSON() = if (this.isFinite()) "$this" else "null" // JSON cannot handle NaN, +Inf, -Inf
fun Double.toJSON() = if (this.isFinite()) "$this" else "null" // JSON cannot handle NaN, +Inf, -Inf

fun Array<String>.toJSON() = "[${this.joinToString(",") { "\"${jsonEncode(it)}\"" }}]"
fun Collection<String>.toJSON() = "[${this.joinToString(",") { "\"${jsonEncode(it)}\"" }}]"
