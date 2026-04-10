package ch.digorydoo.kutils.logging

import ch.digorydoo.kutils.cjk.Unicode
import ch.digorydoo.kutils.logging.Log.LogStrategy
import ch.digorydoo.kutils.logging.Log.Severity
import ch.digorydoo.kutils.logging.Log.Tag
import ch.digorydoo.kutils.logging.Log.TtyOutput

class TtyLogStrategy(
    var minSeverity: Severity = Severity.DEBUG,
    var output: TtyOutput = TtyOutput.INFO_TO_STDOUT_OTHER_TO_STDERR,
    var colours: Boolean = true,
): LogStrategy {
    override fun log(tag: Tag, severity: Severity, msg: String) {
        if (severity.level < minSeverity.level) return

        val stream = when (output) {
            TtyOutput.EVERYTHING_STDOUT -> System.out
            TtyOutput.INFO_TO_STDOUT_OTHER_TO_STDERR -> if (severity == Severity.INFO) System.out else System.err
        }

        val colour = when {
            !colours -> null
            else -> when (severity) {
                Severity.DEBUG, Severity.INFO -> null
                Severity.WARNING -> "${Unicode.ESCAPE}[33m"
                Severity.ERROR -> "${Unicode.ESCAPE}[31m"
            }
        }

        // IntelliJ fails to detect an error or warning if the text is not prefixed with "Error" or "Warning, so we
        // use println here to make sure the prefix will be at the start of the line.
        colour?.let { stream.println(it) }

        val prefix = when (severity) {
            Severity.DEBUG, Severity.INFO -> ""
            Severity.WARNING -> "Warning: "
            Severity.ERROR -> "Error: "
        }

        stream.print("$prefix${tag.name}: $msg")
        if (colour != null) stream.print("${Unicode.ESCAPE}[0m")
        stream.println()
    }
}
