package com.adevinta.springbootdocker

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class HelloController(private val helloRepository: HelloRepository) {

    @GetMapping("/hello")
    fun hello(): Mono<String> {
        return Mono.just("Hello ${helloRepository.getVersion()}")
    }
}