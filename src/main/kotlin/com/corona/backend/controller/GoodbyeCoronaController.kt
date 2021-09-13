package com.corona.backend.controller

import com.corona.backend.domain.infection.res.InfectionRegionRes
import com.corona.backend.domain.infection.res.InfectionRes
import com.corona.backend.infra.goodbye_corona.json.Infection
import com.corona.backend.infra.goodbye_corona.json.InfectionRegion
import com.corona.backend.service.GoodbyeCoronaService
import com.corona.backend.util.DateUtil
import io.swagger.annotations.ApiOperation
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate

@RequestMapping("/v2/data")
@RestController
class GoodbyeCoronaController(
    private val goodbyeCoronaService: GoodbyeCoronaService,
) {

    @ApiOperation("감염(전국)")
    @GetMapping("/infection")
    fun getInfection(
        @RequestParam(name = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") date: LocalDate?,
    ): Infection {
        return goodbyeCoronaService.getInfection(date ?: DateUtil.getDate())
    }

    @ApiOperation("감염(시/도)")
    @GetMapping("/infection-region")
    fun getInfectionRegion(
        @RequestParam(name = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") date: LocalDate?
    ): InfectionRegion {
        return goodbyeCoronaService.getInfectionRegion(date ?: DateUtil.getDate())
    }

}