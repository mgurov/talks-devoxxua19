package com.github.mgurov.devoxxua19.domain

object BusinessLogic {
    fun selectByBuyer(orders: List<PurchaseOrder>, buyer: String): List<PurchaseOrder> {
        return orders.filter { it.buyer == buyer }
    }

    fun countOpenQuantity(orders: List<PurchaseOrder>): Int {
        return orders.sumBy { order ->
            order.segments.sumQuantity { segment -> segment.status.open}
        }
    }
}