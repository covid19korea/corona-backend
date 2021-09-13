package com.corona.backend.service

import com.corona.backend.infra.goodbye_corona.json.Infection
import com.corona.backend.infra.goodbye_corona.json.InfectionRegion
import com.corona.backend.infra.publicdata.xml.inoculation.Inoculation
import com.corona.backend.infra.publicdata.xml.inoculationRegion.InoculationRegion
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class DataCacher {

    val infection: MutableMap<LocalDate, Infection> = mutableMapOf()
    val infectionRegion: MutableMap<LocalDate, InfectionRegion> = mutableMapOf()
    val inoculation: MutableMap<LocalDate, Inoculation> = mutableMapOf()
    val inoculationRegion: MutableMap<LocalDate, InoculationRegion> = mutableMapOf()

    fun getInfection(date: LocalDate): Infection? {
        return infection[date]
    }

    fun cacheInfection(date: LocalDate, infectionRes: Infection) {
        infection[date] = infectionRes
    }

    fun getInfectionRegion(date: LocalDate): InfectionRegion? {
        return infectionRegion[date]
    }

    fun cacheInfectionRegion(date: LocalDate, infectionRegionRes: InfectionRegion) {
        infectionRegion[date] = infectionRegionRes
    }

    fun getInoculation(date: LocalDate): Inoculation? {
        return inoculation[date]
    }

    fun cacheInoculation(date: LocalDate, inoculationRes: Inoculation) {
        inoculation[date] = inoculationRes
    }

    fun getInoculationRegion(date: LocalDate): InoculationRegion? {
        return inoculationRegion[date]
    }

    fun cacheInoculationRegion(date: LocalDate, inoculationRegionRes: InoculationRegion) {
        inoculationRegion[date] = inoculationRegionRes
    }
}
