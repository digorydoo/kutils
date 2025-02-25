@file:Suppress("unused")

package ch.digorydoo.kutils.flow

/**
 * @return The value after passing it through the chain of functions
 */
fun <T> flow(initVal: T, vararg funs: (T) -> T): T {
    return compose(*funs)(initVal)
}
