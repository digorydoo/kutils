@file:Suppress("unused")

package ch.digorydoo.kutils.string

fun indentLines(s: String, indent: Int = 1, trim: Boolean = true): String {
    val prefix = Array(indent) { "   " }.joinToString("")
    return s.split("\n")
        .joinToString("\n") { "$prefix$it" }
        .let { if (trim) it.trim() else it }
}

fun indentLines(list: List<String>, indent: Int = 0): String =
    indentLines(list.toTypedArray(), indent)

fun indentLines(arr: Array<String>, indent: Int = 0): String {
    val p1 = Array(indent) { "   " }.joinToString("")
    val p2 = Array(indent + 1) { "   " }.joinToString("")
    return arr.mapIndexed { i, entry ->
        when (i) {
            0, arr.size - 1 -> "${p1}$entry"
            else -> "${p2}$entry"
        }
    }.joinToString("\n")
}
