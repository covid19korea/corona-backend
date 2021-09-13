package com.corona.backend.scheduler

import com.corona.backend.service.DataCacher
import com.corona.backend.util.CheckingCounterUtil
import com.corona.backend.util.DateUtil
import com.corona.backend.util.String2IntegerConverter
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class PublicDataScheduler(
    private val dataCacher: DataCacher,
) {

    @Scheduled(cron = "0 59 1 * * *") // 한국시간기준 오전 9시 59분
    fun saveCheckingCount() {
        val infection = dataCacher.getInfection(DateUtil.getDate())
        CheckingCounterUtil.setCheckingCounter(String2IntegerConverter.convert(infection!!.checkingCounter))
    }
}
