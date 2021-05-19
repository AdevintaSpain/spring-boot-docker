package com.adevinta.springbootdocker

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.boot.test.context.SpringBootTest
import org.springframework.boot.test.context.SpringBootTest.WebEnvironment.RANDOM_PORT
import org.springframework.boot.web.server.LocalServerPort
import org.springframework.web.reactive.function.client.WebClient

@SpringBootTest(webEnvironment = RANDOM_PORT)
class ApplicationTest {

    @LocalServerPort
    private var port: Int = 0

    @Test
    internal fun `should say hello`() {
        val webClient = WebClient.builder()
                .baseUrl("http://localhost:$port")
                .build()

        val actual = webClient
                .get()
                .uri("/hello")
                .exchangeToMono { it.bodyToMono(String::class.java) }
                .block()

        assertThat(actual).isEqualTo("Hello Dani&Roger!")
    }
}
