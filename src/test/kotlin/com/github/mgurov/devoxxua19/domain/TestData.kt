package com.github.mgurov.devoxxua19.domain

import java.time.LocalDate

fun aPurchaseOrder(
    productCode: String = "whatever product",
    quantity: Int = 1,
    buyer: String = "anonymous"
): PurchaseOrder {
    return PurchaseOrder(
        productCode = productCode,
        quantity = quantity,
        buyer = buyer,
        segments = listOf(aSegment(quantity = quantity))
    )
}

fun aSegment(
    status: SegmentStatus = SegmentStatus.NEW,
    quantity: Int = 1,
    expectedDate: LocalDate = LocalDate.now().plusDays(1)
) = Segment(
    status = status,
    quantity = quantity,
    expectedDate = expectedDate
)
