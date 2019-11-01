package com.github.mgurov.devoxxua19.domain

import java.time.LocalDate

data class PurchaseOrder(
    val productCode: String,
    val quantity: Int,
    val buyer: String,
    val segments: List<Segment>
) {
    init {
        val segmentsQuantity = segments.sumQuantity()
        check(quantity == segmentsQuantity)
            {"Expect po quantity ($quantity) to equal ∑ segment quantities ($segmentsQuantity)"}
    }
}

data class Segment(
    val status: SegmentStatus,
    val quantity: Int,
    val expectedDate: LocalDate
)

fun Iterable<Segment>.sumQuantity(): Int {
    return this.sumBy { it.quantity }
}

enum class SegmentStatus {
    NEW, CONFIRMED, RECEIVED, CANCELLED
}