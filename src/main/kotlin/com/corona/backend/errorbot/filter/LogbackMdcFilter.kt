package com.corona.backend.errorbot.filter

import com.corona.backend.errorbot.util.AgentUtil
import com.corona.backend.errorbot.util.HttpUtil
import com.corona.backend.errorbot.util.MDCUtil
import com.corona.backend.errorbot.util.RequestWrapper
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class LogbackMdcFilter : Filter {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val requestWrapper = RequestWrapper.of(request)

        MDCUtil.setJsonValue(MDCUtil.HEADER_MAP, requestWrapper.headers())
        MDCUtil.setJsonValue(MDCUtil.REQUEST_PARAM, requestWrapper.params())
        MDCUtil.setJsonValue(MDCUtil.REQUEST_BODY, requestWrapper.body())
        MDCUtil.setJsonValue(MDCUtil.USER_INFO, HttpUtil.getCurrentUser())
        MDCUtil.setJsonValue(MDCUtil.AGENT_DETAIL, AgentUtil.getAgentDetail(request as HttpServletRequest))
        MDCUtil.setJsonValue(MDCUtil.REQUEST_URI, requestWrapper.requestUri())

        chain.doFilter(request, response)
    }
}
