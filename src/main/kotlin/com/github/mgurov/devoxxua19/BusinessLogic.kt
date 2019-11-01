package com.github.mgurov.devoxxua19

import org.springframework.stereotype.Component

@Component
class BusinessLogic {

    fun selectOpenOrders(product: String): List<PurchaseOrder> {
        TODO()
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
