package com.corona.backend.util

import java.time.LocalDate
import java.time.LocalDateTime

object DateUtil {

    fun getDate(): LocalDate {
        return if (isYesterday(LocalDateTime.now())) {
            LocalDate.now().minusDays(1)
        } else LocalDate.now()
    }

    private fun isYesterday(time: LocalDateTime): Boolean {
        return (time.hour < 9) || (time.hour == 9 && time.minute < 30)
    }

    fun convert2QueryParam(date: LocalDate) = date.toString().replace("-", "")
}
