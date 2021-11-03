package com.corona.backend.service

import com.corona.backend.infra.publicdata.PublicDataClient
import com.corona.backend.infra.publicdata.xml.infection.PublicInfection
import com.corona.backend.infra.publicdata.xml.infectionRegion.PublicInfectionRegion
import com.corona.backend.infra.publicdata.xml.inoculation.Inoculation
import com.corona.backend.infra.publicdata.xml.inoculationRegion.InoculationRegion
import com.corona.backend.util.DateUtil
import com.corona.backend.util.XmlParser
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import java.time.LocalDate

@Service
class PublicDataService(
    @Value("\${open-api.url.infection}") private val infectionUrl: String,
    @Value("\${open-api.url.infection-region}") private val infectionRegionUrl: String,
    @Value("\${open-api.url.inoculation}") private val inoculationUrl: String,
    @Value("\${open-api.url.inoculation-region}") private val inoculationRegionUrl: String,

    private val dataCacher: DataCacher,

    private val publicDataClient: PublicDataClient,
    private val xmlParser: XmlParser,
) {

    fun getInfection(date: LocalDate): PublicInfection {
        var res = dataCacher.getPublicInfection(date)
        if (res == null) {
            res = xmlParser.parse(publicDataClient.getData(infectionUrl, getInfectionQueryParam()), PublicInfection::class.java)
            dataCacher.cachePublicInfection(date, res)
        }
        return res
    }

    private fun getInfectionQueryParam(): MultiValueMap<String, String> {
        return LinkedMultiValueMap<String, String>().apply {
            add("endCreateDt", DateUtil.convert2QueryParam(DateUtil.getDate()))
            add("startCreateDt", DateUtil.convert2QueryParam(DateUtil.getDate().minusDays(1)))
        }
    }

    fun getInfectionRegion(date: LocalDate): PublicInfectionRegion {
        var res = dataCacher.getPublicInfectionRegion(date)
        if (res == null) {
            res = xmlParser.parse(publicDataClient.getData(infectionRegionUrl, getInfectionRegionQueryParam()), PublicInfectionRegion::class.java)
            dataCacher.cachePublicInfectionRegion(date, res)
        }
        return res
    }

    private fun getInfectionRegionQueryParam(): MultiValueMap<String, String> {
        return LinkedMultiValueMap<String, String>().apply {
            add("endCreateDt", DateUtil.convert2QueryParam(DateUtil.getDate()))
            add("startCreateDt", DateUtil.convert2QueryParam(DateUtil.getDate()))
        }
    }

    fun getInoculation(date: LocalDate): Inoculation {
        var res = dataCacher.getInoculation(date)
        if (res == null) {
            res = xmlParser.parse(publicDataClient.getData(inoculationUrl), Inoculation::class.java)
            dataCacher.cacheInoculation(date, res)
        }
        return res
    }

    fun getInoculationRegion(date: LocalDate): InoculationRegion {
        var res = dataCacher.getInoculationRegion(date)
        if (res == null) {
            res = xmlParser.parse(publicDataClient.getData(inoculationRegionUrl), InoculationRegion::class.java)
            dataCacher.cacheInoculationRegion(date, res)
        }
        return res
    }
}
