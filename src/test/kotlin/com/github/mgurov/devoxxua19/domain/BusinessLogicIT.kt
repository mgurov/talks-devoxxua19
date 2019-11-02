package com.github.mgurov.devoxxua19.domain

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BusinessLogicIT {

    @Autowired
    private lateinit var businessLogic: BusinessLogic
    @Autowired
    private lateinit var purchaseOrderRepository: PurchaseOrderRepository

    @Test
    fun `should select open orders for a given product code`() {
        //given
        val expected1 = aPurchaseOrder {
            productCode = "product"
            segment {
                status = SegmentStatus.NEW; quantity = 1
            }
        }

        val expected2 = aPurchaseOrder {
            productCode = "product"
            segment {
                status = SegmentStatus.CONFIRMED; quantity = 2
            }
            segment {
                status = SegmentStatus.RECEIVED; quantity = 3
            }

        }
        val unexpected = aPurchaseOrder {
            productCode = "product"
            segment {
                status = SegmentStatus.CANCELLED; quantity = 4
            }
        }

        listOf(
            expected1,
            expected2,
            unexpected
        ).forEach { purchaseOrderRepository.save(it) }

        //when
        val actual = businessLogic.selectOpenProductOrders("product")
        //then
        assertThat(actual).containsExactlyInAnyOrder(expected1, expected2)
    }
}