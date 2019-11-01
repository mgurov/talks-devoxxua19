package com.github.mgurov.devoxxua19.domain

import java.time.LocalDate

data class PurchaseOrder(
    val productCode: String,
    val quantity: Int,
    val buyer: String,
    val segments: List<Segment>
)

data class Segment(
    val status: SegmentStatus,
    val quantity: Int,
    val expectedDate: LocalDate
)

enum class SegmentStatus {
    NEW, CONFIRMED, RECEIVED, CANCELLED
}