package com.corona.backend.errorbot.entity

import java.time.LocalDateTime

data class ErrorLog(
    var system: String? = "",
    var loggerName: String? = "",
    var serverName: String? = "",
    var hostName: String? = "",
    var path: String? = "",
    var body: String? = "",
    var message: String? = "",
    var trace: String = "",
    var time: LocalDateTime = LocalDateTime.now(),
    var headerMap: String? = "",
    var parameterMap: String? = "",
    var userInfo: String? = "",
    var agentDetail: String? = "",
)
