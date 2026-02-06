package com.example.hereapp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.hereapp.data.*
import com.example.hereapp.ui.screens.ContactsScreen
import com.example.hereapp.ui.screens.HomeScreen
import com.example.hereapp.ui.screens.MessagesScreen
import com.example.hereapp.ui.theme.HereAppTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {
    private lateinit var database: AppDatabase
    private lateinit var preferencesManager: PreferencesManager

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        database = AppDatabase.getDatabase(this)
        preferencesManager = PreferencesManager(this)

        setContent {
            HereAppTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    HereAppNavigation(
                        database = database,
                        preferencesManager = preferencesManager
                    )
                }
            }
        }
    }
}

@Composable
fun HereAppNavigation(
    database: AppDatabase,
    preferencesManager: PreferencesManager
) {
    val navController = rememberNavController()
    val scope = rememberCoroutineScope()

    // Collect data from database
    val messages by database.messageDao().getAllMessages().collectAsState(initial = emptyList())
    val contacts by database.contactDao().getAllContacts().collectAsState(initial = emptyList())
    val defaultMessageId by preferencesManager.defaultMessageId.collectAsState(initial = null)
    val defaultContactId by preferencesManager.defaultContactId.collectAsState(initial = null)

    // Derive default message and contact
    val defaultMessage = messages.find { it.id == defaultMessageId }
    val defaultContact = contacts.find { it.id == defaultContactId }

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                defaultMessage = defaultMessage,
                defaultContact = defaultContact,
                messages = messages,
                contacts = contacts,
                onSelectMessage = { id ->
                    scope.launch {
                        preferencesManager.setDefaultMessageId(id)
                    }
                },
                onSelectContact = { id ->
                    scope.launch {
                        preferencesManager.setDefaultContactId(id)
                    }
                },
                onNavigateToMessages = { navController.navigate("messages") },
                onNavigateToContacts = { navController.navigate("contacts") }
            )
        }

        composable("messages") {
            MessagesScreen(
                messages = messages,
                defaultMessageId = defaultMessageId,
                onAddMessage = { text ->
                    scope.launch {
                        database.messageDao().insert(Message(text = text))
                    }
                },
                onUpdateMessage = { message ->
                    scope.launch {
                        database.messageDao().update(message)
                    }
                },
                onDeleteMessage = { message ->
                    scope.launch {
                        database.messageDao().delete(message)
                        if (defaultMessageId == message.id) {
                            preferencesManager.clearDefaultMessageId()
                        }
                    }
                },
                onSetDefault = { id ->
                    scope.launch {
                        preferencesManager.setDefaultMessageId(id)
                    }
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }

        composable("contacts") {
            ContactsScreen(
                contacts = contacts,
                defaultContactId = defaultContactId,
                onAddContact = { name, phone ->
                    scope.launch {
                        database.contactDao().insert(Contact(name = name, phone = phone))
                    }
                },
                onUpdateContact = { contact ->
                    scope.launch {
                        database.contactDao().update(contact)
                    }
                },
                onDeleteContact = { contact ->
                    scope.launch {
                        database.contactDao().delete(contact)
                        if (defaultContactId == contact.id) {
                            preferencesManager.clearDefaultContactId()
                        }
                    }
                },
                onSetDefault = { id ->
                    scope.launch {
                        preferencesManager.setDefaultContactId(id)
                    }
                },
                onNavigateBack = { navController.popBackStack() }
            )
        }
    }
}
