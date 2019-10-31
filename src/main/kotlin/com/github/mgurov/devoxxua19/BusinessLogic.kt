package com.github.mgurov.devoxxua19

object BusinessLogic {
    fun selectByBuyer(orders: List<PurchaseOrder>, buyerName: String): List<PurchaseOrder> {
        return orders.filter { it.buyer == buyerName }
    }
}

