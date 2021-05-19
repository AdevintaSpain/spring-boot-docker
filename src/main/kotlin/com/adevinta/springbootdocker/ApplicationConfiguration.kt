package com.adevinta.springbootdocker

import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.JdbcTemplate

@Configuration
class ApplicationConfiguration {

    @Bean
    fun helloRepository(jdbcTemplate: JdbcTemplate) = HelloJDBCRepository(jdbcTemplate)
}