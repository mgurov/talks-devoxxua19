package com.github.mgurov.devoxxua19.domain

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class BusinessLogicTest {

    @Test
    fun `should select by buyer`() {
        val expected1 = PurchaseOrder(productCode = "product A", quantity = 1, buyer = "me")
        val expected2 = PurchaseOrder(productCode = "product B", quantity = 1, buyer = "me")
        val notExpected = PurchaseOrder(productCode = "product A", quantity = 1, buyer = "someone else")

        //when
        val actual = BusinessLogic.selectByBuyer(
            listOf(
                expected1,
                expected2,
                notExpected
            ), "me")

        //then
        assertThat(actual).containsExactlyInAnyOrder(expected1, expected2)
    }
}