package com.example.hereapp.ui.screens

import android.Manifest
import android.content.Intent
import android.provider.ContactsContract
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
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
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import com.example.hereapp.data.Contact
import com.example.hereapp.ui.components.DeleteConfirmationDialog
import com.example.hereapp.ui.components.EditDialog
import com.example.hereapp.ui.components.ItemCard

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ContactsScreen(
    contacts: List<Contact>,
    defaultContactId: Long?,
    onAddContact: (String, String) -> Unit,
    onUpdateContact: (Contact) -> Unit,
    onDeleteContact: (Contact) -> Unit,
    onSetDefault: (Long) -> Unit,
    onNavigateBack: () -> Unit
) {
    var showAddDialog by remember { mutableStateOf(false) }
    var showChoiceDialog by remember { mutableStateOf(false) }
    var editingContact by remember { mutableStateOf<Contact?>(null) }
    var deletingContact by remember { mutableStateOf<Contact?>(null) }

    val context = LocalContext.current

    val contactPickerLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.StartActivityForResult()
    ) { result ->
        val uri = result.data?.data ?: return@rememberLauncherForActivityResult
        val projection = arrayOf(
            ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME,
            ContactsContract.CommonDataKinds.Phone.NUMBER
        )
        context.contentResolver.query(uri, projection, null, null, null)?.use { cursor ->
            if (cursor.moveToFirst()) {
                val name = cursor.getString(
                    cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME)
                )
                val phone = cursor.getString(
                    cursor.getColumnIndexOrThrow(ContactsContract.CommonDataKinds.Phone.NUMBER)
                )
                onAddContact(name, phone)
            }
        }
    }

    val permissionLauncher = rememberLauncherForActivityResult(
        contract = ActivityResultContracts.RequestPermission()
    ) { granted ->
        if (granted) {
            val intent = Intent(Intent.ACTION_PICK, ContactsContract.CommonDataKinds.Phone.CONTENT_URI)
            contactPickerLauncher.launch(intent)
        }
    }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Contacts") },
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
            FloatingActionButton(onClick = { showChoiceDialog = true }) {
                Icon(
                    imageVector = Icons.Default.Add,
                    contentDescription = "Add Contact"
                )
            }
        }
    ) { paddingValues ->
        if (contacts.isEmpty()) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "No contacts yet.\nTap + to add one.",
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
                items(contacts, key = { it.id }) { contact ->
                    ItemCard(
                        title = contact.name,
                        subtitle = contact.phone,
                        isDefault = contact.id == defaultContactId,
                        onSetDefault = { onSetDefault(contact.id) },
                        onEdit = { editingContact = contact },
                        onDelete = { deletingContact = contact }
                    )
                }
            }
        }
    }

    if (showChoiceDialog) {
        AlertDialog(
            onDismissRequest = { showChoiceDialog = false },
            title = { Text("Add Contact") },
            text = { Text("How would you like to add a contact?") },
            confirmButton = {
                TextButton(onClick = {
                    showChoiceDialog = false
                    showAddDialog = true
                }) {
                    Text("Enter Manually")
                }
            },
            dismissButton = {
                TextButton(onClick = {
                    showChoiceDialog = false
                    permissionLauncher.launch(Manifest.permission.READ_CONTACTS)
                }) {
                    Text("Pick from Contacts")
                }
            }
        )
    }

    if (showAddDialog) {
        EditDialog(
            title = "Add Contact",
            fields = listOf("Name" to "", "Phone" to ""),
            onDismiss = { showAddDialog = false },
            onConfirm = { values ->
                onAddContact(values[0], values[1])
                showAddDialog = false
            }
        )
    }

    editingContact?.let { contact ->
        EditDialog(
            title = "Edit Contact",
            fields = listOf("Name" to contact.name, "Phone" to contact.phone),
            onDismiss = { editingContact = null },
            onConfirm = { values ->
                onUpdateContact(contact.copy(name = values[0], phone = values[1]))
                editingContact = null
            }
        )
    }

    deletingContact?.let { contact ->
        DeleteConfirmationDialog(
            itemName = contact.name,
            onDismiss = { deletingContact = null },
            onConfirm = {
                onDeleteContact(contact)
                deletingContact = null
            }
        )
    }
}
