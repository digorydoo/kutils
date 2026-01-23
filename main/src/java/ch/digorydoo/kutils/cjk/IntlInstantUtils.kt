package ch.digorydoo.kutils.cjk

import ch.digorydoo.kutils.utils.LocalizedFormatStyle
import ch.digorydoo.kutils.utils.Moment
import ch.digorydoo.kutils.utils.formatLocalized
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.isoDayNumber
import kotlinx.datetime.number
import java.util.*

private val japaneseDaysOfWeek = arrayOf(
    "【月：げつ】【曜：よう】【日：び】",
    "【火：か】【曜：よう】【日：び】",
    "【水：すい】【曜：よう】【日：び】",
    "【木：もく】【曜：よう】【日：び】",
    "【金：きん】【曜：よう】【日：び】",
    "【土：ど】【曜：よう】【日：び】",
    "【日：にち】【曜：よう】【日：び】",
)

private val japaneseMonths = arrayOf(
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

private val japaneseDays = arrayOf(
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

private val japaneseYears = mapOf(
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

/**
 * @return The moment formatted as an IntlString, e.g. 9. April 2021
 */
fun LocalDateTime.dateToIntlString(): IntlString {
    return IntlString().apply {
        de = formatLocalized(LocalizedFormatStyle.DATE, Locale.GERMANY)
        en = formatLocalized(LocalizedFormatStyle.DATE, Locale.UK)
        fr = formatLocalized(LocalizedFormatStyle.DATE, Locale.FRANCE)
        it = formatLocalized(LocalizedFormatStyle.DATE, Locale.ITALY)
        ja = japaneseDate
    }
}

/**
 * @return The moment formatted as an IntlString, e.g. Freitag, 9. April 2021
 */
fun LocalDateTime.dateToIntlStringLong(multiLine: Boolean = false): IntlString {
    return IntlString().apply {
        de = formatLocalized(LocalizedFormatStyle.WEEKDAY_DATE, Locale.GERMANY)
        en = formatLocalized(LocalizedFormatStyle.WEEKDAY_DATE, Locale.UK)
        fr = formatLocalized(LocalizedFormatStyle.WEEKDAY_DATE, Locale.FRANCE)
        it = formatLocalized(LocalizedFormatStyle.WEEKDAY_DATE, Locale.ITALY)
        ja = if (multiLine) japaneseDateLong else japaneseDateMultiLine
    }
}

/**
 * @return The date in Japanese with furigana
 */
val LocalDateTime.japaneseDate: String
    get() = arrayOf(japaneseYear, " ", japaneseMonth, " ", japaneseDayOfMonth).joinToString("")

/**
 * @return The date in Japanese including day of week, with furigana
 */
val LocalDateTime.japaneseDateLong: String
    get() {
        val ymd = arrayOf(japaneseYear, " ", japaneseMonth, " ", japaneseDayOfMonth).joinToString("")
        val dow = japaneseDayOfWeek
        return "$ymd（$dow）"
    }

/**
 * @return The date in Japanese including day of week on a new line, with furigana
 */
val LocalDateTime.japaneseDateMultiLine: String
    get() {
        val ymd = arrayOf(japaneseYear, " ", japaneseMonth, " ", japaneseDayOfMonth).joinToString("")
        val dow = japaneseDayOfWeek
        return "$ymd\n$dow"
    }

/**
 * @return The day of the week in Japanese including furigana
 */
val LocalDateTime.japaneseDayOfWeek: String
    get() = japaneseDaysOfWeek[dayOfWeek.isoDayNumber - 1]

/**
 * @return The day of the week in Japanese, abbreviated by a single Char
 */
val LocalDateTime.japaneseDayOfWeekAbbrev: Char
    get() = japaneseDaysOfWeek[dayOfWeek.isoDayNumber - 1][1]

/**
 * @return The day of month in Japanese including furigana
 */
val LocalDateTime.japaneseDayOfMonth: String
    get() = japaneseDays[day - 1]

/**
 * @return The month in Japanese including furigana
 */
val LocalDateTime.japaneseMonth: String
    get() = japaneseMonths[month.number - 1]

/**
 * @return The year in Japanese including furigana
 */
val LocalDateTime.japaneseYear: String
    get() = japaneseYears[year] ?: "$year【年：ねん】"

fun Moment.dateToIntlString() = toLocalDateTime().dateToIntlString()
fun Moment.dateToIntlStringLong(multiLine: Boolean = false) = toLocalDateTime().dateToIntlStringLong(multiLine)
val Moment.japaneseDate: String get() = toLocalDateTime().japaneseDate
val Moment.japaneseDateLong: String get() = toLocalDateTime().japaneseDateLong
val Moment.japaneseDateMultiLine: String get() = toLocalDateTime().japaneseDateMultiLine
val Moment.japaneseDayOfWeek: String get() = toLocalDateTime().japaneseDayOfWeek
val Moment.japaneseDayOfWeekAbbrev: Char get() = toLocalDateTime().japaneseDayOfWeekAbbrev
val Moment.japaneseDayOfMonth: String get() = toLocalDateTime().japaneseDayOfMonth
val Moment.japaneseMonth: String get() = toLocalDateTime().japaneseMonth
val Moment.japaneseYear: String get() = toLocalDateTime().japaneseYear
