package com.github.mgurov.devoxxua19.domain

import org.springframework.stereotype.Component

@Component
class BusinessLogic(
    private val purchaseOrderRepository: PurchaseOrderRepository
) {

    fun selectOpenProductOrders(productCode: String): List<PurchaseOrder> {
        return purchaseOrderRepository.findByProductCode(productCode)
            .filter { order -> order.segments.sumOpenQuantity() > 0 }
    }

    companion object {
        fun selectByBuyer(orders: List<PurchaseOrder>, buyer: String): List<PurchaseOrder> {
            return orders.filter { it.buyer == buyer }
        }

        fun countOpenQuantity(orders: List<PurchaseOrder>): Int {
            return orders.sumBy { order ->
                order.segments.sumOpenQuantity()
            }
        }
    }
}