@file:Suppress("unused", "MemberVisibilityCanBePrivate")

package ch.digorydoo.kutils.utils

import ch.digorydoo.kutils.cjk.Unicode
import java.io.File
import java.io.FileWriter
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

object Log {
    enum class Severity(val level: Int) {
        INFO(0),
        WARNING(1),
        ERROR(2),
    }

    class Tag(val name: String)

    enum class TtyOutput { EVERYTHING_STDOUT, INFO_TO_STDOUT_OTHER_TO_STDERR }

    var enabled = true
    var logFile: File? = null; private set
    private var fileLogLevel = Severity.WARNING

    // By default, we output everything to stdout, because IntelliJ adds its own colours to stderr, which interferes
    // with our colours.
    var ttyOutput = TtyOutput.EVERYTHING_STDOUT

    private var ttyLogLevel = Severity.INFO
    private var ttyColours = true

    fun setOptions(
        ttyLogLevel: Severity? = null,
        ttyOutput: TtyOutput? = null,
        ttyColours: Boolean? = null,
        fileLogLevel: Severity? = null,
        logFile: String? = null, // use empty string to turn off logging to file
    ) {
        ttyLogLevel?.let { this.ttyLogLevel = it }
        ttyOutput?.let { this.ttyOutput = it }
        ttyColours?.let { this.ttyColours = it }
        logFile?.let { this.logFile = File(it) }
        fileLogLevel?.let { this.fileLogLevel = it }
    }

    @OptIn(ExperimentalTime::class) // necessary when compiling for Android
    fun truncateLogFile() {
        val logFile = logFile ?: return
        try {
            FileWriter(logFile).apply {
                write("Log started on ${Clock.System.now()}\n")
                close()
            }
        } catch (e: Exception) {
            logToTty(Severity.WARNING, "Truncating log file (${logFile.path}) failed: ${e.message}")
        }
    }

    fun info(tag: Tag, msg: String) = log(tag, Severity.INFO, msg)
    fun warn(tag: Tag, msg: String) = log(tag, Severity.WARNING, msg)
    fun error(tag: Tag, msg: String) = log(tag, Severity.ERROR, msg)

    private fun log(tag: Tag, severity: Severity, msg: String) {
        if (!enabled) return
        val msgWithTag = "${tag.name}: $msg"
        logToTty(severity, msgWithTag)
        logToFile(severity, msgWithTag)
    }

    private fun logToTty(severity: Severity, msg: String) {
        if (severity.level < ttyLogLevel.level) return

        val stream = when (ttyOutput) {
            TtyOutput.EVERYTHING_STDOUT -> System.out
            TtyOutput.INFO_TO_STDOUT_OTHER_TO_STDERR -> if (severity == Severity.INFO) System.out else System.err
        }

        val colour = when {
            !ttyColours -> null
            else -> when (severity) {
                Severity.INFO -> null
                Severity.WARNING -> "${Unicode.ESCAPE}[33m"
                Severity.ERROR -> "${Unicode.ESCAPE}[31m"
            }
        }

        // IntelliJ fails to detect an error or warning if the text is not prefixed with "Error" or "Warning, so we use
        // println here to make sure the prefix will be at the start of the line.
        colour?.let { stream.println(it) }

        val prefix = when (severity) {
            Severity.INFO -> ""
            Severity.WARNING -> "Warning: "
            Severity.ERROR -> "Error: "
        }

        stream.print("$prefix$msg")
        if (colour != null) stream.print("${Unicode.ESCAPE}[0m")
        stream.println()
    }

    @OptIn(ExperimentalTime::class) // necessary when compiling for Android
    private fun logToFile(severity: Severity, msg: String) {
        if (severity.level < fileLogLevel.level) return
        val logFile = logFile ?: return

        try {
            val stamp = Clock.System.now().toString() // formatted as ISO-8601
            logFile.appendText("$stamp [${severity.name}] $msg\n")
        } catch (e: Exception) {
            logToTty(Severity.ERROR, "Failed to write log entry to file (${logFile.path}): ${e.message}")
            logToTty(Severity.WARNING, "Turning off logging to file for subsequent log lines")
            this.logFile = null
        }
    }
}
