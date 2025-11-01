package com.joeahkim.carrental.data.local

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.dataStore by preferencesDataStore("user_prefs")

class DataStoreManager(private val context: Context) {

    companion object {
        val USER_TOKEN = stringPreferencesKey("user_token")
        val USER_NAME = stringPreferencesKey("user_name")
        val USER_ID = stringPreferencesKey("user_id")
    }

    suspend fun saveUserData(token: String, name: String, id: String) {
        context.dataStore.edit { prefs ->
            prefs[USER_TOKEN] = token
            prefs[USER_NAME] = name
            prefs[USER_ID] = id
        }
    }

    fun getToken(): Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[USER_TOKEN]
    }

    fun getName(): Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[USER_NAME]
    }

    fun getUserId(): Flow<String?> = context.dataStore.data.map { prefs ->
        prefs[USER_ID]
    }

    suspend fun clear() {
        context.dataStore.edit { prefs ->
            prefs.clear()
        }
    }
}
