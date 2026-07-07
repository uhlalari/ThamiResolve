package com.thami.resolve.data.repository

import com.thami.resolve.data.local.dao.ShoppingItemDao
import com.thami.resolve.data.local.dao.ShoppingListDao
import com.thami.resolve.data.local.entity.ShoppingItemEntity
import com.thami.resolve.data.local.entity.ShoppingListEntity
import com.thami.resolve.data.mapper.toDomain
import com.thami.resolve.domain.model.ShoppingItem
import com.thami.resolve.domain.model.ShoppingList
import com.thami.resolve.domain.repository.ShoppingRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class ShoppingRepositoryImpl @Inject constructor(
    private val listDao: ShoppingListDao,
    private val itemDao: ShoppingItemDao
) : ShoppingRepository {

    override fun observeLists(): Flow<List<ShoppingList>> =
        listDao.observeListSummaries().map { summaries -> summaries.map { it.toDomain() } }

    override fun observeListWithItems(listId: Long): Flow<ShoppingList?> =
        combine(
            listDao.observeListSummaries(),
            listDao.observeListById(listId),
            itemDao.observeByListId(listId)
        ) { summaries, listEntity, items ->
            val summary = summaries.firstOrNull { it.list.id == listId }
            val list = listEntity ?: summary?.list ?: return@combine null
            val total = items.sumOf { it.quantity * it.unitPrice }
            list.toDomain(items, total)
        }

    override suspend fun createList(name: String): Long =
        listDao.insert(ShoppingListEntity(name = name, createdAtEpochMillis = System.currentTimeMillis(), isFinalized = false))

    override suspend fun addItem(listId: Long, name: String, quantity: Double, unitPrice: Double) {
        itemDao.insert(ShoppingItemEntity(listId = listId, name = name, quantity = quantity, unitPrice = unitPrice))
    }

    override suspend fun updateItem(item: ShoppingItem) {
        itemDao.update(
            ShoppingItemEntity(id = item.id, listId = item.listId, name = item.name, quantity = item.quantity, unitPrice = item.unitPrice)
        )
    }

    override suspend fun removeItem(itemId: Long) {
        itemDao.deleteById(itemId)
    }

    override suspend fun finalizeList(listId: Long) {
        val list = listDao.getById(listId) ?: return
        listDao.update(list.copy(isFinalized = true))
    }

    override suspend fun getListWithItems(listId: Long): ShoppingList? {
        val list = listDao.getById(listId) ?: return null
        val items = itemDao.getByListId(listId)
        val total = items.sumOf { it.quantity * it.unitPrice }
        return list.toDomain(items, total)
    }

    override suspend fun deleteList(listId: Long) {
        listDao.deleteById(listId)
    }
}
