package com.example.tbptb.ui.profile

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbptb.data.local.UserPreferences
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class ProfileViewModel(private val userPreferences: UserPreferences) : ViewModel() {

    // StateFlow untuk menyimpan data pengguna
    private val _username = MutableStateFlow<String>("")
    val username: StateFlow<String> = _username

    private val _email = MutableStateFlow<String>("")
    val email: StateFlow<String> = _email

    // Fungsi untuk memuat data pengguna dari DataStore
    fun loadUserData() {
        viewModelScope.launch {
            // Mengambil username dan email dari UserPreferences (menggunakan DataStore)
            userPreferences.getUsername().collect { savedUsername ->
                _username.value = savedUsername ?: "No Username"
            }
            userPreferences.getEmail().collect { savedEmail ->
                _email.value = savedEmail ?: "No Email"
            }
        }
    }

    // Fungsi untuk mengubah nama pengguna atau email jika diperlukan
//    fun updateUserData(newUsername: String, newEmail: String) {
//        viewModelScope.launch {
//            // Simpan data baru di UserPreferences (menggunakan DataStore)
//            userPreferences.saveUsername(newUsername)
//            userPreferences.saveEmail(newEmail)
//
//            // Memperbarui nilai StateFlow
//            _username.value = newUsername
//            _email.value = newEmail
//        }
//    }

    // Fungsi logout untuk menghapus data pengguna
    fun logout() {
        viewModelScope.launch {
            // Hapus token dan data pengguna dari DataStore
            userPreferences.logout()

            // Reset StateFlow setelah logout
            _username.value = ""
            _email.value = ""
        }
    }
}
