package com.example.tbptb.data.local

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.*
import androidx.datastore.preferences.preferencesDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

// Menggunakan DataStore untuk menyimpan data preferensi pengguna
private val Context.dataStore: DataStore<Preferences> by preferencesDataStore(name = "user_preferences")

class UserPreferences private constructor(private val dataStore: DataStore<Preferences>) {

    // Definisikan kunci-kunci yang akan digunakan untuk menyimpan data di DataStore
    private val IS_LOGGED_IN = booleanPreferencesKey("is_logged_in")
    private val ACCESS_TOKEN = stringPreferencesKey("access_token")
    private val USER_NAME = stringPreferencesKey("user_name")
    private val USER_EMAIL = stringPreferencesKey("user_email")
    private val USER_ID = stringPreferencesKey("user_id")

    // Fungsi untuk menyimpan token
    suspend fun saveToken(token: String) {
        dataStore.edit { preferences ->
            preferences[ACCESS_TOKEN] = token
        }
    }

    // Fungsi untuk mengambil token
    fun getToken(): Flow<String?> {
        return dataStore.data
            .map { preferences -> preferences[ACCESS_TOKEN] }
    }

    // Fungsi untuk menyimpan status login pengguna
    suspend fun saveIsUserLoggedIn(isLoggedIn: Boolean) {
        dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = isLoggedIn
        }
    }

    // Fungsi untuk memeriksa apakah pengguna sudah login
    fun isUserLoggedIn(): Flow<Boolean> {
        return dataStore.data
            .map { preferences -> preferences[IS_LOGGED_IN] == true }
    }

    // Fungsi untuk menyimpan data pengguna
    suspend fun saveUser(username: String, email: String, userId: String) {
        dataStore.edit { preferences ->
            preferences[USER_NAME] = username
            preferences[USER_EMAIL] = email
            preferences[USER_ID] = userId
        }
    }

    // Fungsi untuk mendapatkan username
    fun getUsername(): Flow<String?> {
        return dataStore.data
            .map { preferences -> preferences[USER_NAME] }
    }

    // Fungsi untuk mendapatkan email
    fun getEmail(): Flow<String?> {
        return dataStore.data
            .map { preferences -> preferences[USER_EMAIL] }
    }

    // Fungsi untuk menghapus data pengguna
    suspend fun logout() {
        dataStore.edit { preferences ->
            preferences[IS_LOGGED_IN] = false
            preferences[ACCESS_TOKEN] = ""
            preferences[USER_NAME] = ""
            preferences[USER_EMAIL] = ""
            preferences[USER_ID] = ""
        }
    }

    // Singleton pattern untuk mendapatkan instance UserPreferences
    companion object {
        @Volatile
        private var INSTANCE: UserPreferences? = null

        fun getInstance(context: Context): UserPreferences {
            return INSTANCE ?: synchronized(this) {
                INSTANCE ?: UserPreferences(context.dataStore).also { INSTANCE = it }
            }
        }
    }
}
