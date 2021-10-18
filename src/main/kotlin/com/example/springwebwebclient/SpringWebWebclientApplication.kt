package com.example.springwebwebclient

import io.ktor.client.HttpClient
import io.ktor.client.engine.cio.CIO
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.stereotype.Service
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import org.springframework.web.client.RestTemplate
import org.springframework.web.reactive.function.client.WebClient
import java.net.URI
import java.net.http.HttpRequest
import java.net.http.HttpResponse
import java.util.UUID

@SpringBootApplication
class SpringWebWebclientApplication

fun main(args: Array<String>) {
    runApplication<SpringWebWebclientApplication>(*args)
}

@RestController
class MyHelloController(
    private val delayService: DelayService,
) {
    @GetMapping("/hello")
    suspend fun helloThere() {
        trace("Hello there!")
        delayService.delay()
        trace("General Kenobi")
    }
}

@Service
class DelayService(
    private val javaClient: java.net.http.HttpClient,
) {
    suspend fun delay() {
        val request = HttpRequest.newBuilder()
            .uri(URI.create("$ENDPOINT_URL/$DELAY_SECONDS"))
            .GET()
            .build()

        javaClient.send(request, HttpResponse.BodyHandlers.ofString())
    }

    companion object {
        private const val ENDPOINT_URL = "https://httpbin.org/delay"
        private const val DELAY_SECONDS = 5
    }
}

data class UUIDServiceResponse(val uuid: UUID)

@Configuration
class HttpClientConfiguration {
    @Bean
    fun restTemplate(): RestTemplate = RestTemplate()

    @Bean
    fun ktorClient(): HttpClient = HttpClient(CIO)

    @Bean
    fun webClient(): WebClient = WebClient.create()

    @Bean
    fun javaClient(): java.net.http.HttpClient = java.net.http.HttpClient.newHttpClient()
}

/**
 * Simple console out, but appending the thread name to the message
 */
fun trace(msg: Any) {
    println("[${Thread.currentThread().name}] $msg")
}
