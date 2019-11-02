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

fun Iterable<Segment>.sumOpenQuantity(): Int {
    return this.sumQuantity { segment -> segment.status.open}
}

fun Iterable<Segment>.sumQuantity(filter: (Segment) -> Boolean = {true}): Int {
    return this.filter(filter).sumBy { it.quantity }
}

enum class SegmentStatus(
    val open: Boolean
) {
    NEW(true), CONFIRMED(true), RECEIVED(false), CANCELLED(false)
}