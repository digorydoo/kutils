@file:Suppress("unused", "MemberVisibilityCanBePrivate", "SameParameterValue")

package ch.digorydoo.kutils.utils

import ch.digorydoo.kutils.cjk.IntlString
import java.text.DateFormat
import java.text.ParseException
import java.text.SimpleDateFormat
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * Moments are immutables. They combine the bloated Java API into one concise API:
 *    - Date
 *    - Calendar
 *    - DateUtils
 *    - SimpleDateFormat
 * As of Java 8, there is now a better API, but it's even more bloated:
 *    - Instant
 *    - Clock
 *    - LocalDate
 *    - LocalTime
 *    - LocalDateTime
 *    - ZonedDateTime
 *    - DateTimeFormatter
 *    - YearMonth
 *    - Period
 *    - ZoneOffset
 *    - OffsetDateTime
 */
class Moment {
    enum class U(val value: Int) {
        YEAR(Calendar.YEAR),
        MONTH(Calendar.MONTH),
        DAY(Calendar.DAY_OF_MONTH),
        HOUR(Calendar.HOUR),
        MINUTE(Calendar.MINUTE),
        SECOND(Calendar.SECOND)
    }

    private val date: Date

    constructor() {
        date = Date()
    }

    constructor(m: Moment) {
        date = m.date // a Moment should never change its date, so it's safe to pass it on
    }

    constructor(d: Date) {
        date = d.clone() as Date // Dates are mutable, so clone it
    }

    private constructor(d: Date, noClone: Boolean) {
        require(noClone) { "noClone must be true when calling this constructor" }
        date = d // caller ensures it's safe to use d without cloning it
    }

    /**
     * @return An integer, e.g. 1984
     */
    val year: Int
        get() {
            val c = Calendar.getInstance()
            c.time = date
            return c.get(Calendar.YEAR)
        }

    /**
     * @return An integer, 1..12, with 1 being January
     */
    val month: Int
        get() {
            val c = Calendar.getInstance()
            c.time = date
            return c.get(Calendar.MONTH) + 1
        }

    /**
     * @return An integer, 1..31
     */
    val dayOfMonth: Int
        get() {
            val c = Calendar.getInstance()
            c.time = date
            return c.get(Calendar.DAY_OF_MONTH)
        }

    /**
     * @return An integer, 1..7, with 1 being the representation of Sunday
     */
    val dayOfWeek: Int
        get() {
            val c = Calendar.getInstance()
            c.time = date
            return c.get(Calendar.DAY_OF_WEEK)
        }

    fun setMidnight(): Moment {
        val c = Calendar.getInstance()
        c.time = date
        c.set(Calendar.HOUR_OF_DAY, 0)
        c.set(Calendar.MINUTE, 0)
        c.set(Calendar.SECOND, 0)
        c.set(Calendar.MILLISECOND, 0)
        return Moment(c.time, true)
    }

    /**
     * @return A new moment with n units added to it
     */
    fun add(n: Int, unit: U): Moment {
        val c = Calendar.getInstance()
        c.time = date
        c.add(unit.value, n)
        return Moment(c.time, true)
    }

    /**
     * @return A new moment with n units subtracted from it
     */
    fun subtract(n: Int, unit: U): Moment {
        return add(-n, unit)
    }

    /**
     * @return true iff this moment is before the given moment
     */
    fun before(other: Moment): Boolean {
        return date.before(other.date)
    }

    /**
     * @return true iff this moment is after the given moment
     */
    fun after(other: Moment): Boolean {
        return date.after(other.date)
    }

    /*
     * @return the difference of the two dates with respect to the given time unit
     */
    fun diff(m: Moment, timeUnit: TimeUnit): Long {
        return diff(m.date, timeUnit)
    }

    /*
     * @return the difference of the two dates with respect to the given time unit
     */
    fun diff(date2: Date, timeUnit: TimeUnit): Long {
        val diff = date.time - date2.time
        return timeUnit.convert(diff, TimeUnit.MILLISECONDS)
    }

    /**
     * @return The moment formatted as a string
     */
    fun format(fmt: String, locale: Locale? = null): String {
        return SimpleDateFormat(fmt, locale ?: Locale.getDefault()).format(date)
    }

    /**
     * @return The moment formatted as a localized string
     */
    fun formatDateTimeLocalized(locale: Locale? = null, seconds: Boolean = false): String =
        DateFormat.getDateTimeInstance(
            DateFormat.LONG,
            if (seconds) DateFormat.MEDIUM else DateFormat.SHORT,
            locale ?: Locale.getDefault()
        ).format(date)

