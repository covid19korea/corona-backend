package com.corona.backend.service

import com.corona.backend.cacher.DataCacher
import com.corona.backend.domain.inoculation.res.InoculationRegionRes
import com.corona.backend.domain.inoculation.res.InoculationRes
import com.corona.backend.infra.publicdata.PublicDataClient
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

//    fun getInfection(): InfectionRes {
//        val today = DateUtil.getDate()
//        var infection = dataCacher.getInfection(today)
//        if (infection == null) {
//            val xml = publicDataClient.getData(infectionUrl, DateUtil.getQueryParam(2))
//            infection = InfectionRes.from(xmlParser.parse(xml, PublicInfection::class.java))
//            dataCacher.cache(today, infection)
//        }
//        return infection
//    }
//
//    fun getInfectionRegion(): InfectionRegionRes {
//        val today = DateUtil.getDate()
//        var infectionRegion = dataCacher.getInfectionRegion(today)
//        if (infectionRegion == null) {
//            val xml = publicDataClient.getData(infectionRegionUrl, DateUtil.getQueryParam(1))
//            infectionRegion = InfectionRegionRes.from(xmlParser.parse(xml, PublicInfectionRegion::class.java))
//            dataCacher.cache(today, infectionRegion)
//        }
//        return infectionRegion
//    }

    fun getInoculation(): InoculationRes {
        val today = DateUtil.getDate()
        var inoculation = dataCacher.getInoculation(today)
        if (inoculation == null) {
            val xml = publicDataClient.getData(inoculationUrl)
            inoculation = InoculationRes.from(xmlParser.parse(xml, Inoculation::class.java))
            dataCacher.cache(today, inoculation)
        }
        return inoculation
    }

    fun getInoculationRegion(): InoculationRegionRes {
        val today = DateUtil.getDate()
        var inoculationRegion = dataCacher.getInoculationRegion(today)
        if (inoculationRegion == null) {
            val xml = publicDataClient.getData(inoculationRegionUrl)
            inoculationRegion = InoculationRegionRes.from(xmlParser.parse(xml, InoculationRegion::class.java))
            dataCacher.cache(today, inoculationRegion)
        }
        return inoculationRegion
    }
}
