package com.corona.backend.errorbot.util

import org.slf4j.MDC

object MDCUtil {
    private val mdc = MDC.getMDCAdapter()

    const val HEADER_MAP = "HEADER_MAP"
    const val REQUEST_PARAM = "REQUEST_PARAM"
    const val REQUEST_BODY = "REQUEST_BODY"
    const val USER_INFO = "USER_INFO"
    const val REQUEST_URI = "REQUEST_URI"
    const val AGENT_DETAIL = "AGENT_DETAIL"

    fun set(key: String, value: String) = mdc.put(key, value)
    fun get(key: String): String? = mdc.get(key)

    fun setJsonValue(key: String, value: Any?) {
        try {
            if (value != null) {
                val json = JsonUtil.toJson(value)
                mdc.put(key, json)
            }
        } catch (e: Exception) {
            // ignored
        }
    }
}
