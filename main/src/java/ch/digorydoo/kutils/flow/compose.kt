@file:Suppress("unused")

package ch.digorydoo.kutils.flow

/**
 * @return A function that takes one argument of type T and returns the result of the chained
 *    calls to the given functions
 */
fun <T> compose(vararg funs: (T) -> T): ((T) -> T) {
    return { x ->
        var result = x

        for (f in funs) {
            result = f(result)
        }

        result
    }
}

