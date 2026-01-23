@file:Suppress("unused")

package ch.digorydoo.kutils.string

fun lpad(s: String, toLen: Int = 4, padChar: Char = ' ') =
    when (s.length >= toLen) {
        true -> s
        false -> (Array(toLen - s.length) { padChar }).joinToString("") + s
    }

fun rpad(s: String, toLen: Int = 4, padChar: Char = ' ') =
    when (s.length >= toLen) {
        true -> s
        false -> s + (Array(toLen - s.length) { padChar }).joinToString("")
    }

fun lpad(n: Int, toLen: Int = 4, padChar: Char = ' ') =
    lpad("$n", toLen, padChar)

fun rpad(n: Int, toLen: Int = 4, padChar: Char = ' ') =
    rpad("$n", toLen, padChar)

fun lpadOrEmpty(n: Int, toLen: Int = 4, padChar: Char = ' ') =
    when (n) {
        0 -> (Array(toLen) { padChar }).joinToString("")
        else -> lpad(n, toLen, padChar)
    }

fun rpadOrEmpty(n: Int, toLen: Int = 4, padChar: Char = ' ') =
    when (n) {
        0 -> (Array(toLen) { padChar }).joinToString("")
        else -> rpad(n, toLen, padChar)
    }

fun StringBuilder.appendLPadded(n: Int, toLen: Int = 4, padChar: Char = ' ') {
    val sn = n.toString()
    repeat(toLen - sn.length) { append(padChar) }
    append(sn)
}

fun StringBuilder.appendRPadded(n: Int, toLen: Int = 4, padChar: Char = ' ') {
    val sn = n.toString()
    append(sn)
    repeat(toLen - sn.length) { append(padChar) }
}
