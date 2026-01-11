package ch.digorydoo.kutils.tty

@Suppress("unused")
/**
 * Throw this Throwable to terminate a shell command while displaying a message.
 * Implemented as an Error indicating that you should not generally catch it except from main().
 */
class ShellCommandError(msg: String, val exitCode: Int = 1): Error(msg)
