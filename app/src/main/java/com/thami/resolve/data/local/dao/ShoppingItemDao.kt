package com.thami.resolve.data.local.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.thami.resolve.data.local.entity.ShoppingItemEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ShoppingItemDao {

    @Query("SELECT * FROM shopping_item WHERE listId = :listId ORDER BY id ASC")
    fun observeByListId(listId: Long): Flow<List<ShoppingItemEntity>>

    @Query("SELECT * FROM shopping_item WHERE listId = :listId ORDER BY id ASC")
    suspend fun getByListId(listId: Long): List<ShoppingItemEntity>

    @Insert
    suspend fun insert(item: ShoppingItemEntity): Long

    @Update
    suspend fun update(item: ShoppingItemEntity)

    @Query("DELETE FROM shopping_item WHERE id = :itemId")
    suspend fun deleteById(itemId: Long)
}
