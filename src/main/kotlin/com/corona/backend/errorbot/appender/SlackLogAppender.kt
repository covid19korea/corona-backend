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

        val fields: MutableList<SlackField> = mutableListOf<SlackField>().apply {
            add(
                SlackField().apply {
                    setTitle("에러 내용")
                    setValue(errorLog?.message)
                    setShorten(false)
                }
            )
            add(
                SlackField().apply {
                    setTitle("발생 시간")
                    setValue(errorLog?.time?.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")))
                    setShorten(false)
                }
            )
            add(
                SlackField().apply {
                    setTitle("요청 URI")
                    setValue(errorLog?.path)
                    setShorten(false)
                }
            )
            add(
                SlackField().apply {
                    setTitle("HTTP Header")
                    setValue(JsonUtil.toPrettyJson(errorLog!!.headerMap!!))
                    setShorten(true)
                }
            )
            add(
                SlackField().apply {
                    setTitle("Request Param")
                    setValue(JsonUtil.toPrettyJson(errorLog!!.parameterMap!!))
                    setShorten(true)
                }
            )
            add(
                SlackField().apply {
                    setTitle("HTTP Body")
                    setValue(
                        JsonUtil.toPrettyJson(errorLog!!.body!!)
                            .replace(Regex("(\\\\r\\\\n)|(\\\\n)|(\\\\)| *"), "")
                            .trim('"')
                    )
                    setShorten(false)
                }
            )
            add(
                SlackField().apply {
                    setTitle("사용자 환경")
                    setValue(JsonUtil.toPrettyJson(errorLog!!.agentDetail!!))
                    setShorten(true)
                }
            )
            add(
                SlackField().apply {
                    setTitle("서버")
                    setValue(errorLog?.serverName)
                    setShorten(true)
                }
            )
            add(
                SlackField().apply {
                    setTitle("서버 호스트")
                    setValue(errorLog?.hostName)
                    setShorten(true)
                }
            )
            add(
                SlackField().apply {
                    setTitle("사용자 정보")
                    setValue(JsonUtil.toPrettyJson(errorLog?.userInfo!!))
                    setShorten(false)
                }
            )
        }

        val slackAttachment = SlackAttachment().apply {
            setFallback("서버 에러 발생!")
            setColor("danger")
            setFields(fields)
            setTitle(errorLog!!.message)
            setText(errorLog.trace)
        }

        val slackMessage = SlackMessage("").apply {
            setChannel("#" + logConfig.slack.channel)
            setUsername("ErrorBot")
            setIcon(":exclamation:")
            setAttachments(listOf(slackAttachment))
        }

        slackApi.call(slackMessage)
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

    fun getHostName(): String? {
        try {
            return ContextUtil.getLocalHostName()
        } catch (e: Exception) {
            // ignored
        }
        return null
    }

    fun getStackTrace(stackTraceElements: Array<StackTraceElementProxy>?): String? {
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
