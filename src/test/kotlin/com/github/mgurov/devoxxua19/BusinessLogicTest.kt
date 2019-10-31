package com.github.mgurov.devoxxua19

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class BusinessLogicTest {
    @Test
    fun `should select by buyer`() {
        val given = listOf(
            PurchaseOrder("product A", 1, "me"),
            PurchaseOrder("product B", 2, "me"),
            PurchaseOrder("product A", 2, "someone else")
        )

        //when
        val actual = BusinessLogic.selectByBuyer(given, "me")
        //then

        assertEquals(2, actual.size)
    }
}