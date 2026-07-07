package com.thami.resolve.data.local.dao

import androidx.room.Dao
import androidx.room.Embedded
import androidx.room.Insert
import androidx.room.Query
import androidx.room.Update
import com.thami.resolve.data.local.entity.ShoppingListEntity
import kotlinx.coroutines.flow.Flow

data class ShoppingListSummary(
    @Embedded val list: ShoppingListEntity,
    val total: Double
)

@Dao
interface ShoppingListDao {

    @Query("""
        SELECT l.*, COALESCE(SUM(i.quantity * i.unitPrice), 0.0) AS total
        FROM shopping_list l
        LEFT JOIN shopping_item i ON i.listId = l.id
        GROUP BY l.id
        ORDER BY l.createdAtEpochMillis DESC
    """)
    fun observeListSummaries(): Flow<List<ShoppingListSummary>>

    @Query("SELECT * FROM shopping_list WHERE id = :listId")
    fun observeListById(listId: Long): Flow<ShoppingListEntity?>

    @Query("SELECT * FROM shopping_list WHERE id = :listId LIMIT 1")
    suspend fun getById(listId: Long): ShoppingListEntity?

    @Insert
    suspend fun insert(list: ShoppingListEntity): Long

    @Update
    suspend fun update(list: ShoppingListEntity)

    @Query("DELETE FROM shopping_list WHERE id = :listId")
    suspend fun deleteById(listId: Long)
}
