package com.example.hereapp.data

import android.content.Context
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class PreferencesManagerTest {

    private lateinit var preferencesManager: PreferencesManager

    @Before
    fun setUp() = runBlocking {
        val context = ApplicationProvider.getApplicationContext<Context>()
        preferencesManager = PreferencesManager(context)
        preferencesManager.clearDefaultMessageId()
        preferencesManager.clearDefaultContactId()
    }

    @Test
    fun defaultMessageId_initiallyNull() = runBlocking {
        val id = preferencesManager.defaultMessageId.first()
        assertNull(id)
    }

    @Test
    fun defaultContactId_initiallyNull() = runBlocking {
        val id = preferencesManager.defaultContactId.first()
        assertNull(id)
    }

    @Test
    fun setDefaultMessageId_canBeRead() = runBlocking {
        preferencesManager.setDefaultMessageId(42)
        val id = preferencesManager.defaultMessageId.first()
        assertEquals(42L, id)
    }

    @Test
    fun setDefaultContactId_canBeRead() = runBlocking {
        preferencesManager.setDefaultContactId(99)
        val id = preferencesManager.defaultContactId.first()
        assertEquals(99L, id)
    }

    @Test
    fun setDefaultMessageId_overwritesPrevious() = runBlocking {
        preferencesManager.setDefaultMessageId(1)
        preferencesManager.setDefaultMessageId(2)
        val id = preferencesManager.defaultMessageId.first()
        assertEquals(2L, id)
    }

    @Test
    fun setDefaultContactId_overwritesPrevious() = runBlocking {
        preferencesManager.setDefaultContactId(1)
        preferencesManager.setDefaultContactId(2)
        val id = preferencesManager.defaultContactId.first()
        assertEquals(2L, id)
    }

    @Test
    fun clearDefaultMessageId_setsToNull() = runBlocking {
        preferencesManager.setDefaultMessageId(42)
        preferencesManager.clearDefaultMessageId()
        val id = preferencesManager.defaultMessageId.first()
        assertNull(id)
    }

    @Test
    fun clearDefaultContactId_setsToNull() = runBlocking {
        preferencesManager.setDefaultContactId(99)
        preferencesManager.clearDefaultContactId()
        val id = preferencesManager.defaultContactId.first()
        assertNull(id)
    }

    @Test
    fun messageAndContactIds_areIndependent() = runBlocking {
        preferencesManager.setDefaultMessageId(10)
        preferencesManager.setDefaultContactId(20)
        assertEquals(10L, preferencesManager.defaultMessageId.first())
        assertEquals(20L, preferencesManager.defaultContactId.first())
    }

    @Test
    fun clearMessageId_doesNotAffectContactId() = runBlocking {
        preferencesManager.setDefaultMessageId(10)
        preferencesManager.setDefaultContactId(20)
        preferencesManager.clearDefaultMessageId()
        assertNull(preferencesManager.defaultMessageId.first())
        assertEquals(20L, preferencesManager.defaultContactId.first())
    }
}
