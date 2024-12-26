package com.example.tbptb.ui

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.*
import com.example.tbptb.ui.login.LoginScreen
import com.example.tbptb.data.local.UserPreferences
import com.example.tbptb.ui.homepage.HomeScreen
import com.example.tbptb.ui.jadwalsidang.JadwalSidangScreen
//import com.example.tbptb.ui.permintaansidang.PermintaanSidangDetailScreen
import com.example.tbptb.ui.permintaansidang.PermintaanSidangScreen
import com.example.tbptb.ui.profile.ProfileScreen
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun MyAppNavigation(
    modifier: Modifier = Modifier,
    isUserLoggedIn: Flow<Boolean> = flowOf(false),
    userPreferences: UserPreferences
) {
    val navController = rememberNavController()

    // Tentukan status login pengguna
    val isUserLoggedInState = userPreferences.getToken() != null

    // Memastikan BottomBar hanya tampil setelah login
    Scaffold(
        bottomBar = {
            if (isUserLoggedInState) {
                BottomBar(navController = navController)
            }
        }
    ) { innerPadding ->
        LaunchedEffect(isUserLoggedInState) {
            // Hanya set startDestination ke home jika user sudah login
            if (isUserLoggedInState) {
                navController.navigate("home")
            } else {
                navController.navigate("login")
            }
        }

        NavHost(
            navController = navController,
            startDestination = if (isUserLoggedInState) "home" else "login",
            modifier = Modifier.padding(innerPadding)
        ) {
            composable("login") {
                LoginScreen(modifier = modifier, navController = navController)
            }

            composable("home") {
                HomeScreen(modifier = modifier, navController = navController)
            }

            composable("profile") {
                ProfileScreen(modifier = modifier, navController = navController)
            }

            composable("jadwalsidang") {
                JadwalSidangScreen(modifier = modifier, navController = navController)
            }

            composable("permintaansidang") {
                PermintaanSidangScreen(modifier = modifier, navController = navController)
            }
        }
    }
}


