package com.adevinta.springbootdocker

import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.RestController
import reactor.core.publisher.Mono

@RestController
class HelloController {

    @GetMapping("/hello")
    fun hello() = Mono.just("hello Dani&Roger!")
}