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
            ), "me")

        //then
        assertThat(actual).containsExactlyInAnyOrder(
            expected1,
            expected2
        )
    }

    @Test
    fun `should select open orders by buyer`() {
        val expected1 = aPurchaseOrder(buyer = "me")
        val expected2 = aPurchaseOrder(buyer = "me")
        val unexpected1 = aPurchaseOrder(buyer = "someone else")

        //when
        val actual = BusinessLogic.selectByBuyer(
            listOf(
                expected1,
                expected2,
                unexpected1
            ), "me")

        //then
        assertThat(actual).containsExactlyInAnyOrder(
            expected1,
            expected2
        )
    }

}

class PurchaseOrderFixtureTests {
    @Test
    fun `should default to quantity 1`() {
        val actual = aPurchaseOrder()

        softly {
            assertThat(actual.quantity).isEqualTo(1)
            assertThat(actual.segments.totalQuantity()).isEqualTo(1)
        }
    }
}

fun softly(assertionBlock: SoftAssertions.()->Unit) {
    assertSoftly {
        it.assertionBlock()
    }
}

private fun aPurchaseOrder(
    product: String = "whatever product",
    quantity: Int? = null,
    buyer: String = "me, handsome",
    vararg segments: Segment = arrayOf(defaultSegment.copy(quantity = quantity ?: 1))
): PurchaseOrder {
    return PurchaseOrder(
        product = product,
        quantity = quantity ?: segments.toList().totalQuantity(),
        buyer = buyer,
        segments = segments.toList()
    )
}

val defaultSegment = Segment(quantity = 1, status = SegmentStatus.NEW, date = LocalDate.now().plusDays(1))