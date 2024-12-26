package com.example.tbptb.ui.jadwalsidang

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbptb.data.local.UserPreferences
import com.example.tbptb.data.retrofit.ApiService
import com.example.tbptb.data.response.JadwalSidangResponse
import com.example.tbptb.data.response.JadwalSidangResponseItem
import com.example.tbptb.data.response.PermintaanSidangResponseItem
import com.example.tbptb.data.response.Peserta
import com.example.tbptb.data.retrofit.ApiConfig
import com.example.tbptb.data.retrofit.ApiConfig.apiService
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import retrofit2.HttpException
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flow
import kotlinx.coroutines.flow.collectLatest

class JadwalSidangViewModel(private val apiService: ApiService) : ViewModel() {

    private val _jadwalSidangList = MutableStateFlow<List<JadwalSidangResponseItem>>(emptyList())
    val jadwalSidangList: StateFlow<List<JadwalSidangResponseItem>> = _jadwalSidangList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    init {
        // Memulai pengambilan data ketika ViewModel diinisialisasi
        getJadwalSidang()
        setupAutoRefresh()
    }

    private fun setupAutoRefresh() {
        viewModelScope.launch {
            // Interval waktu dalam milidetik untuk refresh, contoh setiap 60 detik
            val refreshIntervalMs = 60_000L
            while (true) {
                delay(refreshIntervalMs)
                getJadwalSidang()
            }
        }
    }

    fun getJadwalSidang() {
        _isLoading.value = true
        _errorMessage.value = ""
        viewModelScope.launch {
            try {
                val response = apiService.getJadwalSidang()
                if (response.isSuccessful) {
                    _jadwalSidangList.value = response.body() ?: emptyList()
                } else {
                    _errorMessage.value = "Gagal memuat data: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi kesalahan: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}







