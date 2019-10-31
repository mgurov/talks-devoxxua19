package com.github.mgurov.devoxxua19

object BusinessLogic {
    fun selectByBuyer(orders: Collection<PurchaseOrder>, buyerName: String): Collection<PurchaseOrder> {
        return orders.filter { it.buyer == buyerName }
    }
}

