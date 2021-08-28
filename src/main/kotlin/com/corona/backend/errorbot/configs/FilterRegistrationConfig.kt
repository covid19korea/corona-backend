package com.corona.backend.errorbot.configs

import com.corona.backend.errorbot.filter.LogbackMdcFilter
import com.corona.backend.errorbot.filter.MultiReadableHttpServletRequestFilter
import org.springframework.boot.web.servlet.FilterRegistrationBean
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class FilterRegistrationConfig {

    @Bean
    fun multiReadableHttpServletRequestFilterRegistrationBean(): FilterRegistrationBean<MultiReadableHttpServletRequestFilter> {
        val registrationBean = FilterRegistrationBean<MultiReadableHttpServletRequestFilter>()
        val multiReadableHttpServletRequestFilter = MultiReadableHttpServletRequestFilter()
        registrationBean.filter = multiReadableHttpServletRequestFilter
        registrationBean.order = 1
        return registrationBean
    }

    @Bean
    fun logbackMdcFilterRegistrationBean(): FilterRegistrationBean<LogbackMdcFilter> {
        val registrationBean = FilterRegistrationBean<LogbackMdcFilter>()
        val logbackMdcFilter = LogbackMdcFilter()
        registrationBean.filter = logbackMdcFilter
        registrationBean.order = 2
        return registrationBean
    }
}
