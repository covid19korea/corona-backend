package com.corona.backend.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController

@RestController
class PingPongController {

    @GetMapping("/ping")
    fun ping(): String {
        return "pong"
    }
}
