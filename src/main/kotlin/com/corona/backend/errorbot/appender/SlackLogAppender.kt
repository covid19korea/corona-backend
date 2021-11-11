package com.corona.backend.errorbot.appender

import ch.qos.logback.classic.spi.ILoggingEvent
import ch.qos.logback.classic.spi.StackTraceElementProxy
import ch.qos.logback.core.UnsynchronizedAppenderBase
import ch.qos.logback.core.util.ContextUtil
import com.corona.backend.errorbot.configs.LogConfig
import com.corona.backend.errorbot.entity.ErrorLog
import com.corona.backend.errorbot.util.JsonUtil
import com.corona.backend.errorbot.util.MDCUtil
import net.gpedro.integrations.slack.SlackApi
import net.gpedro.integrations.slack.SlackAttachment
import net.gpedro.integrations.slack.SlackField
import net.gpedro.integrations.slack.SlackMessage
import java.time.format.DateTimeFormatter

class SlackLogAppender(
    private val logConfig: LogConfig,
) : UnsynchronizedAppenderBase<ILoggingEvent>() {

    override fun doAppend(eventObject: ILoggingEvent) {
        super.doAppend(eventObject)
    }

    override fun append(iLoggingEvent: ILoggingEvent) {
        if (iLoggingEvent.level.isGreaterOrEqual(logConfig.level)) {
            val errorLog = getErrorLog(iLoggingEvent)
            if (logConfig.slack.enabled) {
                toSlack(errorLog)
            }
        }
    }

    private fun toSlack(errorLog: ErrorLog?) {
        val slackApi = SlackApi(logConfig.slack.webHookUrl)
        val fields = generateSlackFields(errorLog)
        val slackAttachment = generateSlackAttachment(fields, errorLog)
        val slackMessage = generateSlackMessage(slackAttachment)

        slackApi.call(slackMessage)
    }

    private fun generateSlackMessage(slackAttachment: SlackAttachment): SlackMessage {
        return SlackMessage("").apply {
            setChannel("#" + logConfig.slack.channel)
            setUsername("ErrorBot")
            setIcon(":exclamation:")
            setAttachments(listOf(slackAttachment))
        }
    }

    private fun generateSlackAttachment(fields: MutableList<SlackField>, errorLog: ErrorLog?): SlackAttachment {
        return SlackAttachment().apply {
            setFallback("서버 에러 발생!")
            setColor("danger")
            setFields(fields)
            setTitle(errorLog!!.message)
            setText(errorLog.trace)
        }
    }

    private fun generateSlackFields(errorLog: ErrorLog?): MutableList<SlackField> {
        return mutableListOf<SlackField>().apply {
            add(generateSlackField("에러 내용", errorLog?.message, false))
            add(
                generateSlackField(
                    "발생 시간",
                    errorLog?.time?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")),
                    false
                )
            )
            add(generateSlackField("요청 URI", errorLog?.path, false))
            add(generateSlackField("HTTP Header", JsonUtil.toPrettyJson(errorLog!!.headerMap!!), true))
            add(generateSlackField("Request Param", JsonUtil.toPrettyJson(errorLog.parameterMap!!), true))
            add(
                generateSlackField(
                    "HTTP Body",
                    JsonUtil.toPrettyJson(errorLog!!.body!!)
                        .replace(Regex("(\\\\r\\\\n)|(\\\\n)|(\\\\)| *"), "")
                        .trim('"'),
                    false
                )
            )
            add(generateSlackField("사용자 환경", JsonUtil.toPrettyJson(errorLog.agentDetail!!), true))
            add(generateSlackField("서버 호스트", errorLog.hostName, true))
            add(generateSlackField("사용자 정보", JsonUtil.toPrettyJson(errorLog.userInfo!!), false))
        }
    }

    private fun generateSlackField(title: String, value: String?, isShorten: Boolean): SlackField {
        return SlackField().apply {
            setTitle(title)
            setValue(value)
            setShorten(isShorten)
        }
    }

    fun getErrorLog(loggingEvent: ILoggingEvent): ErrorLog? {
        if (loggingEvent.level.isGreaterOrEqual(logConfig.level)) {
            val errorLog = ErrorLog(
                system = "corona-backend",
                loggerName = loggingEvent.loggerName,
                serverName = "corona-backend",
                hostName = getHostName(),
                body = MDCUtil.get(MDCUtil.REQUEST_BODY),
                path = MDCUtil.get(MDCUtil.REQUEST_URI),
                message = loggingEvent.formattedMessage,
                headerMap = MDCUtil.get(MDCUtil.HEADER_MAP),
                parameterMap = MDCUtil.get(MDCUtil.REQUEST_PARAM),
                userInfo = MDCUtil.get(MDCUtil.USER_INFO),
                agentDetail = MDCUtil.get(MDCUtil.AGENT_DETAIL),
            )

            if (loggingEvent.throwableProxy != null) {
                errorLog.trace = getStackTrace(loggingEvent.throwableProxy.stackTraceElementProxyArray) ?: ""
            }

            return errorLog
        }

        return null
    }

    private fun getHostName(): String? {
        try {
            return ContextUtil.getLocalHostName()
        } catch (e: Exception) {
            // ignored
        }
        return null
    }

    private fun getStackTrace(stackTraceElements: Array<StackTraceElementProxy>?): String? {
        if (stackTraceElements == null || stackTraceElements.isEmpty()) {
            return null
        }
        val sb = StringBuilder()
        for (element in stackTraceElements) {
            sb.append(element.toString())
            sb.append("\n")
        }
        return sb.toString()
    }
}
