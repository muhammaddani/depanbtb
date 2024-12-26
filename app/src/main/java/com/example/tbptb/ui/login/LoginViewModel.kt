package com.example.tbptb.ui.login

import android.util.Log
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbptb.data.retrofit.ApiService
import com.example.tbptb.data.local.UserPreferences
import com.example.tbptb.data.response.LoginRequest
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

// LoginViewModel using StateFlow
class LoginViewModel(private val apiService: ApiService, private val userPreferences: UserPreferences) : ViewModel() {

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    private val _loginSuccess = MutableStateFlow(false)
    val loginSuccess: StateFlow<Boolean> = _loginSuccess

    // Function to handle the login logic
    fun login(loginRequest: LoginRequest) {
        viewModelScope.launch {
            try {
                _isLoading.value = true // Set loading ke true saat memulai login

                val response = apiService.login(loginRequest)

                // Debugging log: token yang diterima dari server
                Log.d("LoginViewModel", "Login response token: ${response.token}")

                if (response.token != null) {
                    // Token valid, simpan token ke SharedPreferences
                    userPreferences.saveToken(response.token)

                    // Verifikasi token setelah disimpan
                    val savedToken = userPreferences.getToken()
                    Log.d("LoginViewModel", "Saved token after saving: $savedToken")

                    if (savedToken != null) {
                        _errorMessage.value = "" // Clear error message
                        _isLoading.value = false // Set loading ke false setelah selesai
                        _loginSuccess.value = true // Set login berhasil ke true
                    } else {
                        _errorMessage.value = "Login failed: No token received"
                        _isLoading.value = false
                    }
                } else {
                    _errorMessage.value = "Login failed: Invalid email or password"
                    _isLoading.value = false
                }
            } catch (e: Exception) {
                _isLoading.value = false // Set loading ke false jika error terjadi
                _errorMessage.value = e.message ?: "An error occurred"
            }
        }
    }
}