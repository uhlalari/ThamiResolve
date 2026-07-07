package com.thami.resolve.domain.model

data class ShoppingList(
    val id: Long,
    val name: String,
    val createdAtEpochMillis: Long,
    val isFinalized: Boolean,
    val total: Double,
    val items: List<ShoppingItem> = emptyList()
)
