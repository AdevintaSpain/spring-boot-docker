package com.adevinta.springbootdocker

import org.springframework.jdbc.core.JdbcTemplate

class HelloJDBCRepository(private val jdbcTemplate: JdbcTemplate) : HelloRepository {
    override fun getVersion(): String = jdbcTemplate.queryForObject("SELECT version();", String::class.java)!!
}
