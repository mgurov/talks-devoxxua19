package com.github.mgurov.devoxxua19

import com.fasterxml.jackson.core.type.TypeReference
import com.fasterxml.jackson.databind.DeserializationFeature
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule
import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
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
                "buyer" to po.buyer,
                "segments" to postgresObjectMapper.writeValueAsString(po.segments)
            )
        )

        return id.toLong()
    }

    fun load(id: Long): PurchaseOrder? {
        return jdbcTemplate.queryForObject("""
            select * from purchase_orders 
            where id = :id
        """.trimIndent(), mapOf("id" to id), rowMapper)
    }

    fun findByProduct(product: String): List<PurchaseOrder> {
        return jdbcTemplate.query("""
            select * from purchase_orders 
            where product = :product
        """.trimIndent(), mapOf("product" to product), rowMapper)
    }

    private val rowMapper: (ResultSet, Int) -> PurchaseOrder = {rs, _ ->
        PurchaseOrder(
            rs.getString("product"),
            rs.getInt("quantity"),
            rs.getString("buyer"),
            postgresObjectMapper.readValue(rs.getString("segments"), segmentListType)
        )
    }

    private val segmentListType = object : TypeReference<List<Segment>>() {}
}

private val postgresObjectMapper = jacksonObjectMapper()
    .registerModule(JavaTimeModule())
    .disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES)