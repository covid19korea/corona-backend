package com.corona.backend.errorbot.util

import org.springframework.web.context.request.RequestContextHolder
import org.springframework.web.context.request.ServletRequestAttributes
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletResponse

object HttpUtil {

    fun getCurrentRequest(): HttpServletRequest {
        val req = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
        return req.request
    }

    fun getCurrentResponse(): HttpServletResponse? {
        val req = RequestContextHolder.getRequestAttributes() as ServletRequestAttributes
        return req.response
    }

    fun getCurrentUser(): String {
        // security context holder를 건드려야함
        return "USER"
    }
}
