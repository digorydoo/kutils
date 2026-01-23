package ch.digorydoo.kutils.utils

import kotlinx.datetime.*
import java.time.format.DateTimeFormatter
import java.time.format.FormatStyle
import java.time.format.TextStyle
import java.util.Locale
import kotlin.time.ExperimentalTime
import kotlin.time.Instant

enum class LocalizedFormatStyle(
    val weekday: Boolean = false,
    val date: Boolean = false,
    val time: Boolean = false,
    val seconds: Boolean = false,
) {
    DATE(date = true),
    DATE_TIME(date = true, time = true),
    DATE_TIME_WITH_SECONDS(date = true, time = true, seconds = true),
    TIME(time = true),
    TIME_WITH_SECONDS(time = true, seconds = true),
    WEEKDAY_DATE(weekday = true, date = true),
    WEEKDAY_DATE_TIME(weekday = true, date = true, time = true),
    WEEKDAY_DATE_TIME_WITH_SECONDS(weekday = true, date = true, time = true, seconds = true),
}

/**
 * Formats the given Instant as a localized string.
 */
@OptIn(ExperimentalTime::class) // necessary when compiling for Android
fun Instant.formatLocalized(
    style: LocalizedFormatStyle,
    locale: Locale = Locale.getDefault(),
    zone: TimeZone = TimeZone.currentSystemDefault(),
): String {
    return toLocalDateTime(zone).formatLocalized(style, locale)
}

/**
 * Formats the given LocalDateTime as a localized string.
 */
fun LocalDateTime.formatLocalized(
    style: LocalizedFormatStyle,
    locale: Locale = Locale.getDefault(),
): String {
    // The library kotlinx.datetime does not provide locale-aware formatting, therefore we use Java API.

    val dateStyle = FormatStyle.LONG
    val timeStyle = if (style.seconds) FormatStyle.MEDIUM else FormatStyle.SHORT

    val formatter: DateTimeFormatter = when {
        style.date && style.time -> DateTimeFormatter.ofLocalizedDateTime(dateStyle, timeStyle)
        style.date -> DateTimeFormatter.ofLocalizedDate(dateStyle)
        style.time -> DateTimeFormatter.ofLocalizedTime(timeStyle)
        else -> error("Internal error: LocalizedFormatStyle defined bad combination of flags")
    }

    val localizedFormatter = formatter.withLocale(locale)!!
    // val localDt = dt.toLocalDate()!! FIXME necessary for date?
    val formatted = toJavaLocalDateTime().format(localizedFormatter)

    return when {
        !style.weekday -> formatted
        else -> {
            val weekday = java.time.DayOfWeek.of(dayOfWeek.isoDayNumber).getDisplayName(TextStyle.FULL, locale)
            if (locale == Locale.JAPANESE || locale == Locale.JAPAN) {
                "$formatted（$weekday）"
            } else {
                "$weekday, $formatted"
            }
        }
    }
}

/**
 * @return The number of days a given Month has
 */
fun daysInMonth(month: Month, year: Int): Int = when (month) {
    Month.JANUARY, Month.MARCH, Month.MAY, Month.JULY,
    Month.AUGUST, Month.OCTOBER, Month.DECEMBER,
    -> 31

    Month.APRIL, Month.JUNE, Month.SEPTEMBER, Month.NOVEMBER -> 30

    Month.FEBRUARY -> if (year.isLeapYear()) 29 else 28
}

/**
 * @return true if and only if the year represented by the Int (yyyy) is a leap year
 */
fun Int.isLeapYear(): Boolean =
    (this % 4 == 0 && this % 100 != 0) || (this % 400 == 0)
