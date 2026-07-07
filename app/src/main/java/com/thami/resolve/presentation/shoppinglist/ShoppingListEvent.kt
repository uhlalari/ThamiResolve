package com.thami.resolve.presentation.shoppinglist

sealed interface ShoppingListEvent {
    data class ShowError(val message: String) : ShoppingListEvent
    data class ReportGenerated(val filePath: String) : ShoppingListEvent
    object ListFinalized : ShoppingListEvent
}
