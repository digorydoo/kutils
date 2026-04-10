package ch.digorydoo.kutils.logging

import ch.digorydoo.kutils.logging.Log.LogStrategy
import ch.digorydoo.kutils.logging.Log.Severity
import ch.digorydoo.kutils.logging.Log.Tag
import ch.digorydoo.kutils.utils.Moment
import java.io.File
import java.io.FileWriter
import kotlin.time.Clock
import kotlin.time.ExperimentalTime

@Suppress("unused")
@OptIn(ExperimentalTime::class) // necessary when compiling for Android
class FileLogStrategy(val file: File, var minSeverity: Severity = Severity.WARNING): LogStrategy {
    var enabled = true

    fun truncateLogFile() {
        try {
            FileWriter(file).apply {
                write("Log started on ${Clock.System.now()}\n")
                close()
            }
        } catch (e: Exception) {
            System.err.println("Truncating log file (${file.path}) failed: ${e.message}")
            enabled = false
        }
    }

    override fun log(tag: Tag, severity: Severity, msg: String) {
        if (severity.level < minSeverity.level || !enabled) return

        try {
            val stamp = Moment.now().formatAsZoneAgnosticDateTime()
            file.appendText("$stamp [${severity.name}] ${tag.name}: $msg\n")
        } catch (e: Exception) {
            System.err.println("Failed to write log entry to file (${file.path}): ${e.message}")
            enabled = false
        }
    }
}
