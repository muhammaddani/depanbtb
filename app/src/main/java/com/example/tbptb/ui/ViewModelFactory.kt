package com.example.tbptb.ui

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.example.tbptb.data.retrofit.ApiService
import com.example.tbptb.data.local.UserPreferences
import com.example.tbptb.ui.homepage.HomeViewModel
import com.example.tbptb.ui.jadwalsidang.JadwalSidangViewModel
import com.example.tbptb.ui.login.LoginViewModel
import com.example.tbptb.ui.permintaansidang.PermintaanSidangViewModel
import com.example.tbptb.ui.profile.ProfileViewModel

class ViewModelFactory(
    private val apiService: ApiService,
    private val userPreferences: UserPreferences
) : ViewModelProvider.Factory {

    @Suppress("UNCHECKED_CAST")
    override fun <T : ViewModel> create(modelClass: Class<T>): T {
        return when {
            modelClass.isAssignableFrom(HomeViewModel::class.java) -> {
                HomeViewModel(apiService, userPreferences) as T
            }
            modelClass.isAssignableFrom(ProfileViewModel::class.java) -> {
                ProfileViewModel(userPreferences) as T
            }
            modelClass.isAssignableFrom(LoginViewModel::class.java) -> {
                LoginViewModel(apiService, userPreferences) as T
            }
            modelClass.isAssignableFrom(JadwalSidangViewModel::class.java) -> {
                JadwalSidangViewModel(apiService) as T
            }
            modelClass.isAssignableFrom(PermintaanSidangViewModel::class.java) -> {
                PermintaanSidangViewModel(apiService) as T
            }
            else -> throw IllegalArgumentException("Unknown ViewModel class: ${modelClass.name}")
        }
    }
}



