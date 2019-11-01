package com.github.mgurov.devoxxua19.domain

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions
import org.junit.jupiter.api.Test
import java.time.LocalDate

fun aPurchaseOrder(
    productCode: String = "whatever product",
    quantity: Int = 1,
    buyer: String = "anonymous"
): PurchaseOrder {
    return PurchaseOrder(
        productCode = productCode,
        quantity = quantity,
        buyer = buyer,
        segments = listOf(aSegment(quantity = quantity))
    )
}

fun aSegment(
    status: SegmentStatus = SegmentStatus.NEW,
    quantity: Int = 1,
    expectedDate: LocalDate = LocalDate.now().plusDays(1)
) = Segment(
    status = status,
    quantity = quantity,
    expectedDate = expectedDate
)

class TestDataTests {
    @Test
    fun `order quantity should equal sum segment quantity`() {
        val order = aPurchaseOrder(quantity = 9)

        softly {
            assertThat(order.quantity).isEqualTo(10)
            assertThat(order.segments.sumBy { it.quantity }).isEqualTo(10)
        }

    }
}

private fun softly(assertions: SoftAssertions.() -> Unit) {
    SoftAssertions.assertSoftly(assertions)
}
