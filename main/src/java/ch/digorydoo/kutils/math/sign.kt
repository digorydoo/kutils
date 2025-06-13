package ch.digorydoo.kutils.math

/**
 * Signum function for Int. (Kotlin only has Float and Double.)
 * @return: -1, 0 or 1
 */
fun sign(value: Int) =
    when {
        value < 0 -> -1
        value > 0 -> 1
        else -> 0
    }
