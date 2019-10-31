package com.github.mgurov.devoxxua19

data class PurchaseOrder(
    val product: String,
    val quantity: Int,
    val buyer: String
)