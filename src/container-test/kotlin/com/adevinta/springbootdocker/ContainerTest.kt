package com.adevinta.springbootdocker

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.web.reactive.function.client.WebClient
import org.testcontainers.containers.GenericContainer
import org.testcontainers.containers.Network
import org.testcontainers.junit.jupiter.Container
import org.testcontainers.junit.jupiter.Testcontainers

class KGenericContainer(imageName: String) : GenericContainer<KGenericContainer>(imageName)

@Testcontainers
class ContainerTest {

    companion object {
        private const val postgresAlias = "mypostgres"
        private const val appPort = 8080
        private val network: Network = Network.newNetwork()

        @Container
        var postgres: KGenericContainer = KGenericContainer("postgres:13")
                .withNetwork(network)
                .withNetworkAliases(postgresAlias)
                .withEnv("POSTGRES_USER", "myuser")
                .withEnv("POSTGRES_PASSWORD", "mypassword")
                .withEnv("POSTGRES_DB", "mydb")

        @Container
        var app: KGenericContainer = KGenericContainer("spring-boot-docker:0.0.1-SNAPSHOT")
                .withNetwork(network)
                .dependsOn(postgres)
                .withEnv("DB_HOST", postgresAlias)
                .withExposedPorts(appPort)
    }

    @Test
    internal fun `should say hello again`() {
        val webClient = WebClient.builder()
                .baseUrl("http://localhost:${app.getMappedPort(appPort)}")
                .build()

        val actual = webClient
                .get()
                .uri("/hello")
                .exchangeToMono { it.bodyToMono(String::class.java) }
                .block()

        assertThat(actual).startsWith("Hello PostgreSQL 13")
    }
}
