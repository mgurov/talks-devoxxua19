package com.github.mgurov.devoxxua19

import org.assertj.core.api.Assertions.assertThat
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

}

private fun aPurchaseOrder(
    product: String = "whatever product",
    quantity: Int = 1,
    buyer: String = "me, handsome"
): PurchaseOrder {
    return PurchaseOrder(
        product = product,
        quantity = quantity,
        buyer = buyer,
        segments = listOf(Segment(quantity = 1, status = SegmentStatus.NEW, date = LocalDate.now().plusDays(1)))
    )
}