    /**
     * @return The moment formatted as an IntlString
     */
    private fun formatDate(fmt: Int): IntlString {
        val jyear = if (japaneseYears.containsKey(year)) {
            japaneseYears[year]
        } else {
            "${format("yyyy")}【年：ねん】"
        }

        val jmonth = japaneseMonths[month - 1]
        val jday = japaneseDays[dayOfMonth - 1]

        return IntlString().apply {
            de = DateFormat.getDateInstance(fmt, Locale.GERMANY).format(date)
            en = DateFormat.getDateInstance(fmt, Locale.UK).format(date)
            fr = DateFormat.getDateInstance(fmt, Locale.FRANCE).format(date)
            it = DateFormat.getDateInstance(fmt, Locale.ITALY).format(date)
            ja = arrayOf(jyear, " ", jmonth, " ", jday).joinToString("")
        }
    }

    /**
     * @return The moment formatted as an IntlString, e.g. 9. April 2021
     */
    fun formatDateLong() = formatDate(DateFormat.LONG)

    /**
     * @return The moment formatted as an IntlString, e.g. Freitag, 9. April 2021
     */
    fun formatDateLonger(allowMultiLine: Boolean = false): IntlString {
        val dow = formatDayOfWeek()
        val dtl = formatDateLong()
        return IntlString().apply {
            de = "${dow.de}, ${dtl.de}"
            en = "${dow.en}, ${dtl.en}"
            fr = "${dow.fr}, ${dtl.fr}"
            it = "${dow.it}, ${dtl.it}"

            ja = if (allowMultiLine) {
                "${dtl.ja}\n${dow.ja}"
            } else {
                "${dtl.ja}（${dow.ja}）"
            }
        }
    }

    /**
     * @return The day of the week formatted as an IntlString, e.g. Freitag
     */
    fun formatDayOfWeek(): IntlString {
        // We use our own array of IntlStrings so we can add furigana to Japanese
        return weekdays[dayOfWeek - 1]
    }

    /**
     * @return The day of the week formatted as an IntlString, e.g. Fr
     */
    fun formatDayOfWeekShort(): IntlString {
        return IntlString().apply {
            de = format("E", Locale.GERMANY)
            en = format("E", Locale.UK)
            fr = format("E", Locale.FRANCE)
            it = format("E", Locale.ITALY)
            ja = format("E", Locale.JAPANESE)
        }
    }

    /**
     * @return yyyy-MM-dd
     */
    fun formatRevDate(): String {
        return format("yyyy-MM-dd")
    }

    /**
     * Standard format in reverse order without timezone.
     * @return yyyy-MM-dd HH:mm:ss
     */
    fun formatRevDateTime(): String {
        return format("yyyy-MM-dd HH:mm:ss")
    }

    /**
     * Format in reverse order that may be used in filenames.
     * @return yyyyMMdd-HHmmss
     */
    fun formatRevDateTimeForFileName(): String {
        return format("yyyyMMdd-HHmmss")
    }

    /**
     * @Deprecated("Use formatRevDate instead", ReplaceWith("formatRevDateTime()"))
     * (The Deprecated annotation does not work on a function inherited from Any.)
     * @return yyyy-MM-dd HH:mm:ss
     */
    override fun toString(): String {
        return formatRevDateTime()
    }

    /**
     * @return A clone of this moment's date
     */
    fun toDate(): Date {
        return date.clone() as Date
    }

    /**
     * @return The moment as a Java timestamp
     */
    fun toLong(): Long {
        return date.time
    }

    /**
     * @return The moment as a UNIX timestamp
     */
    fun toUnix(): Long {
        return date.time / 1000L
    }

