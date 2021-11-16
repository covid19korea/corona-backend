package com.corona.backend.controller

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import java.lang.IllegalArgumentException
import java.time.LocalDateTime

@RestController
class PingPongController {

    @GetMapping("/ping")
    fun ping(): String {
        return "pong"
    }

    @GetMapping("/time")
    fun time(): String {
        return LocalDateTime.now().toString()
    }
}
