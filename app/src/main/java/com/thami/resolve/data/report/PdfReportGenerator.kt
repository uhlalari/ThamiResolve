package com.thami.resolve.data.report

import android.content.Context
import android.graphics.Paint
import android.graphics.pdf.PdfDocument
import com.thami.resolve.domain.model.ShoppingList
import com.thami.resolve.domain.report.ReportGenerator
import dagger.hilt.android.qualifiers.ApplicationContext
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import java.io.File
import java.io.FileOutputStream
import java.text.NumberFormat
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class PdfReportGenerator @Inject constructor(
    @ApplicationContext private val context: Context
) : ReportGenerator {

    private val pageWidth = 595
    private val pageHeight = 842
    private val marginLeft = 40f
    private val currencyFormat = NumberFormat.getCurrencyInstance(Locale("pt", "BR"))
    private val dateFormat = SimpleDateFormat("dd/MM/yyyy HH:mm", Locale("pt", "BR"))

    override suspend fun generate(list: ShoppingList): File = withContext(Dispatchers.IO) {
        val document = PdfDocument()
        val pageInfo = PdfDocument.PageInfo.Builder(pageWidth, pageHeight, 1).create()
        var page = document.startPage(pageInfo)
        var canvas = page.canvas

        val titlePaint = Paint().apply { textSize = 22f; isFakeBoldText = true }
        val subtitlePaint = Paint().apply { textSize = 16f; isFakeBoldText = true }
        val metaPaint = Paint().apply { textSize = 12f; color = 0xFF666666.toInt() }
        val headerPaint = Paint().apply { textSize = 13f; isFakeBoldText = true }
        val bodyPaint = Paint().apply { textSize = 12f }
        val totalPaint = Paint().apply { textSize = 16f; isFakeBoldText = true }

        var y = 50f
        canvas.drawText("Thami Resolve", marginLeft, y, titlePaint)
        y += 25f
        canvas.drawText(list.name, marginLeft, y, subtitlePaint)
        y += 16f
        canvas.drawText("Gerado em ${dateFormat.format(Date())}", marginLeft, y, metaPaint)
        y += 30f

        canvas.drawText("Item", marginLeft, y, headerPaint)
        canvas.drawText("Qtd", 300f, y, headerPaint)
        canvas.drawText("Valor Unit.", 370f, y, headerPaint)
        canvas.drawText("Subtotal", 480f, y, headerPaint)
        y += 20f

        list.items.forEach { item ->
            if (y > pageHeight - 80f) {
                document.finishPage(page)
                page = document.startPage(pageInfo)
                canvas = page.canvas
                y = 50f
            }
            canvas.drawText(item.name, marginLeft, y, bodyPaint)
            canvas.drawText(item.quantity.toString(), 300f, y, bodyPaint)
            canvas.drawText(currencyFormat.format(item.unitPrice), 370f, y, bodyPaint)
            canvas.drawText(currencyFormat.format(item.quantity * item.unitPrice), 480f, y, bodyPaint)
            y += 20f
        }

        y += 20f
        canvas.drawText("Total geral: ${currencyFormat.format(list.total)}", marginLeft, y, totalPaint)
        document.finishPage(page)

        val reportsDir = File(context.cacheDir, "reports").apply { mkdirs() }
        val sanitizedFileName = list.name.replace(Regex("[^a-zA-Z0-9\\s]"), "").trim().replace(" ", "_")
        val file = File(reportsDir, "${sanitizedFileName}_${System.currentTimeMillis()}.pdf")
        FileOutputStream(file).use { output -> document.writeTo(output) }
        document.close()
        file
    }
}