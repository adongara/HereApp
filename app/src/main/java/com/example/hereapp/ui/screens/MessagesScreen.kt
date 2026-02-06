package com.example.hereapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.example.hereapp.data.Message
import com.example.hereapp.ui.components.DeleteConfirmationDialog
import com.example.hereapp.ui.components.EditDialog
import com.example.hereapp.ui.components.ItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MessagesScreen(
    messages: List<Message>,
    defaultMessageId: Long?,
    onAddMessage: (String) -> Unit,
    onUpdateMessage: (Message) -> Unit,
    onDeleteMessage: (Message) -> Unit,
    onSetDefault: (Long) -> Unit,
    onNavigateBack: () -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var editingMessage by remember { mutableStateOf<Message?>(null) }
    var deletingMessage by remember { mutableStateOf<Message?>(null) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Messages") },
                navigationIcon = {
                    IconButton(onClick = onNavigateBack) {
                        Icon(
                            imageVector = Icons.Default.ArrowBack,
                            contentDescription = "Back"
                        )
                    }
                }
            )
        },
        floatingActionButton = {
            FloatingActionButton(onClick = { showAddDialog = true }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Message"
                )
            }
        }
    ) { paddingValues ->
        if (messages.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No messages yet.\nTap + to add one.",
                    style = MaterialTheme.typography.bodyLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant
                )
            }
        } else {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentPadding = PaddingValues(vertical = 8.dp)
            ) {
                items(messages, key = { it.id }) { message ->
                    ItemCard(
                        title = message.text,
                        isDefault = message.id == defaultMessageId,
                        onSetDefault = { onSetDefault(message.id) },
                        onEdit = { editingMessage = message },
                        onDelete = { deletingMessage = message }
                    )
                }
            }
        }
    }

    if (showAddDialog) {
        EditDialog(
            title = "Add Message",
            fields = listOf("Message" to ""),
            onDismiss = { showAddDialog = false },
            onConfirm = { values ->
                onAddMessage(values[0])
                showAddDialog = false
            }
        )
    }

    editingMessage?.let { message ->
        EditDialog(
            title = "Edit Message",
            fields = listOf("Message" to message.text),
            onDismiss = { editingMessage = null },
            onConfirm = { values ->
                onUpdateMessage(message.copy(text = values[0]))
                editingMessage = null
            }
        )
    }

    deletingMessage?.let { message ->
        DeleteConfirmationDialog(
            itemName = message.text,
            onDismiss = { deletingMessage = null },
            onConfirm = {
                onDeleteMessage(message)
                deletingMessage = null
            }
        )
    }
}
