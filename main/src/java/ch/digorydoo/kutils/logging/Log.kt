package ch.digorydoo.kutils.logging

@Suppress("unused")
object Log {
    enum class Severity(val level: Int) {
        DEBUG(0),
        INFO(1),
        WARNING(2),
        ERROR(3),
    }

    class Tag(val name: String)

    enum class TtyOutput { EVERYTHING_STDOUT, INFO_TO_STDOUT_OTHER_TO_STDERR }

    // You can use TtyLogStrategy and/or FileLogStrategy. It is also possible to write your own strategy, e.g. to
    // redirect logging to logcat on Android.
    interface LogStrategy {
        fun log(tag: Tag, severity: Severity, msg: String)
    }

    var strategies = mutableListOf<LogStrategy>(TtyLogStrategy())

    inline fun <reified T: LogStrategy> get() =
        strategies.find { it is T }

    var enabled = true

    fun debug(tag: Tag, msg: String) = log(tag, Severity.DEBUG, msg)
    fun info(tag: Tag, msg: String) = log(tag, Severity.INFO, msg)
    fun warn(tag: Tag, msg: String) = log(tag, Severity.WARNING, msg)
    fun error(tag: Tag, msg: String) = log(tag, Severity.ERROR, msg)

    private fun log(tag: Tag, severity: Severity, msg: String) {
        if (!enabled) return
        strategies.forEach { it.log(tag, severity, msg) }
    }
}
