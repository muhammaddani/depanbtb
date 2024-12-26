package com.example.tbptb.ui.permintaansidang

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.tbptb.data.retrofit.ApiService
import com.example.tbptb.data.response.PermintaanSidangResponseItem
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

class PermintaanSidangViewModel(private val apiService: ApiService) : ViewModel() {
    private val _permintaanList = MutableStateFlow<List<PermintaanSidangResponseItem>>(emptyList())
    val permintaanList: StateFlow<List<PermintaanSidangResponseItem>> = _permintaanList

    private val _isLoading = MutableStateFlow(false)
    val isLoading: StateFlow<Boolean> = _isLoading

    private val _errorMessage = MutableStateFlow("")
    val errorMessage: StateFlow<String> = _errorMessage

    fun getPermintaanSidang() {
        _isLoading.value = true
        _errorMessage.value = ""
        viewModelScope.launch {
            try {
                val response = apiService.getPermintaanSidang()
                if (response.isSuccessful) {
                    // Directly set the list as the response body is an array
                    _permintaanList.value = response.body() ?: emptyList()
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

    data class SidangRequestBody(
        val status: String,
        val waktu: String?,
        val tempat: String?,
        val penguji_satu_id: String?,
        val penguji_dua_id: String?,
        val penguji_tiga_id: String?,
        val keterangan: String?
    )


    fun setujuiSidang(id: Int, waktu: String, tempat: String, dosenPengujiSatu: Int, dosenPengujiDua: Int, dosenPengujiTiga: Int, keterangan: String) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val requestBody = SidangRequestBody(
                    status = "disetujui",
                    waktu = waktu,
                    tempat = tempat,
                    penguji_satu_id = dosenPengujiSatu.toString(),
                    penguji_dua_id = dosenPengujiDua.toString(),
                    penguji_tiga_id = dosenPengujiTiga.toString(),
                    keterangan = keterangan
                )
                val response = apiService.approveOrRejectSidang(id, requestBody)
                if (response.isSuccessful) {
                    getPermintaanSidang()  // Refresh the list after the update
                } else {
                    _errorMessage.value = "Gagal menyetujui permintaan sidang: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi kesalahan: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }

    fun tolakSidang(id: Int) {
        _isLoading.value = true
        viewModelScope.launch {
            try {
                val requestBody = SidangRequestBody(
                    status = "ditolak", // Status permintaan sidang yang ditolak
                    waktu = "", // Kosongkan waktu
                    tempat = "", // Kosongkan tempat
                    penguji_satu_id = "", // Kosongkan ID dosen penguji
                    penguji_dua_id = "", // Kosongkan ID dosen penguji
                    penguji_tiga_id = "", // Kosongkan ID dosen penguji
                    keterangan = "" // Kosongkan keterangan
                )
                val response = apiService.approveOrRejectSidang(id, requestBody)
                if (response.isSuccessful) {
                    // Setelah berhasil menolak, muat ulang daftar permintaan sidang
                    getPermintaanSidang()
                } else {
                    _errorMessage.value = "Gagal menolak permintaan sidang: ${response.message()}"
                }
            } catch (e: Exception) {
                _errorMessage.value = "Terjadi kesalahan: ${e.localizedMessage}"
            } finally {
                _isLoading.value = false
            }
        }
    }
}




