package com.github.mgurov.devoxxua19.domain

import org.assertj.core.api.Assertions.*
import org.junit.jupiter.api.Test

class BusinessLogicTest {

    @Test
    fun `should select by buyer`() {
        val expected1 = aPurchaseOrder{buyer = "me"}
        val expected2 = aPurchaseOrder{buyer = "me"}
        val notExpected = aPurchaseOrder{buyer = "someone else"}

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

    @Test
    fun `should count open quantity`() {
        val given = listOf(
            aPurchaseOrder {
                segment {
                    status = SegmentStatus.NEW; quantity = 1
                }
            },

            aPurchaseOrder{
                segment {
                    status = SegmentStatus.CONFIRMED; quantity = 2
                }
                segment {
                    status = SegmentStatus.RECEIVED; quantity = 3
                }

            },

            aPurchaseOrder{
                segment {
                    status = SegmentStatus.CANCELLED; quantity = 4
                }
            }
        )

        //when
        val actual = BusinessLogic.countOpenQuantity(given)
        //then
        assertThat(actual).`as`("1 + 2").isEqualTo(3)
    }
}