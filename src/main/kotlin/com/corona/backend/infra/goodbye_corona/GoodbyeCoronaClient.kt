package com.corona.backend.infra.goodbye_corona

import com.corona.backend.exception.PublicDataException
import io.netty.channel.ChannelOption
import io.netty.handler.timeout.ReadTimeoutHandler
import io.netty.handler.timeout.WriteTimeoutHandler
import org.springframework.http.client.reactive.ReactorClientHttpConnector
import org.springframework.stereotype.Component
import org.springframework.web.reactive.function.client.WebClient
import org.springframework.web.util.DefaultUriBuilderFactory
import reactor.netty.http.client.HttpClient
import reactor.netty.tcp.TcpClient
import java.util.concurrent.TimeUnit

@Component
class GoodbyeCoronaClient {
    private val CUSTOM_CONNECTION_TIMEOUT_MILLS = 15000
    private val CUSTOM_READ_TIMEOUT_MILLS = 15000L
    private val CUSTOM_WRITE_TIMEOUT_MILLS = 15000L

    fun getData(url: String): String {
        val webClient = generateWebClient(url)

        return webClient.get()
            .retrieve()
            .bodyToMono(String::class.java)
            .block() ?: throw PublicDataException()
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
                it.addHandlerFirst(ReadTimeoutHandler(CUSTOM_READ_TIMEOUT_MILLS, TimeUnit.MILLISECONDS))
                    .addHandlerLast(WriteTimeoutHandler(CUSTOM_WRITE_TIMEOUT_MILLS, TimeUnit.MILLISECONDS))
            }
        return tcpClient
    }
}
