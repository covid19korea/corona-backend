package com.corona.backend.util

import com.fasterxml.jackson.dataformat.xml.XmlMapper
import org.springframework.stereotype.Component

@Component
class XmlParser {

    fun <T> parse(xml: String, type: Class<T>): T {
        val xmlMapper = XmlMapper()
        return xmlMapper.readValue(xml, type)
    }
}
