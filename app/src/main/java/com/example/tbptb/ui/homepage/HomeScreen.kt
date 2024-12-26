package com.example.tbptb.ui.homepage

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tbptb.R
import com.example.tbptb.data.local.UserPreferences
import com.example.tbptb.data.retrofit.ApiConfig
import com.example.tbptb.ui.ViewModelFactory

@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    homeViewModel: HomeViewModel = viewModel(factory = ViewModelFactory(ApiConfig.apiService, UserPreferences.getInstance(LocalContext.current)))
) {
    // Mengambil informasi pengguna dari HomeViewModel
    val userInfo by homeViewModel.userInfo.collectAsState()
    val isLoading by homeViewModel.isLoading.collectAsState()

    // Column layout to align greeting and sections
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Greeting Section
        Row(
            modifier = Modifier.fillMaxWidth(),
            horizontalArrangement = Arrangement.SpaceBetween,
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column {
                Text(
                    text = userInfo,
                    style = MaterialTheme.typography.titleLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color(0xFFFFA500) // Orange greeting
                )
                Text(
                    text = "Bagaimana kabar anda?",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color.Gray
                )
            }
            Image(
                painter = painterResource(id = R.drawable.logotahub), // Add your profile image here
                contentDescription = "Profile",
                modifier = Modifier
                    .size(50.dp)
                    .clip(CircleShape)
                    .border(1.dp, Color.Gray, CircleShape)
            )
        }

        Spacer(modifier = Modifier.height(20.dp))

        // Sections (Sidang and Seminar)
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxWidth()
        ) {
            // Sidang Section
            Text(
                text = "Sidang",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFA500) // Sidang section title color
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                // Jadwal Card
                Card(
                    modifier = Modifier
                        .size(120.dp)
                        .padding(8.dp)
                        .clickable {
                            navController.navigate("jadwalsidang")  // Navigate to JadwalSidangScreen
                        },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF0D6))
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.logotahub), // Add your icon here
                            contentDescription = "Jadwal",
                            tint = Color(0xFFFFA500),
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            text = "Jadwal",
                            style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFFFFA500))
                        )
                    }
                }

                // Permintaan Card
                Card(
                    modifier = Modifier
                        .size(120.dp)
                        .padding(8.dp)
                        .clickable {
                            // Navigate to PermintaanSidangScreen
                            navController.navigate("permintaansidang")  // Navigate to the Permintaan Sidang screen
                        },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF0D6))
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.logotahub), // Add your icon here
                            contentDescription = "Permintaan",
                            tint = Color(0xFFFFA500),
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            text = "Permintaan",
                            style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFFFFA500))
                        )
                    }
                }
            }

            Spacer(modifier = Modifier.height(20.dp))

            // Seminar Section
            Text(
                text = "Seminar",
                style = MaterialTheme.typography.titleMedium,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFFFA500) // Seminar section title color
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center
            ) {
                // Jadwal Card
                Card(
                    modifier = Modifier
                        .size(120.dp)
                        .padding(8.dp)
                        .clickable { /* Handle Jadwal click */ },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF0D6))
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.logotahub), // Add your icon here
                            contentDescription = "Jadwal",
                            tint = Color(0xFFFFA500),
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            text = "Jadwal",
                            style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFFFFA500))
                        )
                    }
                }

                // Permintaan Card
                Card(
                    modifier = Modifier
                        .size(120.dp)
                        .padding(8.dp)
                        .clickable { /* Handle Permintaan click */ },
                    colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF0D6))
                ) {
                    Column(
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center,
                        modifier = Modifier.fillMaxSize()
                    ) {
                        Icon(
                            painter = painterResource(id = R.drawable.logotahub), // Add your icon here
                            contentDescription = "Permintaan",
                            tint = Color(0xFFFFA500),
                            modifier = Modifier.size(40.dp)
                        )
                        Text(
                            text = "Permintaan",
                            style = MaterialTheme.typography.bodySmall.copy(color = Color(0xFFFFA500))
                        )
                    }
                }
            }
        }
    }
}


