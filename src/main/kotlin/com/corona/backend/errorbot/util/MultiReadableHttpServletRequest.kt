package com.corona.backend.errorbot.util

import org.apache.tomcat.util.http.fileupload.IOUtils
import java.io.ByteArrayInputStream
import java.io.ByteArrayOutputStream
import java.io.IOException
import javax.servlet.ReadListener
import javax.servlet.ServletInputStream
import javax.servlet.http.HttpServletRequest
import javax.servlet.http.HttpServletRequestWrapper

class MultiReadableHttpServletRequest(request: HttpServletRequest) : HttpServletRequestWrapper(request) {

    private var cachedBytes: ByteArrayOutputStream? = null

    @Throws(IOException::class)
    override fun getInputStream(): ServletInputStream {
        if (cachedBytes == null) {
            cacheInputStream()
        }
        return CachedServletInputStream()
    }

    @Throws(IOException::class)
    private fun cacheInputStream() {
        cachedBytes = ByteArrayOutputStream()
        IOUtils.copy(super.getInputStream(), cachedBytes)
    }

    inner class CachedServletInputStream(
        private val input: ByteArrayInputStream = ByteArrayInputStream(cachedBytes!!.toByteArray())
    ) : ServletInputStream() {

        @Throws(IOException::class)
        override fun read() = input.read()
        override fun isFinished() = false
        override fun isReady() = true
        override fun setReadListener(listener: ReadListener?) {}
    }
}
