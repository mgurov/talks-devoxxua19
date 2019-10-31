package com.github.mgurov.devoxxua19

import org.assertj.core.api.Assertions.tuple
import org.junit.jupiter.api.Test
import java.time.LocalDate

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

private fun aPurchaseOrderDsl(adjust: PurchaseOrderBuilder.() -> Unit): PurchaseOrder {
    val builder = PurchaseOrderBuilder()
    builder.adjust()
    return builder.build()
}

class PurchaseOrderBuilder(
    var product: String = "whatever product",
    var buyer: String = "me, handsome",
    val segments: MutableList<Segment> = mutableListOf()
) {
    fun build() : PurchaseOrder {

        if (segments.isEmpty()) {
            segments += defaultSegment
        }

        return PurchaseOrder(
            product = product,
            quantity = segments.sumQuantity(),
            buyer = buyer,
            segments = segments //TODO: check whether we need copy or not
        )
    }

    fun aSegmentDsl(adjust: SegmentBuilder.() -> Unit) {
        val builder = SegmentBuilder()
        builder.adjust()
        segments += builder.build()
    }
}

class SegmentBuilder(
    var quantity: Int = 1,
    var status: SegmentStatus = SegmentStatus.NEW,
    var date: LocalDate = LocalDate.now().plusDays(1)
) {
    fun build() = Segment(
        quantity = quantity,
        status = status,
        date = date
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


class PurchaseOrderFixtureTests {
    @Test
    fun `should default to quantity 1`() {
        val actual = aPurchaseOrder()

        softly {
            assertThat(actual.quantity).isEqualTo(1)
            assertThat(actual.segments.sumQuantity()).isEqualTo(1)
        }
    }

    @Test
    fun `dsl support`() {
        val po: PurchaseOrder = aPurchaseOrderDsl {

            buyer = "buyer name"

            aSegmentDsl {
                status = SegmentStatus.CANCELLED
                quantity = 2
            }
            aSegmentDsl {
                status = SegmentStatus.CONFIRMED
                quantity = 3
            }
        }

        //TODO: revisit
        softly {
            assertThat(po.buyer).isEqualTo("buyer name")

            assertThat(po.segments).extracting("status", "quantity")
                .containsExactly(
                    tuple(SegmentStatus.CANCELLED, 2),
                    tuple(SegmentStatus.CONFIRMED, 3)
                )
        }
    }
}
