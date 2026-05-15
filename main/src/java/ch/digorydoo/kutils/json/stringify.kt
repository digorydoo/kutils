package ch.digorydoo.kutils.json

import java.util.*

class StringifyException(msg: String): Exception(msg)

private class StringifyOptions(indent: Int, val newlines: Boolean, val spaceAfterColon: Boolean) {
    val stringIndent = if (indent <= 0) "" else " ".repeat(indent)
}

/**
 * Formats an object in the same way how JSON.stringify does in JavaScript. I don't use Klaxon for stringification for
 * these reasons: Klaxon.toJsonString(x) does not support pretty-printing, yet it is not properly minified either.
 * JsonObject.toJsonString(prettyPrint) does support pretty-printing, but does not allow controlling the indentation
 * and needs normal objects to be converted to a JsonObject first. Moreover, my own implementation does not rely on any
 * library at all and is very light-weight.
 *
 * This function does not support Klaxon's automatic data class serialization facility. If you want to use that, use
 * Klaxon's own toJsonString instead. Note, however, that Reflection-based serialization and de-serialization is very
 * slow compared to the direct approach, which has a notable effect when dealing with large data structures.
 *
 * This function sorts map keys when the map type does not guarantee ordering. On the JVM, Map and MutableMap are
 * typically backed by LinkedHashMap, which preserves insertion order. This isn't true for Maps built via buildMap,
 * though; the keys of those maps will be sorted. If you work with Klaxon's JsonObject for parsing, you may pass
 * JsonObjects to stringify() as it implements Map. JsonObject is not derived from LinkedHashMap, and its keys will be
 * sorted.
 *
 * @param serializable The entity you wish to serialize to JSON
 * @param indent Number of spaces to indent each level of hierarchy. Special values:
 *    0 = minified, i.e. no indentation, no spaces after colon, no newlines;
 *    -1 = flat, i.e. no indentation, no spaces after colon, but keep the newlines (better for diffs).
 */
fun stringify(serializable: Any?, indent: Int = 0): String {
    require(indent <= 10) { "Indentation too large" }
    val options = StringifyOptions(
        indent = indent,
        newlines = indent != 0,
        spaceAfterColon = indent > 0,
    )
    return stringifyUnknown(serializable, "", options)
}

private fun stringifyString(s: String) = "\"${jsonEncode(s)}\""

private fun stringifyUnknown(serializable: Any?, currentIndent: String, options: StringifyOptions): String =
    buildString {
        when (serializable) {
            null -> append("null")
            is String -> append(stringifyString(serializable))
            is Char -> append("\"${jsonEncode(serializable.toString())}\"")
            is Boolean -> append(if (serializable) "true" else "false")
            is Int -> append("$serializable")
            is Long -> append("$serializable")
            is Short -> append("$serializable")
            is UShort -> append("$serializable")
            is Byte -> append("$serializable")
            is UByte -> append("$serializable")
            is Float -> append(if (serializable.isFinite()) "$serializable" else "null") // no NaN in JSON
            is Double -> append(if (serializable.isFinite()) "$serializable" else "null")

            is Array<*> -> stringifyArrayLike(serializable.toList(), currentIndent, options)
            is List<*> -> stringifyArrayLike(serializable, currentIndent, options)
            is Set<*> -> stringifyArrayLike(serializable, currentIndent, options)
            is SortedMap<*, *> -> stringifyMapLike(serializable.entries, currentIndent, options)
            is LinkedHashMap<*, *> -> stringifyMapLike(serializable.entries, currentIndent, options)

            is Map<*, *> -> {
                // We come here for Klaxon's JsonObject, but not usually a map created via mapOf() or mutableMapOf(),
                // because they are usually backed by LinkedHashMap on the JVM.
                val sorted = serializable.entries.sortedBy {
                    it.key as? String ?: throw StringifyException("Key in map is not String: ${it.key}")
                }
                stringifyMapLike(sorted, currentIndent, options)
            }

            else -> throw StringifyException("Don't know how to serialize ${serializable::class.java.name}")
        }
    }

private fun StringBuilder.stringifyArrayLike(
    serializable: Collection<*>,
    currentIndent: String,
    options: StringifyOptions,
) {
    val nextLevelIndent = currentIndent + options.stringIndent

    if (serializable.isEmpty()) {
        append("[]")
    } else {
        append("[")
        if (options.newlines) append("\n")

        var first = true

        serializable.forEach { value ->
            if (first) {
                first = false
            } else {
                append(",")
                if (options.newlines) append("\n")
            }

            append(nextLevelIndent)
            append(stringifyUnknown(value, nextLevelIndent, options))
        }

        if (options.newlines) append("\n")
        append(currentIndent)
        append("]")
    }
}

private fun StringBuilder.stringifyMapLike(
    serializable: Collection<Map.Entry<*, *>>,
    currentIndent: String,
    options: StringifyOptions,
) {
    val nextLevelIndent = currentIndent + options.stringIndent

    if (serializable.isEmpty()) {
        append("{}")
    } else {
        append("{")
        if (options.newlines) append("\n")

        var first = true

        serializable.forEach { (key, value) ->
            if (key !is String) throw StringifyException("Key is not a String: $key")

            if (first) {
                first = false
            } else {
                append(",")
                if (options.newlines) append("\n")
            }

            append(nextLevelIndent)
            append(stringifyString(key) + ":")
            if (options.spaceAfterColon) append(" ")
            append(stringifyUnknown(value, nextLevelIndent, options))
        }

        if (options.newlines) append("\n")
        append(currentIndent)
        append("}")
    }
}
