package com.corona.backend.util

import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

object DateUtil {

    private const val SEOUL_TIME_ZONE = "Asia/Seoul"

    fun getDate(): LocalDate {
        val nowSeoul = ZonedDateTime.now(ZoneId.of(SEOUL_TIME_ZONE))
        return if (isYesterday(nowSeoul)) {
            ZonedDateTime.now(ZoneId.of(SEOUL_TIME_ZONE)).minusDays(1).toLocalDate()
        } else ZonedDateTime.now(ZoneId.of(SEOUL_TIME_ZONE)).toLocalDate()
    }

    private fun isYesterday(time: ZonedDateTime): Boolean {
        return (time.hour < 10) || ((time.hour == 10) && (time.minute < 2))
    }

    fun convert2QueryParam(date: LocalDate) = date.toString().replace("-", "")
}
