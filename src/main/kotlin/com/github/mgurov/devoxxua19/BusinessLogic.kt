package com.github.mgurov.devoxxua19

object BusinessLogic {
    fun selectByBuyer(orders: Collection<PurchaseOrder>, buyerName: String): Collection<PurchaseOrder> {
        return orders.filter { it.buyer == buyerName }
    }

    fun countOpenQuantity(given: List<PurchaseOrder>): Int {
        return given.sumBy { it.segments.totalQuantity() }
    }
}

