package com.thami.resolve.presentation.shoppinglist

import android.content.Context
import android.content.Intent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.PictureAsPdf
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.SnackbarHost
import androidx.compose.material3.SnackbarHostState
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.core.content.FileProvider
import androidx.hilt.navigation.compose.hiltViewModel
import com.thami.resolve.domain.model.ShoppingItem
import com.thami.resolve.presentation.components.AnimatedBackgroundBlobs
import com.thami.resolve.presentation.components.AnimatedTotal
import com.thami.resolve.presentation.components.GlassCard
import com.thami.resolve.presentation.components.ShoppingItemRow
import java.io.File

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ShoppingListScreen(
    listId: Long,
    onBack: () -> Unit,
    viewModel: ShoppingListViewModel = hiltViewModel()
) {
    val uiState by viewModel.uiState.collectAsState()
    val context = LocalContext.current
    val snackbarHostState = remember { SnackbarHostState() }
    var showAddSheet by remember { mutableStateOf(false) }
    var editingItem by remember { mutableStateOf<ShoppingItem?>(null) }

    LaunchedEffect(Unit) {
        viewModel.events.collect { event ->
            when (event) {
                is ShoppingListEvent.ShowError -> snackbarHostState.showSnackbar(event.message)
                is ShoppingListEvent.ReportGenerated -> shareReport(context, event.filePath)
                ShoppingListEvent.ListFinalized -> onBack()
            }
        }
    }

    Scaffold(
        snackbarHost = { SnackbarHost(snackbarHostState) },
        topBar = {
            TopAppBar(
                title = { Text(uiState.list?.name.orEmpty()) },
                navigationIcon = {
                    IconButton(onClick = onBack) {
                        Icon(Icons.Filled.ArrowBack, contentDescription = null)
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = {
                editingItem = null
                showAddSheet = true
            }) {
                Icon(Icons.Filled.Add, contentDescription = null)
            }
        }
    ) { padding ->
        Box(modifier = Modifier.fillMaxSize().padding(padding)) {
            AnimatedBackgroundBlobs(modifier = Modifier.fillMaxSize())

            val list = uiState.list
            Column(modifier = Modifier.fillMaxSize()) {
                GlassCard(modifier = Modifier.fillMaxWidth().padding(20.dp), alpha = 0.25f) {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.SpaceBetween
                    ) {
                        Text("Total da lista", style = MaterialTheme.typography.titleMedium)
                        AnimatedTotal(total = list?.total ?: 0.0)
                    }
                }

                LazyColumn(
                    modifier = Modifier.fillMaxSize().padding(horizontal = 20.dp),
                    contentPadding = PaddingValues(bottom = 100.dp),
                    verticalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    items(list?.items.orEmpty(), key = { it.id }) { item ->
                        ShoppingItemRow(
                            item = item,
                            onClick = {
                                editingItem = item
                                showAddSheet = true
                            },
                            onRemove = { viewModel.removeItem(item.id) }
                        )
                    }
                    item {
                        Row(
                            modifier = Modifier.fillMaxWidth().padding(vertical = 16.dp),
                            horizontalArrangement = Arrangement.spacedBy(12.dp)
                        ) {
                            OutlinedButton(onClick = { viewModel.finalizeList() }) {
                                Text("Finalizar lista")
                            }
                            Button(onClick = { viewModel.generateReport() }) {
                                Icon(Icons.Filled.PictureAsPdf, contentDescription = null)
                                Text("  Gerar relatório")
                            }
                        }
                    }
                }
            }
        }
    }

    if (showAddSheet) {
        AddItemSheet(
            item = editingItem,
            onDismiss = { showAddSheet = false },
            onConfirm = { name, quantity, unitPrice ->
                val currentItem = editingItem
                if (currentItem == null) {
                    viewModel.addItem(name, quantity, unitPrice)
                } else {
                    viewModel.updateItem(currentItem.id, name, quantity, unitPrice)
                }
                showAddSheet = false
            }
        )
    }
}

private fun shareReport(context: Context, filePath: String) {
    val file = File(filePath)
    val uri = FileProvider.getUriForFile(context, "${context.packageName}.fileprovider", file)
    val intent = Intent(Intent.ACTION_SEND).apply {
        type = "application/pdf"
        putExtra(Intent.EXTRA_STREAM, uri)
        addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION)
    }
    context.startActivity(Intent.createChooser(intent, "Compartilhar relatório"))
}
