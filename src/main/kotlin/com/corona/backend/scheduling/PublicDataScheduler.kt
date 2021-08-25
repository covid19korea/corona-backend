package com.corona.backend.scheduling

import org.springframework.cache.annotation.CacheEvict
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class PublicDataScheduler {

    @Scheduled(cron = "0 30 9 * * *")
    @CacheEvict(value = ["Infection", "InfectionRegion", "Inoculation", "InoculationRegion"], allEntries = true)
    fun emptyMorningCache() {
    }

    @Scheduled(cron = "0 0 16 * * *")
    @CacheEvict(value = ["Infection", "InfectionRegion", "Inoculation", "InoculationRegion"], allEntries = true)
    fun emptyAfternoonCache() {
    }
}
