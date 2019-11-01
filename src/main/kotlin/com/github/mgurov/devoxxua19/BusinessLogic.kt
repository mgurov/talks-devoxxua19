package com.github.mgurov.devoxxua19

import org.springframework.stereotype.Component

@Component
class BusinessLogic(
    private val purchaseOrderRepository: PurchaseOrderRepository
) {

    fun selectOpenOrders(product: String): List<PurchaseOrder> {
        val productOrders = purchaseOrderRepository.findByProduct(product)
        return productOrders.filter {order ->
            order.segments.any { segment -> segment.isOpen() }
        }
    }

    companion object {
        fun selectByBuyer(orders: Collection<PurchaseOrder>, buyerName: String): Collection<PurchaseOrder> {
            return orders.filter { it.buyer == buyerName }
        }

        fun countOpenQuantity(given: List<PurchaseOrder>): Int {
            return given.sumBy { it.segments.sumQuantity(filter = {segment -> segment.isOpen()}) }
        }
    }
}
