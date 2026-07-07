package com.thami.resolve.presentation.home

import com.thami.resolve.domain.model.ShoppingList

data class HomeUiState(
    val lists: List<ShoppingList> = emptyList(),
    val isLoading: Boolean = true,
    val isCreatingList: Boolean = false
)
