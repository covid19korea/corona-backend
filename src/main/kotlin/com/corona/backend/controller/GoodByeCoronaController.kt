package com.corona.backend.controller

import com.corona.backend.domain.infection.res.InfectionRegionRes
import com.corona.backend.domain.infection.res.InfectionRes
import com.corona.backend.service.GoodbyeCoronaService
import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RequestMapping("/v1/data")
@RestController
class GoodByeCoronaController(
    private val goodbyeCoronaService: GoodbyeCoronaService
) {

    @ApiOperation("감염(전국)")
    @GetMapping("/infection")
    fun getInfection(): InfectionRes {
        return goodbyeCoronaService.getInfection()
    }

    @ApiOperation("감염(시/도)")
    @GetMapping("/infection-region")
    fun getInfectionRegion(): InfectionRegionRes {
        return goodbyeCoronaService.getInfectionRegion()
    }
}
