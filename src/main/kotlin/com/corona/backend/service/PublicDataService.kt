package com.corona.backend.service

import com.corona.backend.domain.redis.InfectionRedisRepository
import com.corona.backend.domain.redis.InfectionRegionRedisRepository
import com.corona.backend.infra.publicdata.PublicDataClient
import com.corona.backend.infra.publicdata.xml.infection.Infection
import com.corona.backend.infra.publicdata.xml.infectionRegion.InfectionRegion
import com.corona.backend.infra.publicdata.xml.inoculation.Inoculation
import com.corona.backend.infra.publicdata.xml.inoculationRegion.InoculationRegion
import com.corona.backend.util.DateUtil
import com.corona.backend.util.XmlParser
import org.springframework.beans.factory.annotation.Value
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
    private val infectionRedisRepository: InfectionRedisRepository,
    private val infectionRegionRedisRepository: InfectionRegionRedisRepository,
    private val xmlParser: XmlParser,
) {

    fun getInfection(date: LocalDate): Infection {
        //
        // val today = infectionRedisRepository.findByIdOrNull(DateUtil.convertToRedisKey(date))
        // if (today != null) {
        //     return today.data
        // }
        //
        // val yesterday = infectionRedisRepository.findByIdOrNull(DateUtil.convertToRedisKey(date.minusDays(1)))
        //
        // val xml = publicDataClient.getData(
        //     url = infectionUrl,
        //     queryParam = makeDateQueryParams(
        //         startDate = date.minusDays(1),
        //         endDate = date,
        //     )
        // )
        // val infection = xmlParser.parse(xml, Infection::class.java)
        // if (infection.body.items.size < 2) {
        //     return yesterday!!.data
        // }
        //
        // return infection

        val xml = publicDataClient.getData(
            url = infectionUrl,
            queryParam = makeDateQueryParams(
                startDate = date.minusDays(1),
                endDate = date,
            )
        )
        return xmlParser.parse(xml, Infection::class.java)
    }

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

    fun getInoculation(): Inoculation {
        val xml = publicDataClient.getData(inoculationUrl)
        return xmlParser.parse(xml, Inoculation::class.java)
    }

    fun getInoculationRegion(): InoculationRegion {
        val xml = publicDataClient.getData(inoculationRegionUrl)
        return xmlParser.parse(xml, InoculationRegion::class.java)
    }
}
