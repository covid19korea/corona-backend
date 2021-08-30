package com.corona.backend.controller

import io.swagger.annotations.ApiOperation
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @ApiOperation("에러봇 테스트")
    @PostMapping("/test")
    fun test(@RequestBody dto: Dto) {
        throw IllegalArgumentException("Errorbot Test")
    }
}
