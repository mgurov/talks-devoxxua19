package com.github.mgurov.devoxxua19

import java.time.LocalDate

data class PurchaseOrder(
    val product: String,
    val quantity: Int,
    val buyer: String,
    val segments: List<Segment>
)

data class Segment(
    val quantity: Int,
    val status: SegmentStatus,
    val date: LocalDate
)

enum class SegmentStatus {
    NEW, CONFIRMED, DELIVERED, CANCELLED
}
