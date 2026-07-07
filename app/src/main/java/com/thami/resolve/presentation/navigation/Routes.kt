package com.thami.resolve.presentation.navigation

import kotlinx.serialization.Serializable

@Serializable
object Home

@Serializable
data class ShoppingListDetail(val listId: Long)
