package com.corona.backend.infra.publicdata

import com.corona.backend.exception.PublicDataException
import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.beans.factory.annotation.Value
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Component
import org.springframework.util.LinkedMultiValueMap
import org.springframework.util.MultiValueMap
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.DefaultUriBuilderFactory
import reactor.netty.http.client.HttpClient
import reactor.netty.tcp.TcpClient
import java.net.URLEncoder
import java.util.concurrent.TimeUnit.MILLISECONDS

@Component
class PublicDataClient(
    @Value("\${open-api.key}") private val secretKey: String,
) {

    private val CUSTOM_CONNECTION_TIMEOUT_MILLS = 15000
    private val CUSTOM_READ_TIMEOUT_MILLS = 15000L
    private val CUSTOM_WRITE_TIMEOUT_MILLS = 15000L

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
        val tcpClient = generateTcpClient()

        val factory = DefaultUriBuilderFactory(url)
        factory.encodingMode = DefaultUriBuilderFactory.EncodingMode.VALUES_ONLY
        return WebClient.builder()
            .clientConnector(ReactorClientHttpConnector(HttpClient.from(tcpClient)))
            .uriBuilderFactory(factory)
            .baseUrl(url)
            .build()
    }

    private fun generateTcpClient(): TcpClient {
        val tcpClient = TcpClient.create()
            .option(ChannelOption.CONNECT_TIMEOUT_MILLIS, CUSTOM_CONNECTION_TIMEOUT_MILLS)
            .doOnConnected {
                it.addHandlerFirst(ReadTimeoutHandler(CUSTOM_READ_TIMEOUT_MILLS, MILLISECONDS))
                    .addHandlerLast(WriteTimeoutHandler(CUSTOM_WRITE_TIMEOUT_MILLS, MILLISECONDS))
            }
        return tcpClient
    }

    private fun setSecretKey(queryParam: MultiValueMap<String, String>?): MultiValueMap<String, String> {
        return queryParam?.apply {
            add("ServiceKey", URLEncoder.encode(secretKey, "UTF-8"))
        } ?: LinkedMultiValueMap<String, String>().apply {
            add("ServiceKey", URLEncoder.encode(secretKey, "UTF-8"))
        }
    }
}
