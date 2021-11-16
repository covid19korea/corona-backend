package com.corona.backend.scheduler

import com.corona.backend.cacher.DataCacher
import com.corona.backend.errorbot.configs.LogConfig
import com.corona.backend.errorbot.entity.ErrorLog
import com.corona.backend.service.PublicDataService
import com.corona.backend.util.DateUtil
import net.gpedro.integrations.slack.SlackApi
import net.gpedro.integrations.slack.SlackAttachment
import net.gpedro.integrations.slack.SlackMessage
import org.springframework.scheduling.annotation.Scheduled
import org.springframework.stereotype.Component

@Component
class CacheScheduler(
    private val publicDataService: PublicDataService,
    private val dataCacher: DataCacher,
    private val logConfig: LogConfig,
) {

    @Scheduled(cron = "0 15 1 * * *") // 한국시간기준 오전 10시 15분
    fun cache() {
        cacheTodayData()
    }

    private fun cacheTodayData() {
        try {
            cacheAll()
        } catch (e: Exception) {
            handleCacheError()
            pushSlack(e)
        }
    }

    private fun cacheAll() {
        publicDataService.getInfection()
        publicDataService.getInfectionRegion()
        publicDataService.getInoculation()
        publicDataService.getInoculationRegion()
    }

    private fun handleCacheError() {
        dataCacher.cache(DateUtil.getDate(), dataCacher.getInfection(DateUtil.getDate(1))!!)
        dataCacher.cache(DateUtil.getDate(), dataCacher.getInfectionRegion(DateUtil.getDate(1))!!)
        dataCacher.cache(DateUtil.getDate(), dataCacher.getInoculation(DateUtil.getDate(1))!!)
        dataCacher.cache(DateUtil.getDate(), dataCacher.getInoculationRegion(DateUtil.getDate(1))!!)
    }

    private fun pushSlack(e: Exception) {
        val slackApi = SlackApi(logConfig.slack.webHookUrl)
        slackApi.call(generateSlackMessage(generateSlackAttachment(ErrorLog(trace = generateStackTrace(e)))))
    }

    private fun generateSlackMessage(slackAttachment: SlackAttachment): SlackMessage {
        return SlackMessage("").apply {
            setChannel("#" + logConfig.slack.channel)
            setUsername("ErrorBot")
            setIcon(":exclamation:")
            setAttachments(listOf(slackAttachment))
        }
    }

    private fun generateSlackAttachment(errorLog: ErrorLog?): SlackAttachment {
        return SlackAttachment().apply {
            setFallback("서버 에러 발생!")
            setColor("danger")
            setTitle(errorLog!!.message)
            setText(errorLog.trace)
        }
    }

    private fun generateStackTrace(e: Exception): String {
        val sb = StringBuilder()
        sb.append(e.javaClass.name).append('\n')
        e.stackTrace
            .forEach { sb.append("${it.className}:${it.lineNumber}\n") }
        return sb.toString()
    }
}
