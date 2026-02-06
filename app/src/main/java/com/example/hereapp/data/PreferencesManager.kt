package com.example.hereapp.data

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.longPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "settings")

class PreferencesManager(private val context: Context) {

    companion object {
        private val DEFAULT_MESSAGE_ID = longPreferencesKey("default_message_id")
        private val DEFAULT_CONTACT_ID = longPreferencesKey("default_contact_id")
    }

    val defaultMessageId: Flow<Long?> = context.dataStore.data.map { preferences ->
        preferences[DEFAULT_MESSAGE_ID]
    }

    val defaultContactId: Flow<Long?> = context.dataStore.data.map { preferences ->
        preferences[DEFAULT_CONTACT_ID]
    }

    suspend fun setDefaultMessageId(id: Long) {
        context.dataStore.edit { preferences ->
            preferences[DEFAULT_MESSAGE_ID] = id
        }
    }

    suspend fun setDefaultContactId(id: Long) {
        context.dataStore.edit { preferences ->
            preferences[DEFAULT_CONTACT_ID] = id
        }
    }

    suspend fun clearDefaultMessageId() {
        context.dataStore.edit { preferences ->
            preferences.remove(DEFAULT_MESSAGE_ID)
        }
    }

    suspend fun clearDefaultContactId() {
        context.dataStore.edit { preferences ->
            preferences.remove(DEFAULT_CONTACT_ID)
        }
    }
}
