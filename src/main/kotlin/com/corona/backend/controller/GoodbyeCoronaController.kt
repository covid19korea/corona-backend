package com.corona.backend.controller

import com.corona.backend.domain.infection.res.InfectionRegionRes
import com.corona.backend.domain.infection.res.InfectionRes
import com.corona.backend.service.GoodbyeCoronaService
import com.corona.backend.util.DateUtil
import io.swagger.annotations.ApiOperation
import org.springframework.format.annotation.DateTimeFormat
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestParam
import org.springframework.web.bind.annotation.RestController
import java.time.LocalDate
import java.time.LocalDateTime

@RequestMapping("/v1/data")
@RestController
class GoodbyeCoronaController(
    private val goodbyeCoronaService: GoodbyeCoronaService,
) {

    @ApiOperation("감염(전국)")
    @GetMapping("/infection")
    fun getInfection(
        @RequestParam(name = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") date: LocalDate?,
    ): InfectionRes {
        return InfectionRes.from(goodbyeCoronaService.getInfection(date ?: DateUtil.getDate()))
    }

    @ApiOperation("감염(시/도)")
    @GetMapping("/infection-region")
    fun getInfectionRegion(
        @RequestParam(name = "date", required = false) @DateTimeFormat(pattern = "yyyy-MM-dd") date: LocalDate?
    ): InfectionRegionRes {
        return InfectionRegionRes.from(goodbyeCoronaService.getInfectionRegion(date ?: DateUtil.getDate()))
    }

    @GetMapping("/test")
    fun getDate(): String {
        val nowSeoul = DateUtil.getDate()
        val nowAmerica = LocalDateTime.now()

        return "seoul = $nowSeoul, America = $nowAmerica"
    }
}
