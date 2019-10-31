package com.github.mgurov.devoxxua19

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test

class BusinessLogicTest {
    @Test
    fun `should select by buyer`() {
        val expected1 = PurchaseOrder("product A", 1, "me")
        val expected2 = PurchaseOrder("product B", 2, "me")
        val unexpected1 = PurchaseOrder("product A", 2, "someone else")

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
    fun `should select by buyer - helper functions`() {
        val expected1 = aPurchaseOrder("me")
        val expected2 = aPurchaseOrder("me")
        val unexpected1 = aPurchaseOrder("someone else")

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

    fun aPurchaseOrder(buyer: String): PurchaseOrder {
        return PurchaseOrder(
            product = "whatever product",
            quantity = 1,
            buyer = buyer
        )
    }
}