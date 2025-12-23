@file:Suppress("unused")

package ch.digorydoo.kutils.file

import ch.digorydoo.kutils.json.jsonDecode
import ch.digorydoo.kutils.json.jsonEncode
import ch.digorydoo.kutils.json.toJSON
import java.io.File

/**
 * @param file The file to search
 * @param checkIfLineMatches Callback that should return true for each matching line
 * @param handleMatch Callback; true=continue, false=abort
 */
fun grep(
    file: File,
    checkIfLineMatches: (line: String, idx: Int) -> Boolean,
    handleMatch: (line: String, idx: Int) -> Boolean,
) {
    file.bufferedReader().use {
        val iter = it.lineSequence().iterator()
        var idx = 1

        while (iter.hasNext()) {
            val line = iter.next()

            if (checkIfLineMatches(line, idx)) {
                if (!handleMatch(line, idx)) {
                    break
                }
            }

            idx++
        }
    }
}

/**
 * Grep for a specifically formatted JSON object or array. The first line is expected to consist of an opening brace.
 * The last line of an object is expected to have an underscore as the key, a null value, and the closing brace. Only
 * JSON objects are supported at the moment. Each line must consist of a key followed by an object.
 * @param file The file to search
 * @param checkIfLineMatches Callback that should return true for each matching line
 * @param checkIfValueMatches Callback that should return true for each matching line
 * @param warn Callback; true=continue, false=abort
 * @param handleMatch Callback; true=continue, false=abort
 */
fun grepJson(
    file: File,
    checkIfLineMatches: (line: String, idx: Int) -> Boolean,
    checkIfValueMatches: (value: String, idx: Int) -> Boolean,
    warn: (msg: String, idx: Int) -> Boolean,
    handleMatch: (key: String, value: String, idx: Int) -> Boolean,
) {
    grep(file, checkIfLineMatches) { line, idx ->
        val trimmed = line.trim()

        if (!trimmed.startsWith("\"")) {
            // This may happen on the first line if the needle was an opening brace.
            warn("Ignoring match on non-key line", idx)
        } else {
            val colonAt = trimmed.indexOf(":")

            if (colonAt < 0) {
                // This should not happen.
                warn("Missing colon", idx)
            } else {
                var value = trimmed.substring(colonAt + 1)

                if (!checkIfValueMatches(value, idx)) {
                    // This may happen if the needle was found in the key.
                    warn("Line seems to match in its key.", idx)
                } else {
                    val key = jsonDecode(trimmed.slice(1 ..< colonAt - 1).trim())

                    if (key == "_") {
                        // This happens on the last line if the needle was an underscore.
                        warn("Ignoring match on non-key line", idx)
                    } else if (!value.endsWith(",")) {
                        // This should not happen. The last line is expected to be the special underscore key.
                        warn("Value line unexpectedly does not end in a comma", idx)
                    } else {
                        value = value.slice(0 ..< value.length - 1) // remove comma
                        // We do not jsonDecode the value, because the caller may want to parse it.
                        handleMatch(key, value, idx)
                    }
                }
            }
        }

        true // continue
    }
}

/**
 * @param file The file to search
 * @param needle The string to search for
 * @param caseInsensitive true=ignore case
 * @param matchWholeEntriesOnly true=only report lines that contain the string within JSON quotes
 * @param warn Callback; true=continue, false=abort
 * @param handleMatch Callback; true=continue, false=abort
 */
fun grepJson(
    file: File,
    needle: String,
    caseInsensitive: Boolean,
    matchWholeEntriesOnly: Boolean,
    warn: (msg: String, idx: Int) -> Boolean,
    handleMatch: (key: String, value: String, idx: Int) -> Boolean,
) {
    // If matchWholeEntriesOnly is true, use toJSON() to put double quotes around the needle.
    // If matchWholeEntriesOnly is false, use jsonEncode() to encode any special characters inside the needle.

    val n = needle
        .let { if (matchWholeEntriesOnly) it.toJSON() else jsonEncode(needle) }
        .let { if (caseInsensitive) it.lowercase() else it }

    val checkMatch = { line: String, idx: Int ->
        if (caseInsensitive) line.lowercase().contains(n) else line.contains(n)
    }

    grepJson(
        file = file,
        checkIfLineMatches = checkMatch,
        checkIfValueMatches = checkMatch,
        warn = warn,
        handleMatch = handleMatch,
    )
}
