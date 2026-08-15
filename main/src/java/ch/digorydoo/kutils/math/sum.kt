package ch.digorydoo.kutils.math

// Kotlin already has Collection<T>.sumOf(block) for various T, but for some odd reason, Float is missing. The same
// goes for Array<T>.sumOf(block): It is not available for T=Float. I tried adding a Float variant, but I get strange
// overloading issues at the calling site. Therefore, I had to call the functions differently according to return type.

inline fun <T> Collection<T>.sumOfFloat(lambda: (t: T) -> Float): Float =
    fold(0f) { result, entry ->
        result + lambda(entry)
    }

inline fun <T> Collection<T>.sumOfDouble(lambda: (t: T) -> Double): Double =
    fold(0.0) { result, entry ->
        result + lambda(entry)
    }

inline fun <T> Collection<T>.sumOfInt(lambda: (t: T) -> Int): Int =
    fold(0) { result, entry ->
        result + lambda(entry)
    }

inline fun <T> Collection<T>.sumOfLong(lambda: (t: T) -> Long): Long =
    fold(0L) { result, entry ->
        result + lambda(entry)
    }

inline fun <T> Array<out T>.sumOfFloat(lambda: (t: T) -> Float): Float =
    fold(0f) { result, entry ->
        result + lambda(entry)
    }

inline fun <T> Array<out T>.sumOfDouble(lambda: (t: T) -> Double): Double =
    fold(0.0) { result, entry ->
        result + lambda(entry)
    }

inline fun <T> Array<out T>.sumOfInt(lambda: (t: T) -> Int): Int =
    fold(0) { result, entry ->
        result + lambda(entry)
    }

inline fun <T> Array<out T>.sumOfLong(lambda: (t: T) -> Long): Long =
    fold(0L) { result, entry ->
        result + lambda(entry)
    }

fun Collection<Float>.sumStartingFrom(startIdx: Int): Float {
    var result = 0f
    forEachIndexed { idx, value ->
        if (idx >= startIdx) result += value
    }
    return result
}

fun Collection<Double>.sumStartingFrom(startIdx: Int): Double {
    var result = 0.0
    forEachIndexed { idx, value ->
        if (idx >= startIdx) result += value
    }
    return result
}

fun Collection<Int>.sumStartingFrom(startIdx: Int): Int {
    var result = 0
    forEachIndexed { idx, value ->
        if (idx >= startIdx) result += value
    }
    return result
}

fun Collection<Long>.sumStartingFrom(startIdx: Int): Long {
    var result = 0L
    forEachIndexed { idx, value ->
        if (idx >= startIdx) result += value
    }
    return result
}

fun Array<Float>.sumStartingFrom(startIdx: Int): Float {
    var result = 0f
    for (i in startIdx ..< size) {
        result += this[i]
    }
    return result
}

fun Array<Double>.sumStartingFrom(startIdx: Int): Double {
    var result = 0.0
    for (i in startIdx ..< size) {
        result += this[i]
    }
    return result
}

fun Array<Int>.sumStartingFrom(startIdx: Int): Int {
    var result = 0
    for (i in startIdx ..< size) {
        result += this[i]
    }
    return result
}

fun Array<Long>.sumStartingFrom(startIdx: Int): Long {
    var result = 0L
    for (i in startIdx ..< size) {
        result += this[i]
    }
    return result
}

fun <T> Collection<T>.sumOfFloatStartingFrom(startIdx: Int, lambda: (t: T) -> Float): Float {
    var result = 0f
    forEachIndexed { idx, value ->
        if (idx >= startIdx) result += lambda(value)
    }
    return result
}

fun <T> Collection<T>.sumOfDoubleStartingFrom(startIdx: Int, lambda: (t: T) -> Double): Double {
    var result = 0.0
    forEachIndexed { idx, value ->
        if (idx >= startIdx) result += lambda(value)
    }
    return result
}

fun <T> Collection<T>.sumOfIntStartingFrom(startIdx: Int, lambda: (t: T) -> Int): Int {
    var result = 0
    forEachIndexed { idx, value ->
        if (idx >= startIdx) result += lambda(value)
    }
    return result
}

fun <T> Collection<T>.sumOfLongStartingFrom(startIdx: Int, lambda: (t: T) -> Long): Long {
    var result = 0L
    forEachIndexed { idx, value ->
        if (idx >= startIdx) result += lambda(value)
    }
    return result
}

fun <T> Array<T>.sumOfFloatStartingFrom(startIdx: Int, lambda: (t: T) -> Float): Float {
    var result = 0f
    for (i in startIdx ..< size) {
        result += lambda(this[i])
    }
    return result
}

fun <T> Array<T>.sumOfDoubleStartingFrom(startIdx: Int, lambda: (t: T) -> Double): Double {
    var result = 0.0
    for (i in startIdx ..< size) {
        result += lambda(this[i])
    }
    return result
}

fun <T> Array<T>.sumOfIntStartingFrom(startIdx: Int, lambda: (t: T) -> Int): Int {
    var result = 0
    for (i in startIdx ..< size) {
        result += lambda(this[i])
    }
    return result
}

fun <T> Array<T>.sumOfLongStartingFrom(startIdx: Int, lambda: (t: T) -> Long): Long {
    var result = 0L
    for (i in startIdx ..< size) {
        result += lambda(this[i])
    }
    return result
}
