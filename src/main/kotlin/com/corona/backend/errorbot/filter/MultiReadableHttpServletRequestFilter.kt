package com.corona.backend.errorbot.filter

import com.corona.backend.errorbot.util.MultiReadableHttpServletRequest
import javax.servlet.Filter
import javax.servlet.FilterChain
import javax.servlet.ServletRequest
import javax.servlet.ServletResponse
import javax.servlet.http.HttpServletRequest

class MultiReadableHttpServletRequestFilter : Filter {

    override fun doFilter(request: ServletRequest, response: ServletResponse, chain: FilterChain) {
        val multiReadRequest = MultiReadableHttpServletRequest(request as HttpServletRequest)
        chain.doFilter(multiReadRequest, response)
    }
}
