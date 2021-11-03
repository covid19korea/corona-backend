package com.corona.backend.infra.publicdata

import com.corona.backend.exception.PublicDataException
import org.springframework.beans.factory.annotation.Value
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.DefaultUriBuilderFactory
import java.net.URLEncoder

@Component
class PublicDataClient(
    @Value("\${open-api.key}") private val secretKey: String,
) {

    fun getData(url: String, queryParam: MultiValueMap<String, String>?): String {
        val webClient = generateWebClient(url)
        val params = setSecretKey(queryParam)

        return webClient.get()
            .uri { uriBuilder ->
                uriBuilder
                    .queryParams(params)
                    .build()
            }
            .retrieve()
            .bodyToMono(String::class.java)
            .block() ?: throw PublicDataException()
    }

    fun getData(url: String): String {
        return getData(url, null)
    }

    private fun generateWebClient(url: String): WebClient {
        val factory = DefaultUriBuilderFactory(url)
        factory.encodingMode = DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY
        return WebClient.builder().uriBuilderFactory(factory).baseUrl(url).build()
    }

    private fun setSecretKey(queryParam: MultiValueMap<String, String>?): MultiValueMap<String, String> {
        return queryParam?.apply {
            add("ServiceKey", URLEncoder.encode(secretKey, "UTF-8"))
        } ?: LinkedMultiValueMap<String, String>().apply {
            add("ServiceKey", URLEncoder.encode(secretKey, "UTF-8"))
        }
    }
}
