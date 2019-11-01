package com.github.mgurov.devoxxua19.domain

import org.junit.jupiter.api.Test

import org.junit.jupiter.api.Assertions.*

class BusinessLogicTest {

    @Test
    fun `should select by buyer`() {
        val given = listOf(
            PurchaseOrder(productCode = "product A", quantity = 1, buyer = "me"),
            PurchaseOrder(productCode = "product B", quantity = 1, buyer = "me"),
            PurchaseOrder(productCode = "product A", quantity = 1, buyer = "someone else")
        )
        //when
        val actual = BusinessLogic.selectByBuyer(given, "me")

        //then
        assertEquals(2, actual.size)

    }
}