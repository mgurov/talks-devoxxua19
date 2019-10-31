package com.github.mgurov.devoxxua19

import org.junit.jupiter.api.Test
import java.time.LocalDate

class PurchaseOrderFixtureTests {
    @Test
    fun `should default to quantity 1`() {
        val actual = aPurchaseOrder()

        softly {
            assertThat(actual.quantity).isEqualTo(1)
            assertThat(actual.segments.sumQuantity()).isEqualTo(1)
        }
    }
}

fun aPurchaseOrder(
    product: String = "whatever product",
    quantity: Int? = null,
    buyer: String = "me, handsome",
    segments: List<Segment> = listOf(defaultSegment.copy(quantity = quantity ?: 1))
): PurchaseOrder {
    return PurchaseOrder(
        product = product,
        quantity = quantity ?: segments.toList().sumQuantity(),
        buyer = buyer,
        segments = segments.toList()
    )
}

fun aSegment(
    quantity: Int = 1,
    status: SegmentStatus = SegmentStatus.NEW,
    date: LocalDate = LocalDate.now().plusDays(1)
) = Segment(
    quantity = quantity,
    status = status,
    date = date
)

val defaultSegment = Segment(quantity = 1, status = SegmentStatus.NEW, date = LocalDate.now().plusDays(1))