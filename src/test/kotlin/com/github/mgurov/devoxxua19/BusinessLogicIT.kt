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

    @Autowired
    private lateinit var testData: TestData

    @Test
    fun `should save and load`() {

        val productCode = "product ${testData.nextId()}"

        val fullyOpen = aPurchaseOrder {
            product = productCode
            segment { status = SegmentStatus.NEW; quantity = 1 }
        }
        val partiallyOpen = aPurchaseOrder {
            product = productCode
            segment { status = SegmentStatus.CANCELLED; quantity = 2 }
            segment { status = SegmentStatus.CONFIRMED; quantity = 3 }
        }
        val closed = aPurchaseOrder {
            product = productCode
            segment { status = SegmentStatus.DELIVERED; quantity = 4 }
        }

        val given = listOf(
            fullyOpen,
            partiallyOpen,
            closed
        )

        given.forEach { purchaseOrderRepository.save(it) }

        //when

        val actual = businessLogic.selectOpenOrders(productCode)

        //then

        assertThat(actual)
            .containsExactlyInAnyOrder(fullyOpen, partiallyOpen)
            .doesNotContain(closed)

    }

}