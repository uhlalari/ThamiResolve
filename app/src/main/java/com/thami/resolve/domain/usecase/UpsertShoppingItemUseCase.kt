package com.thami.resolve.domain.usecase

import com.thami.resolve.domain.model.ShoppingItem
import com.thami.resolve.domain.repository.ShoppingRepository
import javax.inject.Inject

class InvalidShoppingItemException(message: String) : Exception(message)

class UpsertShoppingItemUseCase @Inject constructor(
    private val repository: ShoppingRepository
) {
    suspend operator fun invoke(
        itemId: Long,
        listId: Long,
        name: String,
        quantity: Double,
        unitPrice: Double
    ) {
        val trimmedName = name.trim()
        if (trimmedName.isEmpty()) throw InvalidShoppingItemException("Informe o nome do item")
        if (quantity <= 0.0) throw InvalidShoppingItemException("Quantidade deve ser maior que zero")
        if (unitPrice < 0.0) throw InvalidShoppingItemException("Valor não pode ser negativo")

        if (itemId == 0L) {
            repository.addItem(listId, trimmedName, quantity, unitPrice)
        } else {
            repository.updateItem(
                ShoppingItem(
                    id = itemId,
                    listId = listId,
                    name = trimmedName,
                    quantity = quantity,
                    unitPrice = unitPrice
                )
            )
        }
    }
}
