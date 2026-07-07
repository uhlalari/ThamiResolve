package com.thami.resolve.presentation.shoppinglist

import com.thami.resolve.domain.model.ShoppingList

data class ShoppingListUiState(
    val list: ShoppingList? = null,
    val isLoading: Boolean = true,
    val isGeneratingReport: Boolean = false
)
