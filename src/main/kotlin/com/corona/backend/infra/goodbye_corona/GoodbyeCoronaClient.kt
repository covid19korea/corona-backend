package com.corona.backend.infra.goodbye_corona

import com.corona.backend.exception.PublicDataException
import org.springframework.stereotype.Component
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.DefaultUriBuilderFactory

@Component
class GoodbyeCoronaClient {
    fun getData(url: String, queryParam: MultiValueMap<String, String>?): String {
        val webClient = generateWebClient(url)

        return webClient.get()
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
}
