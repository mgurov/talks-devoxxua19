package com.github.mgurov.devoxxua19

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Component

@Component
class PurchaseOrderRepository(
    private val jdbcTemplate: NamedParameterJdbcTemplate
) {

    val insert = SimpleJdbcInsert(jdbcTemplate.jdbcTemplate)
        .withTableName("purchase_orders")
        .usingColumns("product", "quantity", "buyer", "segments")
        .usingGeneratedKeyColumns("id")

    fun save(po: PurchaseOrder): Long {
        val id = insert.executeAndReturnKey(
            mapOf(
                "product" to po.product,
                "quantity" to po.quantity,
                "buyer" to po.quantity,
                "segments" to "[]"
            )
        )

        return id.toLong()
    }

    fun load(id: Long): PurchaseOrder? {
        TODO()
    }
}