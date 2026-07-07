package com.thami.resolve.domain.usecase

import com.thami.resolve.domain.report.ReportGenerator
import com.thami.resolve.domain.repository.ShoppingRepository
import java.io.File
import javax.inject.Inject

class GenerateReportUseCase @Inject constructor(
    private val repository: ShoppingRepository,
    private val reportGenerator: ReportGenerator
) {
    suspend operator fun invoke(listId: Long): File {
        val list = repository.getListWithItems(listId)
        if (list == null || list.items.isEmpty()) throw EmptyShoppingListException()
        return reportGenerator.generate(list)
    }
}
