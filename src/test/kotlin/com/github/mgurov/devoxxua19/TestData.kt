package com.github.mgurov.devoxxua19

import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class TestData(
    private val jdbcTemplate: JdbcTemplate
) {
    fun nextId(): Long {
        return jdbcTemplate.queryForObject("select nextval('test_sequence_dont_use_for_pro')", Long::class.java)!!
    }
}