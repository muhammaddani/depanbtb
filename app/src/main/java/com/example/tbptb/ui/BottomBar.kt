package com.example.tbptb.ui

import androidx.compose.foundation.layout.size
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.tbptb.R

data class BottomBarItem(
    val title: String,
    val iconRes: Int,
    val route: String // Menambahkan route untuk navigasi
)

@Composable
fun BottomBar(navController: NavHostController) {
    // Mendapatkan rute yang aktif saat ini
    val currentRoute = navController.currentBackStackEntryAsState().value?.destination?.route

    // Daftar item bottom bar dengan icon dan route-nya
    val bottomBarItems = listOf(
        BottomBarItem("Home", R.drawable.logotahub, "home"),
        BottomBarItem("Permintaan TA", R.drawable.logotahub, "permintaanta"),
        BottomBarItem("Mahasiswa TA", R.drawable.logotahub, "mahasiswata"),
        BottomBarItem("Profile", R.drawable.logotahub, "profile")
    )

    // Membuat BottomNavigation dengan item-item
    NavigationBar(
        containerColor = Color.White // Ubah warna latar belakang jika diperlukan
    ) {
        // Looping untuk menampilkan item
        bottomBarItems.forEachIndexed { _, item ->
            NavigationBarItem(
                selected = currentRoute == item.route, // Menentukan apakah item ini dipilih
                onClick = {
                    navController.navigate(item.route) {
                        // Pastikan rute sebelumnya tidak tumpang tindih
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.iconRes),
                        contentDescription = item.title,
                        modifier = Modifier.size(24.dp),
                        tint = if (currentRoute == item.route) Color.Red else Color.Unspecified // Mengubah warna jika dipilih
                    )
                },
                label = { Text(item.title) } // Label untuk item navigasi
            )
        }
    }
}
