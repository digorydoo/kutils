package ch.digorydoo.kutils.utils

import ch.digorydoo.kutils.string.appendLPadded
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.number
import kotlinx.datetime.toInstant
import kotlinx.datetime.toLocalDateTime
import java.nio.file.attribute.FileTime
import java.util.Locale
import kotlin.time.Clock
import kotlin.time.Duration
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

/**
 * Moment is completely timezone-agnostic and is meant for the cases when you do not need to deal with timezones. It is
 * kept simple by design; if you need anything more sophisticated, use Kotlin Instant, DateTime or related classes
 * instead. Instances of Moment are immutable.
 */
@OptIn(ExperimentalTime::class) // necessary when compiling for Android
class Moment(
    val year: Int, // e.g. 2026
    val month: Int, // 1 .. 12
    val day: Int, // 1 .. 31
    val hour: Int, // 0 .. 23
    val minute: Int, // 0 .. 59
    val second: Int, // 0 .. 59
): Comparable<Moment> {
    init {
        require(year in 1900 .. 3000) { "year out of bounds" }
        require(month in 1 .. 12) { "month out of bounds" }
        require(day in 1 .. 31) { "day out of bounds" }
        require(hour in 0 .. 23) { "hour out of bounds" }
        require(minute in 0 .. 59) { "minute out of bounds" }
        require(second in 0 .. 59) { "second out of bounds" }
        require(year < 3000 || (month == 1 && day == 1 && hour == 0 && minute == 0 && second == 0)) {
            "Moment beyond DISTANT_FUTURE not allowed"
        }
    }

    /**
     * @return The moment formatted as yyyy-MM-dd
     */
    fun formatAsZoneAgnosticDate(): String = buildString {
        append(year)
        append('-')
        appendLPadded(month, 2, '0')
        append('-')
        appendLPadded(day, 2, '0')
    }

    /**
     * @return The moment formatted as yyyy-MM-dd HH:mm:ss
     */
    fun formatAsZoneAgnosticDateTime(): String = buildString {
        append(year)
        append('-')
        appendLPadded(month, 2, '0')
        append('-')
        appendLPadded(day, 2, '0')
        append(' ')
        appendLPadded(hour, 2, '0')
        append(':')
        appendLPadded(minute, 2, '0')
        append(':')
        appendLPadded(second, 2, '0')
    }

    /**
     * @return The moment formatted as yyyyMMdd-HHmmss (ideal when using inside a filename, etc.)
     */
    fun formatAsZoneAgnosticDateTimeCompact(): String = buildString {
        append(year)
        appendLPadded(month, 2, '0')
        appendLPadded(day, 2, '0')
        append('-')
        appendLPadded(hour, 2, '0')
        appendLPadded(minute, 2, '0')
        appendLPadded(second, 2, '0')
    }

    fun formatLocalized(
        style: LocalizedFormatStyle = LocalizedFormatStyle.DATE_TIME,
        locale: Locale = Locale.getDefault(),
    ): String = toLocalDateTime().formatLocalized(style, locale)

    override fun toString() = formatAsZoneAgnosticDateTime()

    override fun compareTo(other: Moment) = when {
        year < other.year -> -1
        year > other.year -> 1
        month < other.month -> -1
        month > other.month -> 1
        day < other.day -> -1
        day > other.day -> 1
        hour < other.hour -> -1
        hour > other.hour -> 1
        minute < other.minute -> -1
        minute > other.minute -> 1
        second < other.second -> -1
        second > other.second -> 1
        else -> 0
    }

    override fun equals(other: Any?) =
        this === other || (other is Moment &&
            year == other.year &&
            month == other.month &&
            day == other.day &&
            hour == other.hour &&
            minute == other.minute &&
            second == other.second)

    override fun hashCode(): Int {
        // Verified with ChatGPT that this is better than perfect packing into Long and then truncating.
        var r = second
        r = 31 * r + minute
        r = 31 * r + hour
        r = 31 * r + day
        r = 31 * r + month
        r = 31 * r + year
        return r
    }

    fun toLocalDateTime() = LocalDateTime(
        year = year,
        month = month,
        day = day,
        hour = hour,
        minute = minute,
        second = second,
        nanosecond = 0
    )

    fun toInstant(zone: TimeZone = TimeZone.currentSystemDefault()) =
        toLocalDateTime().toInstant(zone)

    operator fun plus(duration: Duration): Moment {
        val zone = TimeZone.currentSystemDefault()
        val instant = toInstant(zone) + duration
        val dt = instant.toLocalDateTime(zone)
        return dt.toMoment()
    }

    operator fun minus(duration: Duration): Moment {
        val zone = TimeZone.currentSystemDefault()
        val instant = toInstant(zone) - duration
        val dt = instant.toLocalDateTime(zone)
        return dt.toMoment()
    }

    fun atMidnight() = Moment(
        year = year,
        month = month,
        day = day,
        hour = 0,
        minute = 0,
        second = 0,
    )

    companion object {
        val DISTANT_PAST = parseZoneAgnosticOrNull("1900-01-01 00:00:00")!!
        val DISTANT_FUTURE = parseZoneAgnosticOrNull("3000-01-01 00:00:00")!!

        fun now(): Moment {
            val instant = Clock.System.now()
            val dt = instant.toLocalDateTime(TimeZone.currentSystemDefault())
            return dt.toMoment()
        }

        /**
         * Parses the input. The format is determined from length and needs to be one of the formats supported by Moment.
         */
        fun parseZoneAgnosticOrNull(input: String): Moment? = when (input.length) {
            10 -> parseZoneAgnosticDateOrNull(input)      // yyyy-MM-dd
            15 -> parseZoneAgnosticDateTimeCompact(input) // yyyyMMdd-HHmmss
            19 -> parseZoneAgnosticDateTimeOrNull(input)  // yyyy-MM-dd HH:mm:ss
            else -> null
        }

        private fun parseZoneAgnosticDateOrNull(input: String): Moment? {
            // To be sure that no zone computation happens at all, we parse everything ourselves.
            val parts = input.split('-').takeIf { it.size == 3 } ?: return null
            val year = parts[0].takeIf { it.length == 4 }?.toIntOrNull() ?: return null
            val month = parts[1].takeIf { it.length == 2 }?.toIntOrNull() ?: return null
            val day = parts[2].takeIf { it.length == 2 }?.toIntOrNull() ?: return null
            return Moment(
                year = year,
                month = month,
                day = day,
                hour = 0,
                minute = 0,
                second = 0,
            )
        }

        private fun parseZoneAgnosticDateTimeOrNull(input: String): Moment? {
            val chunks = input.split(' ').takeIf { it.size == 2 } ?: return null

            val dateParts = chunks[0].split('-').takeIf { it.size == 3 } ?: return null
            val year = dateParts[0].takeIf { it.length == 4 }?.toIntOrNull() ?: return null
            val month = dateParts[1].takeIf { it.length == 2 }?.toIntOrNull() ?: return null
            val day = dateParts[2].takeIf { it.length == 2 }?.toIntOrNull() ?: return null

            val timeParts = chunks[1].split(':').takeIf { it.size == 3 } ?: return null
            val hour = timeParts[0].takeIf { it.length == 2 }?.toIntOrNull() ?: return null
            val minute = timeParts[1].takeIf { it.length == 2 }?.toIntOrNull() ?: return null
            val second = timeParts[2].takeIf { it.length == 2 }?.toIntOrNull() ?: return null

            return Moment(
                year = year,
                month = month,
                day = day,
                hour = hour,
                minute = minute,
                second = second,
            )
        }

        private fun parseZoneAgnosticDateTimeCompact(input: String): Moment? {
            val chunks = input.split('-').takeIf { it.size == 2 } ?: return null

            val dateParts = chunks[0].takeIf { it.length == 8 } ?: return null
            val year = dateParts.substring(0 .. 3).toIntOrNull() ?: return null
            val month = dateParts.substring(4 .. 5).toIntOrNull() ?: return null
            val day = dateParts.substring(6 .. 7).toIntOrNull() ?: return null

            val timeParts = chunks[1].takeIf { it.length == 6 } ?: return null
            val hour = timeParts.substring(0 .. 1).toIntOrNull() ?: return null
            val minute = timeParts.substring(2 .. 3).toIntOrNull() ?: return null
            val second = timeParts.substring(4 .. 5).toIntOrNull() ?: return null

            return Moment(
                year = year,
                month = month,
                day = day,
                hour = hour,
                minute = minute,
                second = second,
            )
        }

        fun LocalDateTime.toMoment() = Moment(
            year = year,
            month = month.number,
            day = day,
            hour = hour,
            minute = minute,
            second = second,
        )

        fun FileTime.toMoment(): Moment {
            val instant = Instant.fromEpochMilliseconds(toMillis())
            val dt = instant.toLocalDateTime(TimeZone.currentSystemDefault())
            return dt.toMoment()
        }
    }
}
