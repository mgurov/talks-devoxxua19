package com.github.mgurov.devoxxua19

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class BusinessLogicIT {

    @Autowired
    private lateinit var purchaseOrderRepository: PurchaseOrderRepository

    @Autowired
    private lateinit var businessLogic: BusinessLogic

    @Test
    fun `should save and load`() {

        val given = listOf(
            aPurchaseOrder {
                product = "p"
                segment { status = SegmentStatus.NEW; quantity = 1 }
            },
            aPurchaseOrder {
                product = "p"
                segment { status = SegmentStatus.CANCELLED; quantity = 2 }
                segment { status = SegmentStatus.CONFIRMED; quantity = 3 }
            },
            aPurchaseOrder {
                product = "p"
                segment { status = SegmentStatus.DELIVERED; quantity = 4 }
            }
        )

        given.forEach { purchaseOrderRepository.save(it) }

        //when

        val actual = businessLogic.selectOpenOrders("p")

        //then

        assertThat(actual).containsExactlyInAnyOrder(*given.toTypedArray())
    }

}