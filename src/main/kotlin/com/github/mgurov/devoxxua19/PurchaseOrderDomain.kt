package com.github.mgurov.devoxxua19

import java.time.LocalDate

data class PurchaseOrder(
    val product: String,
    val quantity: Int,
    val buyer: String,
    val segments: List<Segment>
) {
    init {
        val segmentQuantity = segments.sumQuantity()
        check(segmentQuantity == quantity)
            {"Purchase order quantity ($quantity) != ∑ segment quantity ($segmentQuantity)"}
    }
}

data class Segment(
    val quantity: Int,
    val status: SegmentStatus,
    val date: LocalDate
) {
    fun isOpen() = status.open
}

fun Iterable<Segment>.sumQuantity(filter: (Segment) -> Boolean = {true}) =
    this.filter(filter).sumBy {  it.quantity }

enum class SegmentStatus(val open: Boolean) {
    NEW(open = true),
    CONFIRMED(open = true),
    DELIVERED(open = false),
    CANCELLED(open = false)
}
