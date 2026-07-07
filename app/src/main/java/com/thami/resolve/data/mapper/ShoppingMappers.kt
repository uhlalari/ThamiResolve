package com.thami.resolve.data.mapper

import com.thami.resolve.data.local.dao.ShoppingListSummary
import com.thami.resolve.data.local.entity.ShoppingItemEntity
import com.thami.resolve.data.local.entity.ShoppingListEntity
import com.thami.resolve.domain.model.ShoppingItem
import com.thami.resolve.domain.model.ShoppingList

fun ShoppingItemEntity.toDomain(): ShoppingItem = ShoppingItem(
    id = id,
    listId = listId,
    name = name,
    quantity = quantity,
    unitPrice = unitPrice
)

fun ShoppingListSummary.toDomain(): ShoppingList = ShoppingList(
    id = list.id,
    name = list.name,
    createdAtEpochMillis = list.createdAtEpochMillis,
    isFinalized = list.isFinalized,
    total = total
)

fun ShoppingListEntity.toDomain(items: List<ShoppingItemEntity>, total: Double): ShoppingList = ShoppingList(
    id = id,
    name = name,
    createdAtEpochMillis = createdAtEpochMillis,
    isFinalized = isFinalized,
    total = total,
    items = items.map { it.toDomain() }
)
