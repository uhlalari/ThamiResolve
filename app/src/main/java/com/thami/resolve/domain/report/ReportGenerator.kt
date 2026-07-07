package com.thami.resolve.domain.report

import com.thami.resolve.domain.model.ShoppingList
import java.io.File

interface ReportGenerator {
    suspend fun generate(list: ShoppingList): File
}
