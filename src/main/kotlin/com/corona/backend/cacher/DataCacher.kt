package com.corona.backend.cacher

import com.corona.backend.domain.infection.res.InfectionRegionRes
import com.corona.backend.domain.infection.res.InfectionRes
import com.corona.backend.domain.inoculation.res.InoculationRegionRes
import com.corona.backend.domain.inoculation.res.InoculationRes
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class DataCacher(
    private val infectionMap: MutableMap<LocalDate, InfectionRes> = mutableMapOf(),
    private val infectionRegionMap: MutableMap<LocalDate, InfectionRegionRes> = mutableMapOf(),
    private val inoculationMap: MutableMap<LocalDate, InoculationRes> = mutableMapOf(),
    private val inoculationRegionMap: MutableMap<LocalDate, InoculationRegionRes> = mutableMapOf(),
) {

    // 캐싱 로직
    fun cache(date: LocalDate, infectionRes: InfectionRes) {
        infectionMap[date] = infectionRes
    }

    fun cache(date: LocalDate, infectionRegionRes: InfectionRegionRes) {
        infectionRegionMap[date] = infectionRegionRes
    }

    fun cache(date: LocalDate, inoculationRes: InoculationRes) {
        inoculationMap[date] = inoculationRes
    }

    fun cache(date: LocalDate, inoculationRegionRes: InoculationRegionRes) {
        inoculationRegionMap[date] = inoculationRegionRes
    }

    // 반환 로직
    fun getInfection(date: LocalDate): InfectionRes? {
        return infectionMap[date]
    }

    fun getInfectionRegion(date: LocalDate): InfectionRegionRes? {
        return infectionRegionMap[date]
    }

    fun getInoculation(date: LocalDate): InoculationRes? {
        return inoculationMap[date]
    }

    fun getInoculationRegion(date: LocalDate): InoculationRegionRes? {
        return inoculationRegionMap[date]
    }
}
