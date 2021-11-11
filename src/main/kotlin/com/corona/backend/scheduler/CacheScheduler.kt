package com.corona.backend.scheduler

import com.corona.backend.cacher.DataCacher
import com.corona.backend.service.PublicDataService
import com.corona.backend.util.DateUtil
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class CacheScheduler(
    private val publicDataService: PublicDataService,
    private val dataCacher: DataCacher,
) {
    @Scheduled(cron = "1 15 1 * * *") // 한국시간기준 오전 10시 15분
    fun cache() {
        cacheTodayData()
    }

    private fun cacheTodayData() {
        try {
            cacheAll()
        } catch (e: Exception) {
            handleCacheError()
            throw e
        }
    }

    private fun handleCacheError() {
        dataCacher.cache(DateUtil.getDate(), dataCacher.getInfection(DateUtil.getDate(1))!!)
        dataCacher.cache(DateUtil.getDate(), dataCacher.getInfectionRegion(DateUtil.getDate(1))!!)
        dataCacher.cache(DateUtil.getDate(), dataCacher.getInoculation(DateUtil.getDate(1))!!)
        dataCacher.cache(DateUtil.getDate(), dataCacher.getInoculationRegion(DateUtil.getDate(1))!!)
    }

    private fun cacheAll() {
        publicDataService.getInfection()
        publicDataService.getInfectionRegion()
        publicDataService.getInoculation()
        publicDataService.getInoculationRegion()
    }
}
