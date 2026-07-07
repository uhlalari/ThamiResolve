package com.thami.resolve.presentation.shoppinglist

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thami.resolve.domain.repository.ShoppingRepository
import com.thami.resolve.domain.usecase.EmptyShoppingListException
import com.thami.resolve.domain.usecase.FinalizeShoppingListUseCase
import com.thami.resolve.domain.usecase.GenerateReportUseCase
import com.thami.resolve.domain.usecase.InvalidShoppingItemException
import com.thami.resolve.domain.usecase.UpsertShoppingItemUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class ShoppingListViewModel @Inject constructor(
    savedStateHandle: SavedStateHandle,
    private val repository: ShoppingRepository,
    private val upsertShoppingItem: UpsertShoppingItemUseCase,
    private val finalizeShoppingList: FinalizeShoppingListUseCase,
    private val generateReport: GenerateReportUseCase
) : ViewModel() {

    private val listId: Long = savedStateHandle.get<Long>("listId") ?: 0L

    private val _uiState = MutableStateFlow(ShoppingListUiState())
    val uiState: StateFlow<ShoppingListUiState> = _uiState

    private val _events = Channel<ShoppingListEvent>(Channel.BUFFERED)
    val events = _events.receiveAsFlow()

    init {
        viewModelScope.launch {
            repository.observeListWithItems(listId).collect { list ->
                _uiState.update { it.copy(list = list, isLoading = false) }
            }
        }
    }

    fun addItem(name: String, quantity: Double, unitPrice: Double) = upsert(0L, name, quantity, unitPrice)

    fun updateItem(itemId: Long, name: String, quantity: Double, unitPrice: Double) =
        upsert(itemId, name, quantity, unitPrice)

    private fun upsert(itemId: Long, name: String, quantity: Double, unitPrice: Double) {
        viewModelScope.launch {
            try {
                upsertShoppingItem(itemId, listId, name, quantity, unitPrice)
            } catch (exception: InvalidShoppingItemException) {
                _events.send(ShoppingListEvent.ShowError(exception.message.orEmpty()))
            }
        }
    }

    fun removeItem(itemId: Long) {
        viewModelScope.launch { repository.removeItem(itemId) }
    }

    fun finalizeList() {
        viewModelScope.launch {
            try {
                finalizeShoppingList(listId)
                _events.send(ShoppingListEvent.ListFinalized)
            } catch (exception: EmptyShoppingListException) {
                _events.send(ShoppingListEvent.ShowError(exception.message.orEmpty()))
            }
        }
    }

    fun generateReport() {
        viewModelScope.launch {
            _uiState.update { it.copy(isGeneratingReport = true) }
            try {
                val file = generateReport(listId)
                _events.send(ShoppingListEvent.ReportGenerated(file.absolutePath))
            } catch (exception: EmptyShoppingListException) {
                _events.send(ShoppingListEvent.ShowError(exception.message.orEmpty()))
            } finally {
                _uiState.update { it.copy(isGeneratingReport = false) }
            }
        }
    }
}
