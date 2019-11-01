package com.github.mgurov.devoxxua19

import org.springframework.beans.factory.annotation.Autowired
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component

@Component
class TestData(
    private val jdbcTemplate: JdbcTemplate,
    private val purchaseOrderRepository: PurchaseOrderRepository
) {
    fun nextId(): Long {
        return jdbcTemplate.queryForObject("select nextval('test_sequence_dont_use_for_pro')", Long::class.java)!!
    }

    fun givenAPurchaseOrder(adjust: PurchaseOrderBuilder.() -> Unit): PurchaseOrder {
        val po = aPurchaseOrder(adjust)
        purchaseOrderRepository.save(po)
        return po
    }
}