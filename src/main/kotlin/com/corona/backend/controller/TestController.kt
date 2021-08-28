package com.corona.backend.controller

import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RestController

@RestController
class TestController {

    @PostMapping("/test")
    fun test(@RequestBody dto: Dto) {
        throw IllegalArgumentException("test value is null")
    }
}
