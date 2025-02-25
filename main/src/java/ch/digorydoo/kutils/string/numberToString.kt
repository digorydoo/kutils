@file:Suppress("unused")

package ch.digorydoo.kutils.string

import kotlin.math.abs
import kotlin.math.floor
import kotlin.math.pow
import kotlin.math.roundToInt

fun withPercent(n: Int, total: Int) = when (n > 0) {
    true -> "${lpad(n)} (${100 * n / total}%)"
    false -> lpad(n)
}

// This works in Kotlin/native as well. In the JVM, you can also use ".3f".format(value)
fun Float.toPrecision(precision: Int) =
    this.toDouble().toPrecision(precision)

fun Double.toPrecision(precision: Int) =
    if (precision < 1) {
        "${this.roundToInt()}"
    } else {
        val p = 10.0.pow(precision)
        val v = (abs(this) * p).roundToInt()
        val i = floor(v / p)
        var f = "${floor(v - (i * p)).toInt()}"
        while (f.length < precision) f = "0$f"
        val s = if (this < 0) "-" else ""
        "$s${i.toInt()}.$f"
    }

fun Int.toDelimited(delimiter: String = "'", chunkSize: Int = 3): String =
    toString().reversed().chunked(chunkSize).joinToString(delimiter).reversed()
