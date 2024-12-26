package com.example.tbptb.ui.homepage

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.identity.cbor.DataItem
import com.example.tbptb.data.local.UserPreferences
import com.example.tbptb.data.retrofit.ApiService
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class HomeViewModel(private val apiService: ApiService, private val userPreferences: UserPreferences) : ViewModel() {

    // StateFlow untuk menyimpan informasi pengguna atau status lainnya
    private val _userInfo = MutableStateFlow<String>("")
    val userInfo: StateFlow<String> = _userInfo

    // StateFlow untuk status loading
    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    // Function untuk mengambil informasi pengguna dari preferences
    fun getUserInfo() {
        viewModelScope.launch {
            _isLoading.value = true  // Set loading ke true

            try {
                // Periksa apakah pengguna sudah login
                userPreferences.isUserLoggedIn().collect { isLoggedIn ->
                    if (isLoggedIn) {
                        // Jika pengguna sudah login, tampilkan info pengguna
                        val username = userPreferences.getUsername() ?: "User"
                        _userInfo.value = "Welcome, $username!"
                    } else {
                        // Jika tidak ada token, tampilkan pesan error
                        _userInfo.value = "No user information available"
                    }
                }
            } catch (e: Exception) {
                _userInfo.value = "Error: ${e.message}"
            } finally {
                _isLoading.value = false  // Set loading ke false
            }
        }
    }

    // Fungsi logout untuk menghapus data pengguna dari preferences
    fun logout() {
        viewModelScope.launch {
            userPreferences.logout()  // Menghapus data pengguna dari DataStore
            _userInfo.value = "Logged out successfully"
        }
    }
}

