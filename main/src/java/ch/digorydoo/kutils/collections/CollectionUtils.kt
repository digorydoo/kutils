package ch.digorydoo.kutils.collections

fun <T, N: Number> Collection<T>.averageOrNull(lambda: (t: T) -> N): Float? =
    takeIf { isNotEmpty() }
        ?.fold(0.0f) { result, t -> result + lambda(t).toFloat() }
        ?.let { it / size }

class ValueAndWeight<T: Number>(val value: T, val weight: T)

fun <T> Collection<T>.weightedAverageOrNull(lambda: (index: Int, t: T) -> ValueAndWeight<Float>): Float? {
    if (isEmpty()) return null

    var weightedSum = 0.0f
    var sumOfWeights = 0.0f

    forEachIndexed { index, item ->
        val vaw = lambda(index, item)
        weightedSum += vaw.value * vaw.weight
        sumOfWeights += vaw.weight
    }

    return weightedSum / sumOfWeights
}
