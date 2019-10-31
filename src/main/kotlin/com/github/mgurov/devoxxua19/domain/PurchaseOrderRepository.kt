package com.github.mgurov.devoxxua19.domain

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
        .usingColumns("product_code", "quantity", "buyer", "segments")
        .usingGeneratedKeyColumns("id")

    fun save(po: PurchaseOrder): Long {
        val id = insert.executeAndReturnKey(
            mapOf(
                "product_code" to po.productCode,
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

    private val rowMapper: (ResultSet, Int) -> PurchaseOrder = {rs, _ ->
        PurchaseOrder(
            rs.getString("product_code"),
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