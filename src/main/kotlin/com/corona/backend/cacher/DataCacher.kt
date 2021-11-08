package com.corona.backend.cacher

import com.corona.backend.infra.publicdata.xml.infection.PublicInfection
import com.corona.backend.infra.publicdata.xml.infectionRegion.PublicInfectionRegion
import com.corona.backend.infra.publicdata.xml.inoculation.Inoculation
import com.corona.backend.infra.publicdata.xml.inoculationRegion.InoculationRegion
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class DataCacher(
    private val infectionMap: MutableMap<LocalDate, PublicInfection> = mutableMapOf(),
    private val infectionRegionMap: MutableMap<LocalDate, PublicInfectionRegion> = mutableMapOf(),
    private val inoculationMap: MutableMap<LocalDate, Inoculation> = mutableMapOf(),
    private val inoculationRegionMap: MutableMap<LocalDate, InoculationRegion> = mutableMapOf(),
) {

    // 캐싱 로직
    fun cache(date: LocalDate, publicInfection: PublicInfection) {
        infectionMap[date] = publicInfection
    }

    fun cache(date: LocalDate, publicInfectionRegion: PublicInfectionRegion) {
        infectionRegionMap[date] = publicInfectionRegion
    }

    fun cache(date: LocalDate, inoculation: Inoculation) {
        inoculationMap[date] = inoculation
    }

    fun cache(date: LocalDate, inoculationRegion: InoculationRegion) {
        inoculationRegionMap[date] = inoculationRegion
    }

    // 반환 로직
    fun getInfection(date: LocalDate): PublicInfection? {
        return infectionMap[date]
    }

    fun getInfectionRegion(date: LocalDate): PublicInfectionRegion? {
        return infectionRegionMap[date]
    }

    fun getInoculation(date: LocalDate): Inoculation? {
        return inoculationMap[date]
    }

    fun getInoculationRegion(date: LocalDate): InoculationRegion? {
        return inoculationRegionMap[date]
    }
}
