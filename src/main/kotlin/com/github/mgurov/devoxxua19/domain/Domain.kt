package com.github.mgurov.devoxxua19.domain

data class PurchaseOrder(
    val productCode: String,
    val quantity: Int,
    val buyer: String
)