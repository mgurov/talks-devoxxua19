package com.github.mgurov.devoxxua19

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

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
            aPurchaseOrder(
                segments = listOf(
                    aSegment(status = SegmentStatus.NEW, quantity = 1)
                )
            ),
            aPurchaseOrder(
                segments = listOf(
                    aSegment(status = SegmentStatus.CANCELLED, quantity = 2),
                    aSegment(status = SegmentStatus.CONFIRMED, quantity = 3)
                )
            ),
            aPurchaseOrder(
                segments = listOf(
                    aSegment(status = SegmentStatus.DELIVERED, quantity = 4)
                )
            )
        )

        //when
        val actual: Int = BusinessLogic.countOpenQuantity(given)

        //then
        assertThat(actual).`as`("1 NEW + 3 CONFIRMED").isEqualTo(4)
    }

}