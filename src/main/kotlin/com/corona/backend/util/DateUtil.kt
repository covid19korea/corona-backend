package com.corona.backend.util

import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

object DateUtil {

    fun getDate(): LocalDate {
        val nowSeoul = ZonedDateTime.now(ZoneId.of("Asia/Seoul"))
        return if (isYesterday(nowSeoul)) {
            ZonedDateTime.now(ZoneId.of("Asia/Seoul")).minusDays(1).toLocalDate()
        } else ZonedDateTime.now(ZoneId.of("Asia/Seoul")).toLocalDate()
    }

    private fun isYesterday(time: ZonedDateTime): Boolean {
        return (time.hour < 9) || (time.hour == 9 && time.minute < 30)
    }

    fun convert2QueryParam(date: LocalDate) = date.toString().replace("-", "")
}
