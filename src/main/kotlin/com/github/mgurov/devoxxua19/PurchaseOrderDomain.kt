package com.github.mgurov.devoxxua19

import java.time.LocalDate

data class PurchaseOrder(
    val product: String,
    val quantity: Int,
    val buyer: String,
    val segments: List<Segment>
) {
    init {
        val segmentQuantity = segments.totalQuantity()
        check(segmentQuantity == quantity)
            {"Purchase order quantity ($quantity) != ∑ segment quantity ($segmentQuantity)"}
    }
}

data class Segment(
    val quantity: Int,
    val status: SegmentStatus,
    val date: LocalDate
)

fun Iterable<Segment>.totalQuantity() = this.sumBy { it.quantity }

enum class SegmentStatus {
    NEW, CONFIRMED, DELIVERED, CANCELLED
}
