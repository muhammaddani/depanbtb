package com.example.tbptb.ui.profile

import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
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
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import com.example.tbptb.R
import com.example.tbptb.data.local.UserPreferences
import com.example.tbptb.data.retrofit.ApiConfig
import com.example.tbptb.ui.ViewModelFactory

@Composable
fun ProfileScreen(
    modifier: Modifier = Modifier,
    navController: NavController,
    profileViewModel: ProfileViewModel = viewModel(factory = ViewModelFactory(ApiConfig.apiService, UserPreferences.getInstance(LocalContext.current)))
) {
    val username by profileViewModel.username.collectAsState()
    val email by profileViewModel.email.collectAsState()

    // Memuat data pengguna saat pertama kali dimuat
    LaunchedEffect(Unit) {
        profileViewModel.loadUserData()
    }

    val context = LocalContext.current

    // Fungsi logout di ProfileScreen
    fun logout() {
        profileViewModel.logout()
        Toast.makeText(context, "Anda telah keluar.", Toast.LENGTH_SHORT).show()

        // Navigasi kembali ke halaman login setelah logout
        navController.navigate("login") {
            popUpTo("login") { inclusive = true }
        }
    }

    // Column layout to align profile information
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(20.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // Profile Header
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(20.dp)
                .clip(shape = CircleShape)
                .background(Color(0xFFFFE1A8)) // Light Yellow background
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                // Profile Image (Placeholder for now, replace with actual image resource)
                Image(
                    painter = painterResource(id = R.drawable.logotahub),  // Add your profile image here
                    contentDescription = "Profile Image",
                    modifier = Modifier
                        .size(100.dp)
                        .clip(CircleShape)
                        .border(2.dp, Color.Gray, CircleShape)
                )
                Spacer(modifier = Modifier.height(8.dp))

                // Username and Email display
                Text(
                    text = username,
                    style = MaterialTheme.typography.headlineLarge.copy(fontWeight = FontWeight.Bold),
                    color = Color.Black
                )
                Text(
                    text = email,
                    style = MaterialTheme.typography.bodyLarge,
                    color = Color.Gray
                )
            }
        }

        Spacer(modifier = Modifier.height(40.dp))

        // Menu options (e.g., Edit Profile, Change Password)
        Column(
            modifier = Modifier.fillMaxWidth()
        ) {
            // "Ubah Profil" option
            ProfileOption(title = "Ubah Profil", onClick = { /* Handle Ubah Profil click */ })

            // "Ganti Kata Sandi" option
            ProfileOption(title = "Ganti Kata Sandi", onClick = { /* Handle Ganti Kata Sandi click */ })

            Spacer(modifier = Modifier.height(16.dp))

            // "Keluar" option
            ProfileOption(
                title = "Keluar",
                onClick = {
                    logout()
                },
                textColor = Color.Red // Color red for logout option
            )
        }
    }
}

