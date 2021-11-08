package com.corona.backend.service

import com.corona.backend.cacher.DataCacher
import com.corona.backend.infra.publicdata.PublicDataClient
import com.corona.backend.infra.publicdata.xml.infection.PublicInfection
import com.corona.backend.infra.publicdata.xml.infectionRegion.PublicInfectionRegion
import com.corona.backend.infra.publicdata.xml.inoculation.Inoculation
import com.corona.backend.infra.publicdata.xml.inoculationRegion.InoculationRegion
import com.corona.backend.util.DateUtil
import com.corona.backend.util.XmlParser
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service

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

    fun getInfection(): PublicInfection {
        val today = DateUtil.getDate()
        var infection = dataCacher.getInfection(today)
        if (infection == null) {
            val xml = publicDataClient.getData(infectionUrl, DateUtil.getQueryParam(1))
            infection = xmlParser.parse(xml, PublicInfection::class.java)
            dataCacher.cache(today, infection)
        }
        return infection
    }

    fun getInfectionRegion(): PublicInfectionRegion {
        val today = DateUtil.getDate()
        var infectionRegion = dataCacher.getInfectionRegion(today)
        if (infectionRegion == null) {
            val xml = publicDataClient.getData(infectionRegionUrl, DateUtil.getQueryParam(0))
            infectionRegion = xmlParser.parse(xml, PublicInfectionRegion::class.java)
            dataCacher.cache(today, infectionRegion)
        }
        return infectionRegion
    }

    fun getInoculation(): Inoculation {
        val today = DateUtil.getDate()
        var inoculation = dataCacher.getInoculation(today)
        if (inoculation == null) {
            val xml = publicDataClient.getData(inoculationUrl)
            inoculation = xmlParser.parse(xml, Inoculation::class.java)
            dataCacher.cache(today, inoculation)
        }
        return inoculation
    }

    fun getInoculationRegion(): InoculationRegion {
        val today = DateUtil.getDate()
        var inoculationRegion = dataCacher.getInoculationRegion(today)
        if (inoculationRegion == null) {
            val xml = publicDataClient.getData(inoculationRegionUrl)
            inoculationRegion = xmlParser.parse(xml, InoculationRegion::class.java)
            dataCacher.cache(today, inoculationRegion)
        }
        return inoculationRegion
    }
}
