package com.thami.resolve.domain.usecase

import com.thami.resolve.domain.repository.ShoppingRepository
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class CreateShoppingListUseCase @Inject constructor(
    private val repository: ShoppingRepository
) {
    suspend operator fun invoke(name: String): Long {
        val finalName = name.trim().ifBlank { defaultName() }
        return repository.createList(finalName)
    }

    private fun defaultName(): String {
        val formatter = SimpleDateFormat("MMMM/yyyy", Locale("pt", "BR"))
        val capitalized = formatter.format(Date()).replaceFirstChar { it.uppercase() }
        return "Lista de Compras - $capitalized"
    }
}
