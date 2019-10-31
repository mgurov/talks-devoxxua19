package com.github.mgurov.devoxxua19

import org.springframework.stereotype.Component

@Component
class PurchaseOrderRepository {
    fun save(po: PurchaseOrder): Long {
        TODO()
    }

    fun load(id: Long): PurchaseOrder? {
        TODO()
    }
}