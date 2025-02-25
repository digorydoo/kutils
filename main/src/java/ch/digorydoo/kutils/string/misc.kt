@file:Suppress("unused")

package ch.digorydoo.kutils.string

fun trunc(s: String, maxLen: Int = 34, ellipsis: String = "...") =
    when (s.length < maxLen) {
        true -> s
        false -> s.slice(0 ..< maxLen - ellipsis.length) + ellipsis
    }

fun dots(s: String): String {
    var result = "$s "

    while (result.length < 37) {
        result +=
            if (result.length % 2 == 0) {
                " "
            } else {
                "."
            }
    }

    return result
}

fun initCap(s: String) =
    when (s.isEmpty()) {
        true -> s
        false -> "${s[0].uppercase()}${s.substring(1).lowercase()}"
    }

private enum class Case { LOWER, UPPER, DELIM, OTHER }

fun snakeCase(s: String): String {
    var prev: Case? = null
    var result = ""

    for (c in s) {
        val lc = c.lowercase()
        val uc = c.uppercase()

        val cur = when (lc) {
            uc -> when (c) {
                '-' -> Case.DELIM
                '_' -> Case.DELIM
                else -> Case.OTHER
            }
            "$c" -> Case.LOWER
            else -> Case.UPPER
        }

        result += when {
            cur == Case.DELIM -> "-"
            cur == Case.UPPER && (prev == Case.LOWER || prev == Case.OTHER) -> "-$lc"
            cur == Case.LOWER && prev == Case.OTHER -> "-$lc"
            else -> lc
        }

        prev = cur
    }

    return result
}

// Can be used from toString to get a shorter version of the standard object stringification.
// We expect classNameWithPck to be of the form: package.className@address
fun zapPackageName(classNameWithPck: String): String {
    val i = classNameWithPck.lastIndexOf('.')
    return if (i < 0) classNameWithPck else classNameWithPck.substring(i + 1 ..< classNameWithPck.length)
}
