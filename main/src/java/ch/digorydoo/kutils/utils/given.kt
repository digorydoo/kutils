package ch.digorydoo.kutils.utils

/**
 * Kotlin's `if` requires parentheses, and there is no keyword between the closing parenthesis and the statement if
 * the statement isn't a block. If the statement starts with an opening parenthesis, this can become hard to read:
 *
 *    return if (px * qx + py * qy > 0) (a - b) * t else (a + b) * t
 *
 * With the following construct, we can write instead:
 *
 *    return { (a - b) * t } given (px * qx + py * qy > 0) otherwise { (a + b) * t }
 *
 * Unfortunately, the lambdas are required, otherwise both sides always get evaluated, which is not what we would
 * expect from a conditional expression. This makes the whole thing quite useless, because we could have used braces
 * in the original `if` statement to clarify it in the first place. Therefore, I keep this internal for now...
 */
internal class Given<T>(val left: () -> T, val condition: Boolean)

internal infix fun <T> (() -> T).given(condition: Boolean) = Given(this, condition)
internal inline infix fun <T> Given<T>.otherwise(crossinline right: () -> T) = if (condition) left() else right()
