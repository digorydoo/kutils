@file:Suppress("unused")

package ch.digorydoo.kutils.file

import ch.digorydoo.kutils.json.jsonDecode
import ch.digorydoo.kutils.json.jsonEncode
import ch.digorydoo.kutils.json.toJSON
import java.io.File

/**
 * @param file The file to search
 * @param needle The string to search for
 * @param caseInsensitive true=ignore case
 * @param handleMatch Callback; true=continue, false=abort
 */
fun grep(file: File, needle: String, caseInsensitive: Boolean, handleMatch: (line: String, idx: Int) -> Boolean) {
    val n = if (caseInsensitive) needle.lowercase() else needle
    grep(
        file,
        checkMatch = { if (caseInsensitive) it.lowercase().contains(n) else it.contains(n) },
        handleMatch,
    )
}

/**
 * @param file The file to search
 * @param checkMatch Callback that should return true for each matching line
 * @param handleMatch Callback; true=continue, false=abort
 */
fun grep(file: File, checkMatch: (line: String) -> Boolean, handleMatch: (line: String, idx: Int) -> Boolean) {
    file.bufferedReader().use {
        val iter = it.lineSequence().iterator()
        var idx = 1

        while (iter.hasNext()) {
            val line = iter.next()

            if (checkMatch(line)) {
                if (!handleMatch(line, idx)) {
                    break
                }
            }

            idx++
        }
    }
}

/**
 * Grep for a specifically formatted JSON object or array. The first line is expected to consist of an opening brace or
 * opening bracket only. The last line of an object is expected to have an underscore as the key, a null value, and the
 * closing brace. The last line of an array is expected to consist of a null value and the closing bracket.
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
    val n = if (matchWholeEntriesOnly) {
        needle.toJSON() // puts quotes around the encoded string
    } else {
        jsonEncode(needle)
    }

    // TODO support array content

    grep(file, n, caseInsensitive = caseInsensitive) { line, idx ->
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

                if (!value.lowercase().contains(needle)) {
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
    }
}
