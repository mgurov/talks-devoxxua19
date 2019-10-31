package com.github.mgurov.devoxxua19

import org.assertj.core.api.Assertions.assertThat
import org.assertj.core.api.SoftAssertions
import org.assertj.core.api.SoftAssertions.assertSoftly
import org.junit.jupiter.api.Test
import java.time.LocalDate

class BusinessLogicTest {

    @Test
    fun `should select by buyer - helper functions`() {
        val expected1 = aPurchaseOrder(buyer = "me")
        val expected2 = aPurchaseOrder(buyer = "me")
        val unexpected1 = aPurchaseOrder(buyer = "someone else")

        //when
        val actual = BusinessLogic.selectByBuyer(
            listOf(
                expected1,
                expected2,
                unexpected1
            ), "me"
        )

        //then
        assertThat(actual).containsExactlyInAnyOrder(
            expected1,
            expected2
        )
    }

    @Test
    fun `should count open quantity`() {
        val given = listOf(
            aPurchaseOrder(segments = listOf(
                aSegment(status = SegmentStatus.NEW, quantity = 1))),
            aPurchaseOrder(segments = listOf(
                aSegment(status = SegmentStatus.CANCELLED, quantity = 2),
                aSegment(status = SegmentStatus.CONFIRMED, quantity = 3))),
            aPurchaseOrder(segments = listOf(
                aSegment(status = SegmentStatus.DELIVERED, quantity = 4)))
        )

        //when
        val actual: Int = BusinessLogic.countOpenQuantity(given)

        //then
        assertThat(actual).`as`("1 NEW + 3 CONFIRMED").isEqualTo(4)
    }

}

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

fun softly(assertionBlock: SoftAssertions.() -> Unit) {
    assertSoftly {
        it.assertionBlock()
    }
}

private fun aPurchaseOrder(
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

private fun aSegment(
    quantity: Int = 1,
    status: SegmentStatus = SegmentStatus.NEW,
    date: LocalDate = LocalDate.now().plusDays(1)
) = Segment(
    quantity = quantity,
    status = status,
    date = date
)

val defaultSegment = Segment(quantity = 1, status = SegmentStatus.NEW, date = LocalDate.now().plusDays(1))