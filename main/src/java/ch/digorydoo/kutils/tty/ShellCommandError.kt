package ch.digorydoo.kutils.tty

@Suppress("unused")
/**
 * Throw this exception to terminate a shell command while displaying a message.
 * You should catch this exception from your main(), write the message to stderr, and exit with an error code.
 */
class ShellCommandError(msg: String, val exitCode: Int = 1): Exception(msg)
