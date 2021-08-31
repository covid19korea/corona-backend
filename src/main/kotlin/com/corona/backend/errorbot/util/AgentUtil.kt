package com.corona.backend.errorbot.util

import eu.bitwalker.useragentutils.Browser
import eu.bitwalker.useragentutils.BrowserType
import eu.bitwalker.useragentutils.DeviceType
import eu.bitwalker.useragentutils.Manufacturer
import eu.bitwalker.useragentutils.OperatingSystem
import eu.bitwalker.useragentutils.RenderingEngine
import eu.bitwalker.useragentutils.UserAgent
import eu.bitwalker.useragentutils.Version
import javax.servlet.http.HttpServletRequest

object AgentUtil {

    fun getAgentDetail(request: HttpServletRequest): Map<String, String> {
        val agentDetail = HashMap<String, String>()
        agentDetail["browser"] = getBrowser().toString()
        agentDetail["browserType"] = getBrowserType().toString()
        agentDetail["browserVersion"] = getBrowserVersion().toString()
        agentDetail["renderingEngine"] = getRenderingEngine().toString()
        agentDetail["os"] = getUserOs().toString()
        agentDetail["deviceType"] = getDeviceType().toString()
        agentDetail["manufacturer"] = getManufacturer().toString()
        return agentDetail
    }

    fun getUserAgentString(request: HttpServletRequest): String = request.getHeader("User-Agent")

    fun getUserAgent(request: HttpServletRequest): UserAgent? {
        return try {
            val userAgentString = getUserAgentString(request)
            UserAgent.parseUserAgentString(userAgentString)
        } catch (e: Exception) {
            e.printStackTrace()
            return null
        }
    }

    fun getUserOs(): OperatingSystem = getUserOs(HttpUtil.getCurrentRequest())

    fun getUserOs(request: HttpServletRequest): OperatingSystem {
        val userAgent = getUserAgent(request)
        return if (userAgent == null) {
            OperatingSystem.UNKNOWN
        } else userAgent.operatingSystem
    }

    fun getBrowser(): Browser = getBrowser(HttpUtil.getCurrentRequest())

    fun getBrowser(request: HttpServletRequest): Browser {
        val userAgent = getUserAgent(request)
        return if (userAgent == null) {
            Browser.UNKNOWN
        } else userAgent.browser
    }

    fun getBrowserVersion(): Version? = getBrowserVersion(HttpUtil.getCurrentRequest())

    fun getBrowserVersion(request: HttpServletRequest): Version? {
        val userAgent = getUserAgent(request)
        return if (userAgent == null) {
            Version("0", "0", "0")
        } else userAgent.browserVersion
    }

    fun getBrowserType(): BrowserType = getBrowserType(HttpUtil.getCurrentRequest())

    fun getBrowserType(request: HttpServletRequest): BrowserType {
        val browser = getBrowser(request)
        return if (browser == null) {
            BrowserType.UNKNOWN
        } else browser.browserType
    }

    fun getRenderingEngine(): RenderingEngine = getRenderingEngine(HttpUtil.getCurrentRequest())

    fun getRenderingEngine(request: HttpServletRequest): RenderingEngine {
        val browser = getBrowser(request)
        return if (browser == null) {
            RenderingEngine.OTHER
        } else browser.renderingEngine
    }

    fun getDeviceType(): DeviceType = getDeviceType(HttpUtil.getCurrentRequest())

    fun getDeviceType(request: HttpServletRequest): DeviceType {
        val os = getUserOs(request)
        return if (os == null) {
            DeviceType.UNKNOWN
        } else os.deviceType
    }

    fun getManufacturer() = getManufacturer(HttpUtil.getCurrentRequest())

    fun getManufacturer(request: HttpServletRequest): Manufacturer {
        val os = getUserOs(request)
        return if (os == null) {
            Manufacturer.OTHER
        } else os.manufacturer
    }
}
