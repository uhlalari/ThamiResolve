package com.thami.resolve.data.local

import androidx.room.Database
import androidx.room.RoomDatabase
import com.thami.resolve.data.local.dao.ShoppingItemDao
import com.thami.resolve.data.local.dao.ShoppingListDao
import com.thami.resolve.data.local.entity.ShoppingItemEntity
import com.thami.resolve.data.local.entity.ShoppingListEntity

@Database(
    entities = [ShoppingListEntity::class, ShoppingItemEntity::class],
    version = 1,
    exportSchema = false
)
abstract class ShoppingDatabase : RoomDatabase() {
    abstract fun shoppingListDao(): ShoppingListDao
    abstract fun shoppingItemDao(): ShoppingItemDao
}
