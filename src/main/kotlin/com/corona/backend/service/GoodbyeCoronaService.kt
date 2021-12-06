package com.corona.backend.service

import com.corona.backend.cacher.DataCacher
import com.corona.backend.domain.infection.res.InfectionRegionRes
import com.corona.backend.domain.infection.res.InfectionRes
import com.corona.backend.infra.goodbye_corona.GoodbyeCoronaClient
import com.corona.backend.infra.goodbye_corona.json.InfectionRegion
import com.corona.backend.util.DateUtil
import com.corona.backend.util.InfectionUtil
import com.corona.backend.util.String2IntegerConverter
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

@Service
class GoodbyeCoronaService(
    @Value("\${goodbye-api.url.infection}") private val infectionUrl: String,
    @Value("\${goodbye-api.url.infection-region}") private val infectionRegionUrl: String,

    private val goodbyeCoronaClient: GoodbyeCoronaClient,
    private val om: ObjectMapper,
    private val dataCacher: DataCacher,
) {
    fun getInfection(): InfectionRes {
        val today = DateUtil.getDate()
        var infection = dataCacher.getInfection(today)
        if (infection == null) {
            val json = goodbyeCoronaClient.getData(infectionUrl)
            infection = om.readValue(json)
            dataCacher.cache(today, infection)
        }
        return InfectionRes.from(infection)
    }

    fun getInfectionRegion(): InfectionRegionRes {
        val today = DateUtil.getDate()
        var infectionRegionRes = dataCacher.getInfectionRegion(today)
        if (infectionRegionRes == null) {
            val json = goodbyeCoronaClient.getData(infectionRegionUrl)
            val infectionRegion: InfectionRegion = om.readValue(json)
            infectionRegionRes = InfectionRegionRes.from(infectionRegion)
            dataCacher.cache(today, infectionRegionRes)
            InfectionUtil.cacheInfectionCount(String2IntegerConverter.convert(infectionRegion.korea.newCase))
        }
        return infectionRegionRes
    }
}
