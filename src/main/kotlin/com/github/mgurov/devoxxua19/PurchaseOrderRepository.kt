package com.github.mgurov.devoxxua19

import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import org.springframework.jdbc.core.simple.SimpleJdbcInsert
import org.springframework.stereotype.Component
import java.sql.ResultSet

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
        return jdbcTemplate.queryForObject("select * from purchase_orders where id = :id", mapOf("id" to id), rowMapper)
    }

    val rowMapper: (ResultSet, Int) -> PurchaseOrder = {rs, _ ->
        PurchaseOrder(
            rs.getString("product"),
            rs.getInt("quantity"),
            rs.getString("buyer"),
            emptyList()
        )
    }
}