package com.corona.backend.util

import org.springframework.util.LinkedMultiValueMap
import java.time.LocalDate
import java.time.ZoneId
import java.time.ZonedDateTime

object DateUtil {

    private const val SEOUL_TIME_ZONE = "Asia/Seoul"

    // 비즈니스 로직
    fun getDate(): LocalDate {
        val nowSeoul = ZonedDateTime.now(ZoneId.of(SEOUL_TIME_ZONE))
        if (isYesterday(nowSeoul)) {
            return nowSeoul.minusDays(1).toLocalDate()
        }
        return nowSeoul.toLocalDate()
    }

    fun getDate(offset: Long): LocalDate {
        return getDate().minusDays(offset)
    }

    private fun isYesterday(time: ZonedDateTime): Boolean {
        return (time.hour < 10) || ((time.hour == 10) && (time.minute < 15))
    }

    fun getQueryParam(offset: Long): LinkedMultiValueMap<String, String> {
        return LinkedMultiValueMap<String, String>().apply {
            add("endCreateDt", date2String(getDate()))
            add("startCreateDt", date2String(getDate(offset)))
        }
    }

    private fun date2String(date: LocalDate) = date.toString().replace("-", "")
}
