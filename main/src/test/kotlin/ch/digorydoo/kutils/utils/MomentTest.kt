package ch.digorydoo.kutils.utils

import java.util.Locale
import kotlin.test.Test
import kotlin.test.assertEquals
import kotlin.test.assertFalse
import kotlin.test.assertNotEquals
import kotlin.test.assertTrue
import kotlin.time.Duration.Companion.days
import kotlin.time.Duration.Companion.minutes

internal class MomentTest {
    @Test
    fun `should get the expected result from formatAsZoneAgnosticDate`() {
        val m = Moment(
            year = 1986,
            month = 5,
            day = 3,
            hour = 19,
            minute = 27,
            second = 42,
        )
        assertEquals("1986-05-03", m.formatAsZoneAgnosticDate())
    }

    @Test
    fun `should get the expected result from formatAsZoneAgnosticDateTime`() {
        val m = Moment(
            year = 1986,
            month = 5,
            day = 3,
            hour = 19,
            minute = 27,
            second = 42,
        )
        assertEquals("1986-05-03 19:27:42", m.formatAsZoneAgnosticDateTime())
    }

    @Test
    fun `should get the expected result from formatAsZoneAgnosticDateTimeCompact`() {
        val m = Moment(
            year = 1986,
            month = 5,
            day = 3,
            hour = 19,
            minute = 27,
            second = 42,
        )
        assertEquals("19860503-192742", m.formatAsZoneAgnosticDateTimeCompact())
    }

    @Test
    fun `should get the expected result from formatLocalized`() {
        val m = Moment(
            year = 1986,
            month = 5,
            day = 3,
            hour = 19,
            minute = 27,
            second = 42,
        )
        assertEquals("3 May 1986, 19:27", m.formatLocalized())
        assertEquals("3. Mai 1986", m.formatLocalized(LocalizedFormatStyle.DATE, Locale.GERMAN))
        assertEquals("3 mai 1986 19:27", m.formatLocalized(LocalizedFormatStyle.DATE_TIME, Locale.FRENCH))
        assertEquals("3 May 1986, 19:27:42", m.formatLocalized(LocalizedFormatStyle.DATE_TIME_WITH_SECONDS, Locale.UK))
        assertEquals("19:27", m.formatLocalized(LocalizedFormatStyle.TIME, Locale.GERMAN))
        assertEquals("19:27:42", m.formatLocalized(LocalizedFormatStyle.TIME_WITH_SECONDS, Locale.GERMAN))
        assertEquals("Samstag, 3. Mai 1986", m.formatLocalized(LocalizedFormatStyle.WEEKDAY_DATE, Locale.GERMAN))
        assertEquals(
            "Saturday, 3 May 1986, 19:27",
            m.formatLocalized(LocalizedFormatStyle.WEEKDAY_DATE_TIME, Locale.UK)
        )
        assertEquals(
            "Saturday, 3 May 1986, 19:27:42",
            m.formatLocalized(LocalizedFormatStyle.WEEKDAY_DATE_TIME_WITH_SECONDS, Locale.UK)
        )
        assertEquals(
            "1986年5月3日 19:27:42（土曜日）",
            m.formatLocalized(LocalizedFormatStyle.WEEKDAY_DATE_TIME_WITH_SECONDS, Locale.JAPAN)
        )
    }

    @Test
    fun `should get the expected result from toString`() {
        val m = Moment(
            year = 1986,
            month = 5,
            day = 3,
            hour = 19,
            minute = 27,
            second = 42,
        )
        assertEquals("1986-05-03 19:27:42", m.toString())
    }

    @Test
    fun `should have a now() value between DISTANT_PAST and DISTANT_FUTURE`() {
        assertTrue(Moment.DISTANT_PAST < Moment.DISTANT_FUTURE, "DISTANT_PAST < DISTANT_FUTURE")
        assertTrue(Moment.DISTANT_FUTURE > Moment.DISTANT_PAST, "DISTANT_FUTURE > DISTANT_PAST")

        val now = Moment.now()
        assertTrue(Moment.DISTANT_PAST < now, "DISTANT_PAST < now")
        assertTrue(Moment.DISTANT_FUTURE > now, "DISTANT_FUTURE > now")
    }

    @Test
    fun `should correctly add n days`() {
        val a = Moment(1986, 2, 27, 19, 27, 42) // 1986-02-27 19:27:42
        val b = a + 5.days
        assertEquals("1986-03-04 19:27:42", b.toString())
        assertFalse(1986.isLeapYear())

        val c = Moment(1984, 2, 27, 19, 27, 42) // 1984-02-27 19:27:42
        val d = c + 5.days
        assertEquals("1984-03-03 19:27:42", d.toString())
        assertTrue(1984.isLeapYear())
    }

    @Test
    fun `should correctly subtract n minutes`() {
        val a = Moment(1986, 2, 27, 19, 27, 42) // 1986-02-27 19:27:42
        val b = a - 29.minutes
        assertEquals("1986-02-27 18:58:42", b.toString())
    }

    @Test
    fun `should get the same moment when formatting with formatAsZoneAgnosticDateTime and parsing back`() {
        val ma = Moment(1986, 2, 27, 19, 27, 42) // 1986-02-27 19:27:42
        val fa = ma.formatAsZoneAgnosticDateTime()
        val mb = Moment.parseZoneAgnosticOrNull(fa)
        assertEquals("1986-02-27 19:27:42", mb.toString())
        assertEquals(ma, mb)
    }

    @Test
    fun `should get the same moment when formatting with formatAsZoneAgnosticDateTimeCompact and parsing back`() {
        val ma = Moment(1986, 2, 27, 19, 27, 42) // 1986-02-27 19:27:42
        val fa = ma.formatAsZoneAgnosticDateTimeCompact()
        val mb = Moment.parseZoneAgnosticOrNull(fa)
        assertEquals("1986-02-27 19:27:42", mb.toString())
        assertEquals(ma, mb)
    }

    @Test
    fun `should get the same date at midnight when formatting with formatAsZoneAgnosticDate and parsing back`() {
        val ma = Moment(1986, 2, 27, 19, 27, 42) // 1986-02-27 19:27:42
        val fa = ma.formatAsZoneAgnosticDate()
        val mb = Moment.parseZoneAgnosticOrNull(fa)
        assertEquals("1986-02-27 00:00:00", mb.toString())
        assertNotEquals(ma, mb)
    }
}
