@file:Suppress("unused")

package ch.digorydoo.kutils.string

import kotlin.math.abs
import kotlin.math.pow
import kotlin.math.round

fun withPercent(n: Int, total: Int) = when (n > 0) {
    true -> "${lpad(n)} (${100 * n / total}%)"
    false -> lpad(n)
}

fun Float.toPrecision(precision: Int) =
    this.toDouble().toPrecision(precision)

/**
 * In the JVM, you can use ".${precision}f".format(value), but this extension function here works even in
 * Kotlin/native, and it also avoids scientific notation altogether.
 * @param precision The desired number of digits after the comma
 */
fun Double.toPrecision(precision: Int): String {
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
