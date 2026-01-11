package ch.digorydoo.kutils.flow

/**
 * Lets you chain functions like this:
 *    val x = y into ::h into ::g into ::f
 *
 * is the same as:
 *    val x = f(g(h(y)))
 *
 * Note: infix operator has low priority:
 *     val x = (3 * 4) into { it + 2 } // result is 14
 *     val x = 3 * (4 into { it + 2 }) // result is 18
 *     val x = 3 * 4 into { it + 2 } // result is 14, not 18
 */
infix fun <A, B> A.into(lambda: (A) -> B): B = lambda(this)
