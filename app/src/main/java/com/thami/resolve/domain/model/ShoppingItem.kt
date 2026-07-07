package com.thami.resolve.domain.model

data class ShoppingItem(
    val id: Long,
    val listId: Long,
    val name: String,
    val quantity: Double,
    val unitPrice: Double
) {
    val subtotal: Double
        get() = quantity * unitPrice
}
