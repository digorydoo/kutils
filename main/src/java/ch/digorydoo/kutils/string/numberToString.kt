@file:Suppress("unused")

package ch.digorydoo.kutils.string

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.round

fun withPercent(n: Int, total: Int, padding: Int = 4) = when (n > 0) {
    true -> "${lpad(n, toLen = padding)} (${100 * n / total}%)"
    false -> lpad(n, toLen = padding)
}

fun Float.toPercent(precision: Int = 1) = when (this) {
    0f -> "0%"
    1f -> "100%"
    else -> "${(100f * this).toFixed(precision)}%"
}

fun Double.toPercent(precision: Int = 1) = when (this) {
    0.0 -> "0%"
    1.0 -> "100%"
    else -> "${(100.0 * this).toFixed(precision)}%"
}

fun Float.toFixed(precision: Int) =
    this.toDouble().toFixed(precision)

/**
 * Converts the value to String using a fixed number of digits after the comma. The result will never use scientific
 * notation. On the JVM, you might also use ".${precision}f".format(value), but that form might result in scientific
 * notation, and it does not work on Kotlin/native, while this function does.
 * @param precision The desired number of digits after the comma
 */
fun Double.toFixed(precision: Int): String {
    require(precision >= 0)

    if (precision == 0) {
        return round(this).toLong().toString()
    }

    val sign = if (this < 0) "-" else ""
    val absVal = abs(this)
    val factor = 10.0.pow(precision)
    val scaled = round(absVal * factor)

    if (!scaled.isFinite() || scaled > Long.MAX_VALUE || scaled < Long.MIN_VALUE) {
        return "<overflow>"
    }

    val scaledL = scaled.toLong()
    val intPart = scaledL / factor.toLong()
    val fracPart = scaledL % factor.toLong()
    val fracStr = fracPart.toString().padStart(precision, '0')
    return "$sign$intPart.$fracStr"
}

fun Int.toDelimited(delimiter: String = "'", chunkSize: Int = 3): String =
    toString().reversed().chunked(chunkSize).joinToString(delimiter).reversed()