    companion object {
        @Suppress("unused")
        private const val TAG = "Moment"

        private val weekdays = arrayOf(
            IntlString().apply {
                en = "Sunday"
                de = "Sonntag"
                fr = "Dimanche"
                it = "Domenica"
                ja = "【日：にち】【曜：よう】【日：び】"
            },
            IntlString().apply {
                en = "Monday"
                de = "Montag"
                fr = "Lundi"
                it = "Lunedì"
                ja = "【月：げつ】【曜：よう】【日：び】"
            },
            IntlString().apply {
                en = "Tuesday"
                de = "Dienstag"
                fr = "Mardi"
                it = "Martedì"
                ja = "【火：か】【曜：よう】【日：び】"
            },
            IntlString().apply {
                en = "Wednesday"
                de = "Mittwoch"
                fr = "Mercredi"
                it = "Mercoledì"
                ja = "【水：すい】【曜：よう】【日：び】"
            },
            IntlString().apply {
                en = "Thursday"
                de = "Donnerstag"
                fr = "Jeudi"
                it = "Giovedì"
                ja = "【木：もく】【曜：よう】【日：び】"
            },
            IntlString().apply {
                en = "Friday"
                de = "Freitag"
                fr = "Vendredi"
                it = "Venerdì"
                ja = "【金：きん】【曜：よう】【日：び】"
            },
            IntlString().apply {
                en = "Saturday"
                de = "Samstag"
                fr = "Samedi"
                it = "Sabato"
                ja = "【土：ど】【曜：よう】【日：び】"
            }
        )

        val japaneseMonths = arrayOf(
            "【１：いち】【月：がつ】",
            "【２：に】【月：がつ】",
            "【３：さん】【月：がつ】",
            "【４：し】【月：がつ】",
            "【５：ご】【月：がつ】",
            "【６：ろく】【月：がつ】",
            "【７：しち】【月：がつ】",
            "【８：はち】【月：がつ】",
            "【９：く】【月：がつ】",
            "【10：じゅう】【月：がつ】",
            "【11：じゅういち】【月：がつ】",
            "【12：じゅうに】【月：がつ】"
        )

        val japaneseDays = arrayOf(
            "【１日：ついたち】", // tsuitachi
            "【２：ふつ】【日：か】",  // futsuka
            "【３：みっ】【日：か】", // mikka
            "【４：よっ】【日：か】", // yokka
            "【５：いつ】【日：か】", // itsuka
            "【６：むい】【日：か】",  // muika
            "【７：なの】【日：か】", // nanoka
            "【８：よう】【日：か】", // yōka
            "【９：ここの】【日：か】", // kokonoka
            "【10：とお】【日：か】", // tōka
            "【11：じゅういち】【日：にち】", // jūichi-nichi
            "【12：じゅうに】【日：にち】", // jūni-nichi
            "【13：じゅうさん】【日：にち】", // jūsan-nichi
            "【14：じゅうよっ】【日：か】", // jūyokka
            "【15：じゅうご】【日：にち】", // jūgo-nichi
            "【16：じゅうろく】【日：にち】", // jūroku-nichi
            "【17：じゅうしち】【日：にち】", // jūshichi-nichi
            "【18：じゅうはち】【日：にち】", // jūhachi-nichi
            "【19：じゅうく】【日：にち】", // jūku-nichi
            "【20：はつ】【日：か】", // hatsuka
            "【21：にじゅういち】【日：にち】", // nijūichi-nichi
            "【22：にじゅうに】【日：にち】", // nijūni-nichi
            "【23：にじゅうさん】【日：にち】", // nijūsan-nichi
            "【24：にじゅうよっ】【日：か】", // nijūyokka
            "【25：にじゅうご】【日：にち】", // nijūgo-nichi
            "【26：にじゅうろく】【日：にち】", // nijūroku-nichi
            "【27：にじゅうしち】【日：にち】", // nijūshichi-nichi
            "【28：にじゅうはち】【日：にち】", // nijūhachi-nichi
            "【29：にじゅうく】【日：にち】", // nijūku-nichi
            "【30：さんじゅう】【日：にち】", // sanjū-nichi
            "【31：さんじゅういち】【日：にち】" // sanjūichi-nichi
        )

        val japaneseYears = mapOf(
            2019 to "【2019：にせんじゅうきゅう】【年：ねん】", // ni-sen jū-kyū nen
            2020 to "【2020：にせんにじゅう】【年：ねん】", // ni-sen ni-jū nen
            2021 to "【2021：にせんにじゅういち】【年：ねん】", // ni-sen ni-jū ichi nen
            2022 to "【2022：にせんにじゅうに】【年：ねん】", // ni-sen ni-jū ni nen
            2023 to "【2023：にせんにじゅうさん】【年：ねん】", // ni-sen ni-jū san nen
            2024 to "【2024：にせんにじゅうよん】【年：ねん】", // ni-sen ni-jū yon nen
            2025 to "【2025：にせんにじゅうご】【年：ねん】", // ni-sen ni-jū go nen
            2026 to "【2026：にせんにじゅうろく】【年：ねん】", // ni-sen ni-jū roku nen
            2027 to "【2027：にせんにじゅうなな】【年：ねん】", // ni-sen ni-jū nana nen NEEDS VERIFICATION
            2028 to "【2028：にせんにじゅうはち】【年：ねん】", // ni-sen ni-jū hachi nen
            2029 to "【2029：にせんにじゅうきゅう】【年：ねん】", // ni-sen ni-jū kyū nen
            2030 to "【2030：にせんさんじゅう】【年：ねん】", // ni-sen san-jū nen
            2031 to "【2031：にせんさんじゅういち】【年：ねん】", // ni-sen san-jū ichi nen
            2032 to "【2032：にせんさんじゅうに】【年：ねん】", // ni-sen san-jū ni nen
        )

        val futureSentinel = fromString("3000-01-01 00:00:00")!!
        val pastSentinel = fromString("1900-01-01 00:00:00")!!

        /**
         * Parses the given string as yyyy-MM-DD HH:mm:ss and converts it into a Date using the
         * current locale.
         * @return an instance of Moment, or null if parsing failed
         */
        fun fromString(s: String): Moment? {
            @Suppress("SimpleDateFormat")
            val fmt = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")

            return try {
                val date = fmt.parse(s) ?: return null
                Moment(date)
            } catch (e: ParseException) {
                null
            }
        }

        /**
         * @return A new moment created from the given Java timestamp
         */
        fun fromLong(l: Long): Moment {
            return Moment(Date(l))
        }
    }
}
