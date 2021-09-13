package com.corona.backend.service

import com.corona.backend.infra.goodbye_corona.GoodbyeCoronaClient
import com.corona.backend.infra.goodbye_corona.json.Infection
import com.corona.backend.infra.goodbye_corona.json.InfectionRegion
import com.corona.backend.util.InfectionUtil
import com.corona.backend.util.String2IntegerConverter
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class GoodbyeCoronaService(
    @Value("\${goodbye-api.url.infection}") private val infectionUrl: String,
    @Value("\${goodbye-api.url.infection-region}") private val infectionRegionUrl: String,

    private val dataCacher: DataCacher,

    private val goodbyeCoronaClient: GoodbyeCoronaClient,
    private val om: ObjectMapper,
) {
    fun getInfection(date: LocalDate): Infection {
        var res = dataCacher.getInfection(date)
        if (res == null) {
            res = om.readValue<Infection>(goodbyeCoronaClient.getData(infectionUrl))
            dataCacher.cacheInfection(date, res)
        }
        return res
    }

    fun getInfectionRegion(date: LocalDate): InfectionRegion {
        var res = dataCacher.getInfectionRegion(date)
        if (res == null) {
            res = om.readValue<InfectionRegion>(goodbyeCoronaClient.getData(infectionRegionUrl))
            dataCacher.cacheInfectionRegion(date, res)
            InfectionUtil.cacheInfectionCount(String2IntegerConverter.convert(res.korea.newCase))
        }
        return res
    }
}
