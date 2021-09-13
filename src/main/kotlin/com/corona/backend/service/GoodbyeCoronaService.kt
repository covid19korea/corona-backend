package com.corona.backend.service

import com.corona.backend.infra.goodbye_corona.GoodbyeCoronaClient
import com.corona.backend.infra.goodbye_corona.json.Infection
import com.corona.backend.infra.goodbye_corona.json.InfectionRegion
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.readValue
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class GoodbyeCoronaService(
    @Value("\${goodbye-api.url.infection}") private val infectionUrl: String,
    @Value("\${goodbye-api.url.infection-region}") private val infectionRegionUrl: String,

    private val goodbyeCoronaClient: GoodbyeCoronaClient,
    private val om: ObjectMapper,
) {
    fun getInfection(localDate: LocalDate): Infection {
        val json = goodbyeCoronaClient.getData(infectionUrl)
        return om.readValue(json)
    }

    fun getInfectionRegion(localDate: LocalDate): InfectionRegion {
        val json = goodbyeCoronaClient.getData(infectionRegionUrl)
        return om.readValue(json)
    }
}
