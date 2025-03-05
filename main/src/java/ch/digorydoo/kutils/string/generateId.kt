@file:Suppress("unused")

package ch.digorydoo.kutils.string

import kotlin.random.Random

/**
 * Characters that are generally useful for ids. Omitting characters that are easily confused visually.
 */
val ID_CHARS = arrayOf(
    'a', 'b', 'c', 'd', 'e', 'f', 'g', 'h', 'i', 'j', 'k', /* l */ 'm', 'n', 'o', 'p', 'q', 'r', 's', 't',
    'u', 'v', 'w', 'x', 'y', 'z',

    'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', /* I */ 'J', 'K', 'L', 'M', 'N', /* O */ 'P', 'Q', 'R', 'S', 'T',
    'U', 'V', 'W', 'X', 'Y', 'Z',

    // Numbers must come last (unless you change ID_CHARS_WITHOUT_NUMBER)
    /* 0, 1 */ '2', '3', '4', '5', '6', '7', '8', '9'
)

val ID_CHARS_WITHOUT_NUMBERS = ID_CHARS.slice(0 .. ID_CHARS.indexOf('Z'))

/**
 * @return a String of the given length; the first character is guaranteed not to be a number
 */
fun generateId(length: Int): String {
    require(length > 0)

    var id = ID_CHARS_WITHOUT_NUMBERS.let { it[Random.nextInt(it.size)] }.toString()

    if (length > 1) {
        (1 ..< length).forEach {
            id += ID_CHARS.let { it[Random.nextInt(it.size)] }
        }
    }

    return id
}
