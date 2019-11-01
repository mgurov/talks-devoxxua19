package com.github.mgurov.devoxxua19.domain

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class BusinessLogicTest {

    @Test
    fun `should select by buyer`() {
        val expected1 = aPurchaseOrder(buyer = "me")
        val expected2 = aPurchaseOrder(buyer = "me")
        val notExpected = aPurchaseOrder(buyer = "someone else")

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

fun aPurchaseOrder(
    productCode: String = "whatever product",
    quantity: Int = 1,
    buyer: String = "anonymous"
): PurchaseOrder {
    return PurchaseOrder(
        productCode = productCode,
        quantity = quantity,
        buyer = buyer,
        segments = emptyList()
    )
}