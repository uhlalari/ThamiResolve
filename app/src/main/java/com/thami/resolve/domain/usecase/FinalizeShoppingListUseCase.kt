package com.thami.resolve.domain.usecase

import com.thami.resolve.domain.repository.ShoppingRepository
import javax.inject.Inject

class EmptyShoppingListException : Exception("Adicione ao menos um item antes de finalizar a lista")

class FinalizeShoppingListUseCase @Inject constructor(
    private val repository: ShoppingRepository
) {
    suspend operator fun invoke(listId: Long) {
        val list = repository.getListWithItems(listId)
        if (list == null || list.items.isEmpty()) throw EmptyShoppingListException()
        repository.finalizeList(listId)
    }
}
