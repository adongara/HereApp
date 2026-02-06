package com.example.hereapp.ui.components

import androidx.compose.ui.test.*
import androidx.compose.ui.test.junit4.createComposeRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.junit.Assert.*
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class CommonComponentsTest {

    @get:Rule
    val composeTestRule = createComposeRule()

    // --- ItemCard tests ---

    @Test
    fun itemCard_displaysTitle() {
        composeTestRule.setContent {
            ItemCard(
                title = "Test Title",
                isDefault = false,
                onSetDefault = {},
                onEdit = {},
                onDelete = {}
            )
        }
        composeTestRule.onNodeWithText("Test Title").assertIsDisplayed()
    }

    @Test
    fun itemCard_displaysSubtitle_whenProvided() {
        composeTestRule.setContent {
            ItemCard(
                title = "Title",
                subtitle = "Subtitle",
                isDefault = false,
                onSetDefault = {},
                onEdit = {},
                onDelete = {}
            )
        }
        composeTestRule.onNodeWithText("Subtitle").assertIsDisplayed()
    }

    @Test
    fun itemCard_noSubtitle_whenNull() {
        composeTestRule.setContent {
            ItemCard(
                title = "Title",
                subtitle = null,
                isDefault = false,
                onSetDefault = {},
                onEdit = {},
                onDelete = {}
            )
        }
        composeTestRule.onNodeWithText("Title").assertIsDisplayed()
    }

    @Test
    fun itemCard_defaultStar_showsFilledWhenDefault() {
        composeTestRule.setContent {
            ItemCard(
                title = "Title",
                isDefault = true,
                onSetDefault = {},
                onEdit = {},
                onDelete = {}
            )
        }
        composeTestRule.onNodeWithContentDescription("Default").assertIsDisplayed()
    }

    @Test
    fun itemCard_defaultStar_showsOutlineWhenNotDefault() {
        composeTestRule.setContent {
            ItemCard(
                title = "Title",
                isDefault = false,
                onSetDefault = {},
                onEdit = {},
                onDelete = {}
            )
        }
        composeTestRule.onNodeWithContentDescription("Set as default").assertIsDisplayed()
    }

    @Test
    fun itemCard_setDefaultCallback_triggered() {
        var called = false
        composeTestRule.setContent {
            ItemCard(
                title = "Title",
                isDefault = false,
                onSetDefault = { called = true },
                onEdit = {},
                onDelete = {}
            )
        }
        composeTestRule.onNodeWithContentDescription("Set as default").performClick()
        assertTrue(called)
    }

    @Test
    fun itemCard_editCallback_triggered() {
        var called = false
        composeTestRule.setContent {
            ItemCard(
                title = "Title",
                isDefault = false,
                onSetDefault = {},
                onEdit = { called = true },
                onDelete = {}
            )
        }
        composeTestRule.onNodeWithContentDescription("Edit").performClick()
        assertTrue(called)
    }

    @Test
    fun itemCard_deleteCallback_triggered() {
        var called = false
        composeTestRule.setContent {
            ItemCard(
                title = "Title",
                isDefault = false,
                onSetDefault = {},
                onEdit = {},
                onDelete = { called = true }
            )
        }
        composeTestRule.onNodeWithContentDescription("Delete").performClick()
        assertTrue(called)
    }

    // --- EditDialog tests ---

    @Test
    fun editDialog_displaysTitle() {
        composeTestRule.setContent {
            EditDialog(
                title = "Edit Message",
                fields = listOf("Text" to "Hello"),
                onDismiss = {},
                onConfirm = {}
            )
        }
        composeTestRule.onNodeWithText("Edit Message").assertIsDisplayed()
    }

    @Test
    fun editDialog_displaysFieldValues() {
        composeTestRule.setContent {
            EditDialog(
                title = "Edit",
                fields = listOf("Name" to "Alice", "Phone" to "555-1234"),
                onDismiss = {},
                onConfirm = {}
            )
        }
        composeTestRule.onNodeWithText("Alice").assertIsDisplayed()
        composeTestRule.onNodeWithText("555-1234").assertIsDisplayed()
    }

    @Test
    fun editDialog_saveEnabled_whenFieldsNotBlank() {
        composeTestRule.setContent {
            EditDialog(
                title = "Edit",
                fields = listOf("Text" to "Hello"),
                onDismiss = {},
                onConfirm = {}
            )
        }
        composeTestRule.onNodeWithText("Save").assertIsEnabled()
    }

    @Test
    fun editDialog_saveDisabled_whenFieldBlank() {
        composeTestRule.setContent {
            EditDialog(
                title = "Edit",
                fields = listOf("Text" to ""),
                onDismiss = {},
                onConfirm = {}
            )
        }
        composeTestRule.onNodeWithText("Save").assertIsNotEnabled()
    }

    @Test
    fun editDialog_cancelCallback_triggered() {
        var called = false
        composeTestRule.setContent {
            EditDialog(
                title = "Edit",
                fields = listOf("Text" to "Hello"),
                onDismiss = { called = true },
                onConfirm = {}
            )
        }
        composeTestRule.onNodeWithText("Cancel").performClick()
        assertTrue(called)
    }

    @Test
    fun editDialog_confirmCallback_triggered() {
        var result: List<String>? = null
        composeTestRule.setContent {
            EditDialog(
                title = "Edit",
                fields = listOf("Text" to "Hello"),
                onDismiss = {},
                onConfirm = { result = it }
            )
        }
        composeTestRule.onNodeWithText("Save").performClick()
        assertNotNull(result)
        assertEquals(listOf("Hello"), result)
    }

    // --- DeleteConfirmationDialog tests ---

    @Test
    fun deleteDialog_displaysItemName() {
        composeTestRule.setContent {
            DeleteConfirmationDialog(
                itemName = "My Message",
                onDismiss = {},
                onConfirm = {}
            )
        }
        composeTestRule.onNodeWithText("Are you sure you want to delete \"My Message\"?")
            .assertIsDisplayed()
    }

    @Test
    fun deleteDialog_displaysDeleteTitle() {
        composeTestRule.setContent {
            DeleteConfirmationDialog(
                itemName = "Item",
                onDismiss = {},
                onConfirm = {}
            )
        }
        composeTestRule.onNodeWithText("Delete").assertIsDisplayed()
    }

    @Test
    fun deleteDialog_confirmCallback_triggered() {
        var called = false
        composeTestRule.setContent {
            DeleteConfirmationDialog(
                itemName = "Item",
                onDismiss = {},
                onConfirm = { called = true }
            )
        }
        // There are two "Delete" texts (title and button), click the button
        composeTestRule.onAllNodesWithText("Delete")[1].performClick()
        assertTrue(called)
    }

    @Test
    fun deleteDialog_cancelCallback_triggered() {
        var called = false
        composeTestRule.setContent {
            DeleteConfirmationDialog(
                itemName = "Item",
                onDismiss = { called = true },
                onConfirm = {}
            )
        }
        composeTestRule.onNodeWithText("Cancel").performClick()
        assertTrue(called)
    }
}
