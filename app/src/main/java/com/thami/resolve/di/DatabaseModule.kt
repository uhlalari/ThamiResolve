package com.thami.resolve.di

import android.content.Context
import androidx.room.Room
import com.thami.resolve.data.local.ShoppingDatabase
import com.thami.resolve.data.local.dao.ShoppingItemDao
import com.thami.resolve.data.local.dao.ShoppingListDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    @Provides
    @Singleton
    fun provideDatabase(@ApplicationContext context: Context): ShoppingDatabase =
        Room.databaseBuilder(context, ShoppingDatabase::class.java, "thami_resolve.db").build()

    @Provides
    fun provideShoppingListDao(database: ShoppingDatabase): ShoppingListDao = database.shoppingListDao()

    @Provides
    fun provideShoppingItemDao(database: ShoppingDatabase): ShoppingItemDao = database.shoppingItemDao()
}
