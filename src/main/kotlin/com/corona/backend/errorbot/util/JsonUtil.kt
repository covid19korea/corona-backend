package com.corona.backend.errorbot.util

import com.fasterxml.jackson.annotation.JsonInclude
import com.fasterxml.jackson.core.JsonParser
import com.fasterxml.jackson.core.JsonProcessingException
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.databind.MapperFeature
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.databind.SerializationFeature

object JsonUtil {

    private val mapper = ObjectMapper()

    init {
        mapper.setSerializationInclusion(JsonInclude.Include.NON_NULL)
        mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false)
        mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false)
        mapper.configure(MapperFeature.AUTO_DETECT_GETTERS, true)
        mapper.configure(MapperFeature.AUTO_DETECT_IS_GETTERS, true)
        mapper.configure(JsonParser.Feature.ALLOW_SINGLE_QUOTES, true)
        mapper.configure(MapperFeature.DEFAULT_VIEW_INCLUSION, false)
    }

    fun toJson(`object`: Any): String {
        return try {
            mapper.writeValueAsString(`object`)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    fun <T> fromJson(jsonStr: String, cls: Class<T>): T {
        return try {
            mapper.readValue(jsonStr, cls)
        } catch (e: Exception) {
            throw RuntimeException(e)
        }
    }

    fun toPrettyJson(json: String): String {
        val jsonObject = fromJson(json, Any::class.java)
        return try {
            mapper.writerWithDefaultPrettyPrinter().writeValueAsString(jsonObject)
        } catch (e: JsonProcessingException) {
            e.printStackTrace()
            return ""
        }
    }
}
