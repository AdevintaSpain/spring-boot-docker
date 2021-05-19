package com.adevinta.springbootdocker

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.web.reactive.function.client.WebClient
import org.testcontainers.containers.GenericContainer
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers


class KGenericContainer(imageName: String) : GenericContainer<KGenericContainer>(imageName)

@Testcontainers
class ContainerTest {

    companion object {
        private const val port = 8080

        @Container
        var app: KGenericContainer = KGenericContainer("spring-boot-docker:0.0.1-SNAPSHOT")
                .withExposedPorts(port)
    }

    @Test
    internal fun `should say hello again`() {
        val mappedPort = app.getMappedPort(port)

        val webClient = WebClient.builder()
                .baseUrl("http://localhost:$mappedPort")
                .build()

        val actual = webClient
                .get()
                .uri("/hello")
                .exchangeToMono { it.bodyToMono(String::class.java) }
                .block()

        assertThat(actual).isEqualTo("Hello Dani&Roger!")
    }
}
