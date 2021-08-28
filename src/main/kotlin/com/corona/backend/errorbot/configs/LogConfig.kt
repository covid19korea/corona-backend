package com.corona.backend.errorbot.configs

import ch.qos.logback.classic.Level
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Configuration

@Configuration
@ConfigurationProperties(prefix = "log")
class LogConfig {
    var level: Level = Level.ERROR
    var slack: Slack = Slack(false, "", "")

    class Slack(
        var enabled: Boolean,
        var webHookUrl: String,
        var channel: String,
    )
}
