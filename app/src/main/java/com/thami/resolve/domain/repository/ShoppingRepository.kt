package com.thami.resolve.domain.repository

import com.thami.resolve.domain.model.ShoppingItem
import com.thami.resolve.domain.model.ShoppingList
import kotlinx.coroutines.flow.Flow

interface ShoppingRepository {
    fun observeLists(): Flow<List<ShoppingList>>
    fun observeListWithItems(listId: Long): Flow<ShoppingList?>
    suspend fun createList(name: String): Long
    suspend fun addItem(listId: Long, name: String, quantity: Double, unitPrice: Double)
    suspend fun updateItem(item: ShoppingItem)
    suspend fun removeItem(itemId: Long)
    suspend fun finalizeList(listId: Long)
    suspend fun getListWithItems(listId: Long): ShoppingList?
    suspend fun deleteList(listId: Long)
}
