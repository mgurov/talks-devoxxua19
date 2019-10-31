package com.github.mgurov.devoxxua19

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import java.time.LocalDate

fun aPurchaseOrder(adjust: PurchaseOrderBuilder.() -> Unit): PurchaseOrder {
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
            segments += Segment(quantity = 1, status = SegmentStatus.NEW, date = LocalDate.now().plusDays(1))
        }

        return PurchaseOrder(
            product = product,
            quantity = segments.sumQuantity(),
            buyer = buyer,
            segments = segments.toList()
        )
    }

    fun segment(adjust: SegmentBuilder.() -> Unit) {
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

class PurchaseOrderFixtureTests {
    @Test
    fun `should default to quantity 1`() {
        val actual = aPurchaseOrder {}

        softly {
            assertThat(actual.quantity).isEqualTo(1)
            assertThat(actual.segments.sumQuantity()).isEqualTo(1)
        }
    }

    @Test
    fun `should not keep reference to the original list`() {
        val actual = PurchaseOrderBuilder()
        actual.segment {  }
        val firstBuild = actual.build()

        assertThat(firstBuild.segments).hasSize(1)

        actual.segment {  }

        softly {
            assertThat(firstBuild.segments).hasSize(1)
            assertThat(actual.build().segments).hasSize(2)
        }
    }

    @Test
    fun `dsl support`() {
        val po: PurchaseOrder = aPurchaseOrder {

            buyer = "buyer name"

            segment {
                status = SegmentStatus.CANCELLED
                quantity = 2
            }
            segment {
                status = SegmentStatus.CONFIRMED
                quantity = 3
            }
        }

        softly {
            assertThat(po.buyer).isEqualTo("buyer name")

            assertThat(po.segments.map { it.status to it.quantity })
                .containsExactly(
                    SegmentStatus.CANCELLED to 2,
                    SegmentStatus.CONFIRMED to 3
                )
        }
    }
}
