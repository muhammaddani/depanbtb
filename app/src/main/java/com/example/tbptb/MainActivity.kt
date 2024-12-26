package com.example.tbptb

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import com.example.tbptb.data.local.UserPreferences
import com.example.tbptb.ui.MyAppNavigation
import com.example.tbptb.ui.theme.TBPTBTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {

    // Inisialisasi UserPreferences
    private lateinit var userPreferences: UserPreferences

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        // Menginisialisasi UserPreferences
        userPreferences = UserPreferences.getInstance(applicationContext)

        setContent {
            TBPTBTheme {
                // A surface container using the 'background' color from the theme
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MyAppNavigation(
                        // Menggunakan UserPreferences untuk memeriksa apakah pengguna sudah login
                        isUserLoggedIn = userLoggedInState(),
                        userPreferences = userPreferences
                    )
                }
            }
        }
    }

    // Mengambil status login berdasarkan apakah token ada di DataStore
    private fun userLoggedInState(): Flow<Boolean> {
        return userPreferences.isUserLoggedIn()
    }
}
