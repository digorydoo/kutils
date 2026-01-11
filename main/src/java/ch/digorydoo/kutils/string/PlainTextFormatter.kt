package ch.digorydoo.kutils.string

/**
 * Class that formats the given text to a certain width assuming a mono-spaced font.
 */
class PlainTextFormatter(private val maxWidth: Int) {
    /**
     * @return The formatted text, or the given text itself if there is not enough room
     */
    fun format(text: String, indent: Int = 0): String {
        return formatOrNull(text, indent) ?: text
    }

    /**
     * @return The formatted text, or null if there is not enough room
     */
    fun formatOrNull(text: String, indent: Int = 0): String? {
        if (text.isEmpty()) {
            return text
        }

        val maxLineLen = maxWidth - indent

        if (maxLineLen < MIN_REASONABLE_WIDTH) {
            return null
        }

        val sindent = Array(indent) { ' ' }.joinToString("")
        val result = StringBuilder()
        val line = StringBuilder()
        var wordStart = 0
        var isInWord = false
        var newlinesSinceWord = 0

        text.forEachIndexed { i, c ->
            when (c) {
                ' ', '\t', '\r' -> {
                    if (isInWord) {
                        if (line.isNotEmpty()) line.append(' ')
                        line.append(text.substring(wordStart, i))
                        isInWord = false
                    }
                }
                '\n' -> {
                    if (isInWord) {
                        if (line.isNotEmpty()) line.append(' ')
                        line.append(text.substring(wordStart, i))
                        isInWord = false
                    }

                    if (++newlinesSinceWord == 2) {
                        if (result.isNotEmpty()) result.append('\n')
                        result.append(sindent)
                        result.append(line.toString())
                        line.clear()
                    }
                }
                else -> {
                    newlinesSinceWord = 0

                    if (!isInWord) {
                        isInWord = true
                        wordStart = i
                    }

                    if (line.length + i - wordStart + 2 > maxLineLen) {
                        if (result.isNotEmpty()) result.append('\n')

                        if (line.isNotEmpty()) {
                            result.append(sindent)
                            result.append(line.toString())
                            line.clear()
                        } else {
                            // Word is longer than maxLineLen, force a break here
                            result.append(text.substring(wordStart, i + 1))
                            isInWord = false
                        }
                    } else if (isSoftBreak(c)) {
                        if (wordStart < i) {
                            if (line.isNotEmpty() && !isSoftBreak(line.last())) line.append(' ')
                            line.append(text.substring(wordStart, i + 1))
                            isInWord = false
                        }
                    }
                }
            }
        }

        if (isInWord) {
            if (line.isNotEmpty()) line.append(' ')
            line.append(text.substring(wordStart, text.length))
        }

        if (line.isNotEmpty()) {
            if (result.isNotEmpty()) result.append('\n')
            result.append(sindent)
            result.append(line.toString())
        }

        return result.toString()
    }

    private fun isSoftBreak(c: Char) = when (c) {
        '-', '+', '=', '/', ',', ':', ';', '?', '!' -> true
        else -> false
    }

    companion object {
        private const val MIN_REASONABLE_WIDTH = 8
    }
}
