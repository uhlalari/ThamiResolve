package com.thami.resolve.presentation.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.thami.resolve.domain.repository.ShoppingRepository
import com.thami.resolve.domain.usecase.CreateShoppingListUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class HomeViewModel @Inject constructor(
    private val repository: ShoppingRepository,
    private val createShoppingList: CreateShoppingListUseCase
) : ViewModel() {

    private val _uiState = MutableStateFlow(HomeUiState())
    val uiState: StateFlow<HomeUiState> = _uiState

    init {
        viewModelScope.launch {
            repository.observeLists().collect { lists ->
                _uiState.update { it.copy(lists = lists, isLoading = false) }
            }
        }
    }

    fun createList(name: String, onCreated: (Long) -> Unit) {
        viewModelScope.launch {
            _uiState.update { it.copy(isCreatingList = true) }
            val id = createShoppingList(name)
            _uiState.update { it.copy(isCreatingList = false) }
            onCreated(id)
        }
    }

    fun deleteList(listId: Long) {
        viewModelScope.launch {
            repository.deleteList(listId)
        }
    }
}
