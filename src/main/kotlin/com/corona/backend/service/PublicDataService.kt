package com.corona.backend.service

import com.corona.backend.infra.publicdata.PublicDataClient
import com.corona.backend.infra.publicdata.xml.infection.Infection
import com.corona.backend.infra.publicdata.xml.infectionRegion.InfectionRegion
import com.corona.backend.infra.publicdata.xml.inoculation.Inoculation
import com.corona.backend.infra.publicdata.xml.inoculationRegion.InoculationRegion
import com.corona.backend.util.DateUtil
import com.corona.backend.util.XmlParser
import org.springframework.beans.factory.annotation.Value
import org.springframework.cache.annotation.Cacheable
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import java.time.LocalDate

@Service
class PublicDataService(
    @Value("\${open-api.url.infection}") private val infectionUrl: String,
    @Value("\${open-api.url.infection-region}") private val infectionRegionUrl: String,
    @Value("\${open-api.url.inoculation}") private val inoculationUrl: String,
    @Value("\${open-api.url.inoculation-region}") private val inoculationRegionUrl: String,

    private val publicDataClient: PublicDataClient,
    private val xmlParser: XmlParser,
) {

    @Cacheable(value = ["Infection"], key = "#date")
    fun getInfection(date: LocalDate): Infection {
        date.minusDays(1)
        date

        val xml = publicDataClient.getData(
            url = infectionUrl,
            queryParam = makeDateQueryParams(
                startDate = date.minusDays(1),
                endDate = date,
            )
        )
        return xmlParser.parse(xml, Infection::class.java)
    }

    @Cacheable(value = ["InfectionRegion"], key = "#date")
    fun getInfectionRegion(date: LocalDate): InfectionRegion {
        val xml = publicDataClient.getData(
            url = infectionRegionUrl,
            queryParam = makeDateQueryParams(
                startDate = date,
                endDate = date,
            )
        )
        return xmlParser.parse(xml, InfectionRegion::class.java)
    }

    private fun makeDateQueryParams(startDate: LocalDate, endDate: LocalDate) = LinkedMultiValueMap<String, String>()
        .apply {
            add("startCreateDt", DateUtil.convert2QueryParam(startDate))
            add("endCreateDt", DateUtil.convert2QueryParam(endDate))
        }

    @Cacheable(value = ["Inoculation"])
    fun getInoculation(): Inoculation {
        val xml = publicDataClient.getData(inoculationUrl)
        return xmlParser.parse(xml, Inoculation::class.java)
    }

    @Cacheable(value = ["InoculationRegion"])
    fun getInoculationRegion(): InoculationRegion {
        val xml = publicDataClient.getData(inoculationRegionUrl)
        return xmlParser.parse(xml, InoculationRegion::class.java)
    }
}
