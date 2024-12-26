package com.example.tbptb.ui.jadwalsidang

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.BasicText
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tbptb.R
import com.example.tbptb.data.local.UserPreferences
import com.example.tbptb.data.retrofit.ApiConfig
import com.example.tbptb.ui.ViewModelFactory
import com.example.tbptb.data.response.JadwalSidangResponseItem
import com.example.tbptb.data.response.Mahasiswa
import com.example.tbptb.data.response.PermintaanSidangResponseItem
import com.example.tbptb.data.retrofit.ApiService
import com.example.tbptb.ui.permintaansidang.PermintaanSidangViewModel
import java.time.LocalDate
import java.time.format.DateTimeFormatter

// JadwalSidangScreen.kt
@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun JadwalSidangScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    jadwalSidangViewModel: JadwalSidangViewModel = viewModel(factory = ViewModelFactory(ApiConfig.apiService, UserPreferences.getInstance(LocalContext.current))),
    permintaanSidangViewModel: PermintaanSidangViewModel = viewModel(factory = ViewModelFactory(ApiConfig.apiService, UserPreferences.getInstance(LocalContext.current)))
) {
    // Mengambil data permintaan sidang dari ViewModel
    val jadwalSidangList by jadwalSidangViewModel.jadwalSidangList.collectAsState()
    val isLoading by jadwalSidangViewModel.isLoading.collectAsState()
    val errorMessage by jadwalSidangViewModel.errorMessage.collectAsState()

    // Urutkan permintaan sidang berdasarkan tanggal
    val sortedPermintaanList = jadwalSidangList.sortedBy { permintaan ->
        val waktuString = permintaan.waktu ?: ""
        try {
            // Mengubah string waktu menjadi LocalDate untuk perbandingan
            LocalDate.parse(waktuString.toString(), DateTimeFormatter.ISO_DATE)
        } catch (e: Exception) {
            // Jika waktu tidak valid, anggap sebagai tanggal yang sangat lama
            LocalDate.MIN
        }
    }
    LaunchedEffect(Unit) {
        jadwalSidangViewModel.getJadwalSidang() // Ambil data permintaan sidang saat layar dimuat
    }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        } else if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        } else if (sortedPermintaanList.isEmpty()) {
            Text(text = "Tidak ada permintaan sidang", color = MaterialTheme.colorScheme.onSurface)
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(sortedPermintaanList) { permintaan ->
                    val mahasiswa = permintaan.permintaanTum?.mahasiswa
                    val waktu = permintaan.waktu ?: "Tanggal tidak ditemukan"
                    val tempat = permintaan.tempat ?: "Tempat tidak ditemukan"


                    // Card untuk menampilkan setiap permintaan sidang
                    Card(modifier = Modifier.fillMaxWidth().padding(8.dp)) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            Text(text = "Nama: ${mahasiswa?.nama ?: "Nama tidak ditemukan"}", fontWeight = FontWeight.Bold)
                            Text(text = "NIM: ${mahasiswa?.nIM ?: "NIM tidak ditemukan"}")
                            Text(text = "Judul TA: ${permintaan.permintaanTum?.judul ?: "Judul tidak ditemukan"}")
                            Text(text = "Waktu: $waktu")
                            Text(text = "Tempat: $tempat")

                            permintaan.permintaanTum?.dosenPembimbing?.let { pembimbing ->
                                Text(text = "Pembimbing Satu: ${pembimbing.pembimbingSatu?.nama ?: "Pembimbing Satu tidak ditemukan"}")
                                Text(text = "Pembimbing Dua: ${pembimbing.pembimbingDua?.nama ?: "Pembimbing Dua tidak ditemukan"}")
                            }
                        }
                    }
                }
            }
        }
    }
}





