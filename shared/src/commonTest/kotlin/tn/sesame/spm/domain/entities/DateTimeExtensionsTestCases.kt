package tn.sesame.spm.domain.entities

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalTime
import kotlinx.datetime.Month
import tn.sesame.spm.domain.formatDMY
import tn.sesame.spm.domain.formatHHMM
import kotlin.test.Test
import kotlin.test.asserter


class DateTimeExtensionsTestCases {

    private val dummyDateObject1: LocalDate = LocalDate(
        dayOfMonth = 2,
        month = Month.APRIL,
        year = 1998
    )

    private val dummyDateObject2: LocalDate = LocalDate(
        dayOfMonth = 11,
        month = Month.DECEMBER,
        year = 1998
    )

    private val dummyTimeObject1: LocalTime = LocalTime(
        hour = 2,
        minute = 2,
        second = 0
    )

    private val dummyTimeObject2: LocalTime = LocalTime(
        hour = 2,
        minute = 45,
        second = 1
    )

    private val dummyTimeObject3: LocalTime = LocalTime(
        hour = 0,
        minute = 20,
        second = 1
    )

    @Test
    fun `GIVEN a LocalDate object WHEN formatted to DDMMYYYY format THEN the test succeeds`() {
        asserter.assertEquals(
            actual = dummyDateObject1.formatDMY(),
            expected = "02/04/1998",
            message = "Date format does not follow the domain specifications"
        )
        asserter.assertEquals(
            actual = dummyDateObject2.formatDMY(),
            expected = "11/12/1998",
            message = "Date format does not follow the domain specifications"
        )
    }

    @Test
    fun `GIVEN a LocalTime object WHEN formatted to hhmmss format THEN the test succeeds`() {
        asserter.assertEquals(
            actual = dummyTimeObject1.formatHHMM(),
            expected = "2h02m",
            message = "Time format does not follow the domain specifications"
        )
        asserter.assertEquals(
            actual = dummyTimeObject2.formatHHMM(),
            expected = "2h45m",
            message = "Time format does not follow the domain specifications"
        )
        asserter.assertEquals(
            actual = dummyTimeObject3.formatHHMM(),
            expected = "0h20m",
            message = "Time format does not follow the domain specifications"
        )
    }

}