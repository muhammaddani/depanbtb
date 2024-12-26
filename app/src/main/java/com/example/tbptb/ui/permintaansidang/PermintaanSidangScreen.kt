package com.example.tbptb.ui.permintaansidang

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
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
import com.example.tbptb.data.response.PermintaanSidangResponseItem

@Composable
fun DosenRadioButtons(
    selectedDosenId: Int?,
    dosenList: List<Dosen>,
    onDosenSelected: (Int) -> Unit
) {
    Column {
        dosenList.forEach { dosen ->
            Row(verticalAlignment = Alignment.CenterVertically) {
                RadioButton(
                    selected = selectedDosenId == dosen.id,
                    onClick = { onDosenSelected(dosen.id) }
                )
                Text(text = dosen.nama)
            }
        }
    }
}

@Composable
fun PermintaanSidangScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    permintaanSidangViewModel: PermintaanSidangViewModel = viewModel(factory = ViewModelFactory(ApiConfig.apiService, UserPreferences.getInstance(LocalContext.current)))
) {
    val permintaanList by permintaanSidangViewModel.permintaanList.collectAsState()
    val isLoading by permintaanSidangViewModel.isLoading.collectAsState()
    val errorMessage by permintaanSidangViewModel.errorMessage.collectAsState()

    val showConfirmationDialog = remember { mutableStateOf(false) }
    val showFormDialog = remember { mutableStateOf(false) }
    val showDetailDialog = remember { mutableStateOf(false) }
    val selectedPermintaanId = remember { mutableStateOf<Int?>(null) }
    val selectedPermintaan = remember { mutableStateOf<PermintaanSidangResponseItem?>(null) }

    var waktu by remember { mutableStateOf("") }
    var tempat by remember { mutableStateOf("") }
    var dosenPengujiSatu by remember { mutableStateOf<Int?>(null) }
    var dosenPengujiDua by remember { mutableStateOf<Int?>(null) }
    var dosenPengujiTiga by remember { mutableStateOf<Int?>(null) }
    var keterangan by remember { mutableStateOf("") }

    val dosenList = remember { listOf(
        Dosen(1, "Dosen 1"),
        Dosen(2, "Dosen 2"),
        Dosen(3, "Dosen 3")
    ) }

    LaunchedEffect(Unit) {
        permintaanSidangViewModel.getPermintaanSidang() // Call the API to get sidang requests
    }

    Column(
        modifier = modifier.fillMaxSize().padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.padding(16.dp))
        } else if (errorMessage.isNotEmpty()) {
            Text(text = errorMessage, color = MaterialTheme.colorScheme.error)
        } else if (permintaanList.isEmpty()) {
            Text(text = "Tidak ada permintaan sidang", color = MaterialTheme.colorScheme.onSurface)
        } else {
            LazyColumn(modifier = Modifier.fillMaxSize()) {
                items(permintaanList) { permintaan ->
                    // Card with a clickable action
                    Card(modifier = Modifier.fillMaxWidth().padding(8.dp).clickable {
                        selectedPermintaanId.value = permintaan.id
                        selectedPermintaan.value = permintaan
                        showDetailDialog.value = true
                    }) {
                        Column(modifier = Modifier.padding(16.dp)) {
                            val mahasiswa = permintaan.permintaanTum?.mahasiswa
                            Text(text = "Mahasiswa: ${mahasiswa?.nama ?: "Nama tidak ditemukan"}", fontWeight = FontWeight.Bold)
                            Text(text = "NIM: ${mahasiswa?.nIM ?: "NIM tidak ditemukan"}")
                        }
                    }
                }
            }
        }
    }

    // Displaying detailed information in a dialog when a card is clicked
    if (showDetailDialog.value) {
        AlertDialog(
            onDismissRequest = { showDetailDialog.value = false },
            title = { Text("Detail Permintaan Sidang") },
            text = {
                selectedPermintaan.value?.let { permintaan ->
                    val mahasiswa = permintaan.permintaanTum?.mahasiswa
                    Column {
                        mahasiswa?.foto?.let {
                            Image(painter = painterResource(id = R.drawable.logotahub), contentDescription = "Foto Mahasiswa", modifier = Modifier.size(100.dp).clip(CircleShape))
                        }
                        Text(text = "Nama: ${mahasiswa?.nama ?: "Nama tidak ditemukan"}")
                        Text(text = "NIM: ${mahasiswa?.nIM ?: "NIM tidak ditemukan"}")
                        Text(text = "Judul TA: ${permintaan.permintaanTum?.judul ?: "Judul tidak ditemukan"}")

                        permintaan.permintaanTum?.dosenPembimbing?.let { pembimbing ->
                            Text(text = "Pembimbing Satu: ${pembimbing.pembimbingSatu?.nama ?: "Pembimbing Satu tidak ditemukan"}")
                            Text(text = "Pembimbing Dua: ${pembimbing.pembimbingDua?.nama ?: "Pembimbing Dua tidak ditemukan"}")
                        }

                        Spacer(modifier = Modifier.height(16.dp))

                        // Add "Tolak" and "Setuju" buttons in Detail dialog
                        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.Center) {
                            Button(
                                onClick = { showConfirmationDialog.value = true },
                                modifier = Modifier.padding(end = 16.dp)
                            ) {
                                Text(text = "Tolak")
                            }

                            Button(
                                onClick = { showFormDialog.value = true }
                            ) {
                                Text(text = "Setuju")
                            }
                        }
                    }
                }
            },
            confirmButton = {},
            dismissButton = {}
        )
    }

    // Dialog Konfirmasi untuk Tolak
    if (showConfirmationDialog.value) {
        AlertDialog(
            onDismissRequest = { showConfirmationDialog.value = false },
            title = { Text("Konfirmasi") },
            text = { Text("Apakah Anda yakin ingin menolak permintaan sidang ini?") },
            confirmButton = {
                Button(
                    onClick = {
                        selectedPermintaan.value?.id?.let {
                            permintaanSidangViewModel.tolakSidang(it)
                        }
                        showConfirmationDialog.value = false
                        navController.popBackStack()
                        navController.navigate("permintaansidang")
                    }
                ) {
                    Text("Ya")
                }
            },
            dismissButton = {
                Button(onClick = { showConfirmationDialog.value = false }) {
                    Text("Tidak")
                }
            }
        )
    }

    // Dialog untuk form input saat Setuju
    if (showFormDialog.value) {
        AlertDialog(
            onDismissRequest = { showFormDialog.value = false },
            title = { Text("Input Data Sidang") },
            text = {
                Column {
                    TextField(
                        value = waktu,
                        onValueChange = { waktu = it },
                        label = { Text("Waktu (YYYY-MM-DD)") },
                        placeholder = { Text("YYYY-MM-DD") }
                    )
                    TextField(
                        value = tempat,
                        onValueChange = { tempat = it },
                        label = { Text("Tempat") }
                    )

                    Text("Pilih Dosen Penguji 1:")
                    DosenRadioButtons(
                        selectedDosenId = dosenPengujiSatu,
                        dosenList = dosenList,
                        onDosenSelected = { dosenId -> dosenPengujiSatu = dosenId }
                    )

                    Text("Pilih Dosen Penguji 2:")
                    DosenRadioButtons(
                        selectedDosenId = dosenPengujiDua,
                        dosenList = dosenList,
                        onDosenSelected = { dosenId -> dosenPengujiDua = dosenId }
                    )

                    Text("Pilih Dosen Penguji 3:")
                    DosenRadioButtons(
                        selectedDosenId = dosenPengujiTiga,
                        dosenList = dosenList,
                        onDosenSelected = { dosenId -> dosenPengujiTiga = dosenId }
                    )

                    TextField(
                        value = keterangan,
                        onValueChange = { keterangan = it },
                        label = { Text("Keterangan") }
                    )
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        // Status otomatis menjadi "Disetujui"
                        selectedPermintaan.value?.id?.let {
                            dosenPengujiSatu?.let { it1 ->
                                dosenPengujiDua?.let { it2 ->
                                    dosenPengujiTiga?.let { it3 ->
                                        permintaanSidangViewModel.setujuiSidang(
                                            it,
                                            waktu,
                                            tempat,
                                            it1,
                                            it2,
                                            it3,
                                            keterangan
                                        )
                                    }
                                }
                            }
                        }
                        showFormDialog.value = false
                        navController.popBackStack()
                        navController.navigate("permintaansidang") // Refresh halaman setelah disetujui
                    }
                ) {
                    Text("Simpan")
                }
            },
            dismissButton = {
                Button(onClick = { showFormDialog.value = false }) {
                    Text("Batal")
                }
            }
        )
    }
}

data class Dosen(val id: Int, val nama: String)
