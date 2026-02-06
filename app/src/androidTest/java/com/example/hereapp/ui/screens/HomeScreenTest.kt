package com.example.hereapp.ui.screens

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.example.hereapp.data.Contact
import com.example.hereapp.data.Message
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class HomeScreenTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    private val sampleMessages = listOf(
        Message(id = 1, text = "I'm here!"),
        Message(id = 2, text = "On my way"),
        Message(id = 3, text = "Running late")
    )

    private val sampleContacts = listOf(
        Contact(id = 1, name = "Alice", phone = "555-1111"),
        Contact(id = 2, name = "Bob", phone = "555-2222"),
        Contact(id = 3, name = "Charlie", phone = "555-3333")
    )

    @Test
    fun displaysAppTitle() {
        composeTestRule.setContent {
            HomeScreen(
                defaultMessage = null,
                defaultContact = null,
                onNavigateToMessages = {},
                onNavigateToContacts = {}
            )
        }
        composeTestRule.onNodeWithText("HereApp").assertIsDisplayed()
    }

    @Test
    fun nullMessage_showsPlaceholder() {
        composeTestRule.setContent {
            HomeScreen(
                defaultMessage = null,
                defaultContact = null,
                onNavigateToMessages = {},
                onNavigateToContacts = {}
            )
        }
        composeTestRule.onNodeWithText("No default message set").assertIsDisplayed()
    }

    @Test
    fun nullContact_showsPlaceholder() {
        composeTestRule.setContent {
            HomeScreen(
                defaultMessage = null,
                defaultContact = null,
                onNavigateToMessages = {},
                onNavigateToContacts = {}
            )
        }
        composeTestRule.onNodeWithText("No default contact set").assertIsDisplayed()
    }

    @Test
    fun withMessage_showsMessageText() {
        composeTestRule.setContent {
            HomeScreen(
                defaultMessage = Message(id = 1, text = "I'm here!"),
                defaultContact = null,
                onNavigateToMessages = {},
                onNavigateToContacts = {}
            )
        }
        composeTestRule.onNodeWithText("I'm here!").assertIsDisplayed()
    }

    @Test
    fun withContact_showsContactNameAndPhone() {
        composeTestRule.setContent {
            HomeScreen(
                defaultMessage = null,
                defaultContact = Contact(id = 1, name = "Alice", phone = "555-1234"),
                onNavigateToMessages = {},
                onNavigateToContacts = {}
            )
        }
        composeTestRule.onNodeWithText("Alice").assertIsDisplayed()
        composeTestRule.onNodeWithText("555-1234").assertIsDisplayed()
    }

    @Test
    fun sendButton_disabledWhenNoDefaults() {
        composeTestRule.setContent {
            HomeScreen(
                defaultMessage = null,
                defaultContact = null,
                onNavigateToMessages = {},
                onNavigateToContacts = {}
            )
        }
        composeTestRule.onNodeWithText("SEND").assertIsDisplayed()
        composeTestRule.onNodeWithText("SEND").assertIsNotEnabled()
    }

    @Test
    fun sendButton_disabledWhenOnlyMessage() {
        composeTestRule.setContent {
            HomeScreen(
                defaultMessage = Message(id = 1, text = "Hi"),
                defaultContact = null,
                onNavigateToMessages = {},
                onNavigateToContacts = {}
            )
        }
        composeTestRule.onNodeWithText("SEND").assertIsNotEnabled()
    }

    @Test
    fun sendButton_disabledWhenOnlyContact() {
        composeTestRule.setContent {
            HomeScreen(
                defaultMessage = null,
                defaultContact = Contact(id = 1, name = "Alice", phone = "555-1234"),
                onNavigateToMessages = {},
                onNavigateToContacts = {}
            )
        }
        composeTestRule.onNodeWithText("SEND").assertIsNotEnabled()
    }

    @Test
    fun sendButton_enabledWhenBothDefaultsSet() {
        composeTestRule.setContent {
            HomeScreen(
                defaultMessage = Message(id = 1, text = "Hi"),
                defaultContact = Contact(id = 1, name = "Alice", phone = "555-1234"),
                onNavigateToMessages = {},
                onNavigateToContacts = {}
            )
        }
        composeTestRule.onNodeWithText("SEND").assertIsEnabled()
    }

    @Test
    fun noDefaults_showsHintText() {
        composeTestRule.setContent {
            HomeScreen(
                defaultMessage = null,
                defaultContact = null,
                onNavigateToMessages = {},
                onNavigateToContacts = {}
            )
        }
        composeTestRule.onNodeWithText("Set a default message and contact to enable sending")
            .assertIsDisplayed()
    }

    @Test
    fun bothDefaultsSet_hintNotShown() {
        composeTestRule.setContent {
            HomeScreen(
                defaultMessage = Message(id = 1, text = "Hi"),
                defaultContact = Contact(id = 1, name = "Alice", phone = "555-1234"),
                onNavigateToMessages = {},
                onNavigateToContacts = {}
            )
        }
        composeTestRule.onNodeWithText("Set a default message and contact to enable sending")
            .assertDoesNotExist()
    }

    @Test
    fun navigateToMessages_callback_triggered() {
        var called = false
        composeTestRule.setContent {
            HomeScreen(
                defaultMessage = null,
                defaultContact = null,
                onNavigateToMessages = { called = true },
                onNavigateToContacts = {}
            )
        }
        composeTestRule.onNodeWithContentDescription("Messages").performClick()
        assertTrue(called)
    }

    @Test
    fun navigateToContacts_callback_triggered() {
        var called = false
        composeTestRule.setContent {
            HomeScreen(
                defaultMessage = null,
                defaultContact = null,
                onNavigateToMessages = {},
                onNavigateToContacts = { called = true }
            )
        }
        composeTestRule.onNodeWithContentDescription("Contacts").performClick()
        assertTrue(called)
    }

    @Test
    fun displaysMessageLabel() {
        composeTestRule.setContent {
            HomeScreen(
                defaultMessage = null,
                defaultContact = null,
                onNavigateToMessages = {},
                onNavigateToContacts = {}
            )
        }
        composeTestRule.onNodeWithText("Message").assertIsDisplayed()
    }

    @Test
    fun displaysRecipientLabel() {
        composeTestRule.setContent {
            HomeScreen(
                defaultMessage = null,
                defaultContact = null,
                onNavigateToMessages = {},
                onNavigateToContacts = {}
            )
        }
        composeTestRule.onNodeWithText("Recipient").assertIsDisplayed()
    }

    // --- Expand/collapse and selection tests ---

    @Test
    fun messageCard_showsExpandArrow() {
        composeTestRule.setContent {
            HomeScreen(
                defaultMessage = null,
                defaultContact = null,
                onNavigateToMessages = {},
                onNavigateToContacts = {}
            )
        }
        composeTestRule.onNodeWithContentDescription("Expand messages").assertIsDisplayed()
    }

    @Test
    fun contactCard_showsExpandArrow() {
        composeTestRule.setContent {
            HomeScreen(
                defaultMessage = null,
                defaultContact = null,
                onNavigateToMessages = {},
                onNavigateToContacts = {}
            )
        }
        composeTestRule.onNodeWithContentDescription("Expand contacts").assertIsDisplayed()
    }

    @Test
    fun messageList_notVisibleByDefault() {
        composeTestRule.setContent {
            HomeScreen(
                defaultMessage = null,
                defaultContact = null,
                messages = sampleMessages,
                contacts = sampleContacts,
                onNavigateToMessages = {},
                onNavigateToContacts = {}
            )
        }
        composeTestRule.onNodeWithText("On my way").assertDoesNotExist()
    }

    @Test
    fun contactList_notVisibleByDefault() {
        composeTestRule.setContent {
            HomeScreen(
                defaultMessage = null,
                defaultContact = null,
                messages = sampleMessages,
                contacts = sampleContacts,
                onNavigateToMessages = {},
                onNavigateToContacts = {}
            )
        }
        composeTestRule.onNodeWithText("Bob").assertDoesNotExist()
    }

    @Test
    fun tapMessageCard_expandsMessageList() {
        composeTestRule.setContent {
            HomeScreen(
                defaultMessage = sampleMessages[0],
                defaultContact = null,
                messages = sampleMessages,
                onNavigateToMessages = {},
                onNavigateToContacts = {}
            )
        }
        composeTestRule.onNodeWithContentDescription("Expand messages").performClick()
        composeTestRule.onNodeWithText("On my way").assertIsDisplayed()
        composeTestRule.onNodeWithText("Running late").assertIsDisplayed()
    }

    @Test
    fun tapContactCard_expandsContactList() {
        composeTestRule.setContent {
            HomeScreen(
                defaultMessage = null,
                defaultContact = sampleContacts[0],
                contacts = sampleContacts,
                onNavigateToMessages = {},
                onNavigateToContacts = {}
            )
        }
        composeTestRule.onNodeWithContentDescription("Expand contacts").performClick()
        composeTestRule.onNodeWithText("Bob").assertIsDisplayed()
        composeTestRule.onNodeWithText("Charlie").assertIsDisplayed()
    }

    @Test
    fun expandedMessageCard_showsCollapseArrow() {
        composeTestRule.setContent {
            HomeScreen(
                defaultMessage = null,
                defaultContact = null,
                messages = sampleMessages,
                onNavigateToMessages = {},
                onNavigateToContacts = {}
            )
        }
        composeTestRule.onNodeWithContentDescription("Expand messages").performClick()
        composeTestRule.onNodeWithContentDescription("Collapse messages").assertIsDisplayed()
    }

    @Test
    fun selectMessage_callsCallback() {
        var selectedId: Long? = null
        composeTestRule.setContent {
            HomeScreen(
                defaultMessage = sampleMessages[0],
                defaultContact = null,
                messages = sampleMessages,
                onSelectMessage = { selectedId = it },
                onNavigateToMessages = {},
                onNavigateToContacts = {}
            )
        }
        composeTestRule.onNodeWithContentDescription("Expand messages").performClick()
        composeTestRule.onNodeWithText("On my way").performClick()
        assertEquals(2L, selectedId)
    }

    @Test
    fun selectContact_callsCallback() {
        var selectedId: Long? = null
        composeTestRule.setContent {
            HomeScreen(
                defaultMessage = null,
                defaultContact = sampleContacts[0],
                contacts = sampleContacts,
                onSelectContact = { selectedId = it },
                onNavigateToMessages = {},
                onNavigateToContacts = {}
            )
        }
        composeTestRule.onNodeWithContentDescription("Expand contacts").performClick()
        composeTestRule.onNodeWithText("Bob").performClick()
        assertEquals(2L, selectedId)
    }

    @Test
    fun emptyMessageList_showsPlaceholder() {
        composeTestRule.setContent {
            HomeScreen(
                defaultMessage = null,
                defaultContact = null,
                messages = emptyList(),
                onNavigateToMessages = {},
                onNavigateToContacts = {}
            )
        }
        composeTestRule.onNodeWithContentDescription("Expand messages").performClick()
        composeTestRule.onNodeWithText("No messages configured").assertIsDisplayed()
    }

    @Test
    fun emptyContactList_showsPlaceholder() {
        composeTestRule.setContent {
            HomeScreen(
                defaultMessage = null,
                defaultContact = null,
                contacts = emptyList(),
                onNavigateToMessages = {},
                onNavigateToContacts = {}
            )
        }
        composeTestRule.onNodeWithContentDescription("Expand contacts").performClick()
        composeTestRule.onNodeWithText("No contacts configured").assertIsDisplayed()
    }

    @Test
    fun selectMessage_collapsesListAfterSelection() {
        composeTestRule.setContent {
            HomeScreen(
                defaultMessage = sampleMessages[0],
                defaultContact = null,
                messages = sampleMessages,
                onSelectMessage = {},
                onNavigateToMessages = {},
                onNavigateToContacts = {}
            )
        }
        composeTestRule.onNodeWithContentDescription("Expand messages").performClick()
        composeTestRule.onNodeWithText("On my way").performClick()
        composeTestRule.onNodeWithText("Running late").assertDoesNotExist()
    }

    @Test
    fun selectContact_collapsesListAfterSelection() {
        composeTestRule.setContent {
            HomeScreen(
                defaultMessage = null,
                defaultContact = sampleContacts[0],
                contacts = sampleContacts,
                onSelectContact = {},
                onNavigateToMessages = {},
                onNavigateToContacts = {}
            )
        }
        composeTestRule.onNodeWithContentDescription("Expand contacts").performClick()
        composeTestRule.onNodeWithText("Bob").performClick()
        composeTestRule.onNodeWithText("Charlie").assertDoesNotExist()
    }
}
