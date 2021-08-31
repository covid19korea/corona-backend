package com.corona.backend.errorbot.util

import java.util.Scanner
import java.util.StringJoiner
import javax.servlet.ServletRequest
import javax.servlet.http.HttpServletRequest

class RequestWrapper private constructor(private val request: HttpServletRequest) {

    companion object {
        fun of(request: ServletRequest) = RequestWrapper(request as HttpServletRequest)
    }

    fun headers(): Map<String, String> {
        val convertedHeaderMap = HashMap<String, String>()
        val headerMap = request.headerNames

        while (headerMap.hasMoreElements()) {
            val name = headerMap.nextElement()
            val value = request.getHeader(name)

            convertedHeaderMap[name] = value
        }
        return convertedHeaderMap
    }

    fun params(): Map<String, String> {
        val convertedParameterMap = HashMap<String, String>()
        val parameterMap = request.parameterMap

        for (key in parameterMap.keys) {
            val values = parameterMap[key]
            val valueString = StringJoiner(",")

            for (value in values!!) {
                valueString.add(value)
            }

            convertedParameterMap[key] = valueString.toString()
        }
        return convertedParameterMap
    }

    fun body(): String {
        val s = Scanner(request.inputStream, "UTF-8").useDelimiter("\\A")
        return if (s.hasNext()) s.next() else ""
    }

    fun requestUri() = "[${request.method}]  ${request.requestURI}"
}
