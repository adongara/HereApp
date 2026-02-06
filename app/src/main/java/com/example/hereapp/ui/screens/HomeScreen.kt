package com.example.hereapp.ui.screens

import android.app.Activity
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Message
import androidx.compose.material.icons.filled.People
import androidx.compose.material.icons.filled.Send
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.example.hereapp.SmsHelper
import com.example.hereapp.data.Contact
import com.example.hereapp.data.Message

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    defaultMessage: Message?,
    defaultContact: Contact?,
    onNavigateToMessages: () -> Unit,
    onNavigateToContacts: () -> Unit
) {
    val context = LocalContext.current
    var showPermissionDialog by remember { mutableStateOf(false) }

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("HereApp") },
                actions = {
                    IconButton(onClick = onNavigateToMessages) {
                        Icon(
                            imageVector = Icons.Default.Message,
                            contentDescription = "Messages"
                        )
                    }
                    IconButton(onClick = onNavigateToContacts) {
                        Icon(
                            imageVector = Icons.Default.People,
                            contentDescription = "Contacts"
                        )
                    }
                }
            )
        }
    ) { paddingValues ->
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            // Default Message Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Message",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    Text(
                        text = defaultMessage?.text ?: "No default message set",
                        style = MaterialTheme.typography.bodyLarge,
                        color = if (defaultMessage != null)
                            MaterialTheme.colorScheme.onSurface
                        else
                            MaterialTheme.colorScheme.onSurfaceVariant
                    )
                }
            }

            // Default Contact Card
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(vertical = 8.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Recipient",
                        style = MaterialTheme.typography.labelMedium,
                        color = MaterialTheme.colorScheme.primary
                    )
                    Spacer(modifier = Modifier.height(4.dp))
                    if (defaultContact != null) {
                        Text(
                            text = defaultContact.name,
                            style = MaterialTheme.typography.bodyLarge
                        )
                        Text(
                            text = defaultContact.phone,
                            style = MaterialTheme.typography.bodyMedium,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    } else {
                        Text(
                            text = "No default contact set",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurfaceVariant
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(32.dp))

            // Send Button
            Button(
                onClick = {
                    if (defaultMessage != null && defaultContact != null) {
                        if (SmsHelper.hasSmsPermission(context)) {
                            SmsHelper.sendSms(context, defaultContact.phone, defaultMessage.text)
                        } else {
                            showPermissionDialog = true
                        }
                    }
                },
                modifier = Modifier
                    .size(150.dp),
                enabled = defaultMessage != null && defaultContact != null,
                shape = MaterialTheme.shapes.extraLarge
            ) {
                Column(
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Send,
                        contentDescription = null,
                        modifier = Modifier.size(48.dp)
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "SEND",
                        style = MaterialTheme.typography.titleMedium
                    )
                }
            }

            if (defaultMessage == null || defaultContact == null) {
                Spacer(modifier = Modifier.height(16.dp))
                Text(
                    text = "Set a default message and contact to enable sending",
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    textAlign = TextAlign.Center
                )
            }
        }
    }

    if (showPermissionDialog) {
        AlertDialog(
            onDismissRequest = { showPermissionDialog = false },
            title = { Text("SMS Permission Required") },
            text = { Text("This app needs permission to send SMS messages. Please grant the permission to continue.") },
            confirmButton = {
                TextButton(
                    onClick = {
                        showPermissionDialog = false
                        (context as? Activity)?.let { activity ->
                            SmsHelper.requestSmsPermission(activity)
                        }
                    }
                ) {
                    Text("Grant Permission")
                }
            },
            dismissButton = {
                TextButton(onClick = { showPermissionDialog = false }) {
                    Text("Cancel")
                }
            }
        )
    }
}
