package com.corona.backend.controller

import com.corona.backend.domain.infection.res.InfectionRegionRes
import com.corona.backend.domain.infection.res.InfectionRes
import com.corona.backend.domain.inoculation.res.InoculationRegionRes
import com.corona.backend.domain.inoculation.res.InoculationRes
import com.corona.backend.service.PublicDataService
import com.corona.backend.util.DateUtil
import io.swagger.annotations.ApiOperation
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RestController
@RequestMapping("/v1/data")
class PublicDataController(
    private val publicDataService: PublicDataService,
) {

    @ApiOperation("감염(전국)")
    @GetMapping("/infection")
    fun getInfection(
        @RequestParam(name = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") date: LocalDate?,
    ): InfectionRes {
        return InfectionRes.from(publicDataService.getInfection(date ?: DateUtil.getDate()))
    }

    @ApiOperation("감염(시/도)")
    @GetMapping("/infection-region")
    fun getInfectionRegion(
        @RequestParam(name = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") date: LocalDate?
    ): InfectionRegionRes {
        return InfectionRegionRes.from(publicDataService.getInfectionRegion(date ?: DateUtil.getDate()))
    }

    @ApiOperation("접종(전국)")
    @GetMapping("/inoculation")
    fun getInoculation(): InoculationRes {
        return InoculationRes.from(publicDataService.getInoculation())
    }

    @ApiOperation("접종(시/도)")
    @GetMapping("/inoculation-region")
    fun getInoculationRegion(): InoculationRegionRes {
        return InoculationRegionRes.from(publicDataService.getInoculationRegion())
    }
}
