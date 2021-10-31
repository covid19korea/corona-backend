package com.corona.backend.service

import com.corona.backend.infra.publicdata.PublicDataClient
import com.corona.backend.infra.publicdata.xml.inoculation.Inoculation
import com.corona.backend.infra.publicdata.xml.inoculationRegion.InoculationRegion
import com.corona.backend.util.XmlParser
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Service
import java.time.LocalDate

@Service
class PublicDataService(
    @Value("\${open-api.url.inoculation}") private val inoculationUrl: String,
    @Value("\${open-api.url.inoculation-region}") private val inoculationRegionUrl: String,

    private val dataCacher: DataCacher,

    private val publicDataClient: PublicDataClient,
    private val xmlParser: XmlParser,
) {

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
