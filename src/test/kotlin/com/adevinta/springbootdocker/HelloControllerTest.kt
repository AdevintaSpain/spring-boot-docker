package com.adevinta.springbootdocker

import org.junit.jupiter.api.Test
import org.mockito.Mockito.doReturn
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest
import org.springframework.boot.test.mock.mockito.MockBean
import org.springframework.test.web.reactive.server.WebTestClient
import org.springframework.test.web.reactive.server.expectBody

@WebFluxTest(controllers = [HelloController::class])
class HelloControllerTest {

    @Autowired
    private lateinit var webClient: WebTestClient

    @MockBean
    private lateinit var helloRepository: HelloRepository

    @Test
    internal fun `should say hello`() {
        val version = "Dummy 1.0"
        doReturn(version).`when`(helloRepository).getVersion()

        webClient
                .get().uri("/hello")
                .exchange()
                .expectStatus().isOk
                .expectBody<String>()
                .isEqualTo("Hello $version")
    }
}
