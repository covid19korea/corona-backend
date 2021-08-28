package com.corona.backend.errorbot.configs

import ch.qos.logback.classic.LoggerContext
import com.corona.backend.errorbot.appender.SlackLogAppender
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.InitializingBean
import org.springframework.context.annotation.Configuration
import kotlin.jvm.Throws

@Configuration
class LogContextConfig(
    private val logConfig: LogConfig,
) : InitializingBean {

    @Throws(Exception::class)
    override fun afterPropertiesSet() {
        val loggerContext = LoggerFactory.getILoggerFactory() as LoggerContext

        val slackLogAppender = SlackLogAppender(logConfig).apply {
            context = loggerContext
            name = "slackLogbackAppender"
        }
        slackLogAppender.start()

        loggerContext.getLogger("ROOT").addAppender(slackLogAppender)
    }
}
