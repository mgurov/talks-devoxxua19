package com.github.mgurov.devoxxua19

import org.assertj.core.api.Assertions.assertThat
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest

@SpringBootTest
class PurchaseOrderRepositoryIT {

    @Autowired
    private lateinit var purchaseOrderRepository: PurchaseOrderRepository

    @Test
    fun `should save and load`() {
        assertThat(purchaseOrderRepository).isNotNull()
    }

}