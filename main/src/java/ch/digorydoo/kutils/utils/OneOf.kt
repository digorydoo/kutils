@file:Suppress("unused")

package ch.digorydoo.kutils.utils

/**
 * This class provides a simple mechanism that simulates a union type of two alternatives. Usage is as follows:
 *
 *     val result: OneOf<String, Int> = when (kind) {
 *         0 -> OneOf.First("string")
 *         else -> OneOf.Second(kind)
 *     }
 *
 *     if (result is OneOf.First) println(result.first)
 *     if (result is OneOf.Second) println(result.second)
 *
 *     when (result) {
 *         is OneOf.First -> doSomething(result.first)
 *         is OneOf.Second -> doSomething(result.second)
 *     }
 */
sealed class OneOf<A, B> {
    data class First<A, B>(val first: A): OneOf<A, B>()
    data class Second<A, B>(val second: B): OneOf<A, B>()
}
