package com.github.mgurov.devoxxua19.domain

import com.github.mgurov.devoxxua19.softly
import org.junit.jupiter.api.Test
import org.springframework.jdbc.core.JdbcTemplate
import org.springframework.stereotype.Component
import java.time.LocalDate

@Component
class TestData(private val jdbcTemplate: JdbcTemplate) {
    fun nextId(): Long {
        return jdbcTemplate.queryForObject("select nextval('test_sequence_dont_use_for_pro')", Long::class.java)!!
    }
}

fun aPurchaseOrder(adjuster: PurchaseOrderBuilder.() -> Unit): PurchaseOrder {
    val builder = PurchaseOrderBuilder()
    builder.adjuster()
    return builder.build()
}

class PurchaseOrderBuilder(
    var productCode: String = "whatever product",
    var quantity: Int? = null,
    var buyer: String = "anonymous",
    private val segments: MutableList<Segment> = mutableListOf()
) {
    fun build(): PurchaseOrder {
        val segmentsList = segments.toList()
        return PurchaseOrder(
            productCode = productCode,
            quantity = quantity ?: segmentsList.sumQuantity(),
            buyer = buyer,
            segments = segmentsList
        )
    }

    fun segment(adjuster: SegmentBuilder.() -> Unit) {
        val builder = SegmentBuilder()
        builder.adjuster()
        this.segments += builder.build()
    }
}

class SegmentBuilder(
    var status: SegmentStatus = SegmentStatus.NEW,
    var quantity: Int = 1,
    var expectedDate: LocalDate = LocalDate.now().plusDays(1)
) {
    fun build() = Segment(
        status = status,
        quantity = quantity,
        expectedDate = expectedDate
    )
}

class TestDataTests {
    @Test
    fun `order quantity should equal sum segment quantity`() {
        val order = aPurchaseOrder { quantity = 10 }

        softly {
            assertThat(order.quantity).isEqualTo(10)
            assertThat(order.segments.sumQuantity()).isEqualTo(10)
        }
    }
}