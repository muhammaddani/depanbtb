package com.example.tbptb.data.retrofit

import com.example.tbptb.data.response.JadwalSidangResponseItem
import com.example.tbptb.data.response.LoginRequest
import com.example.tbptb.data.response.LoginResponse
import com.example.tbptb.data.response.PermintaanSidangResponse
import com.example.tbptb.data.response.PermintaanSidangResponseItem
import com.example.tbptb.ui.permintaansidang.PermintaanSidangViewModel
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.PUT
import retrofit2.http.Path

interface ApiService {
    @POST("users/login")
    suspend fun login(
        @Body loginRequest: LoginRequest
    ): LoginResponse

    @GET("sidang/jadwal")
    suspend fun getJadwalSidang(): Response<List<JadwalSidangResponseItem>>

    // Endpoint untuk mendapatkan daftar permintaan sidang
    @GET("sidang/requests")
    suspend fun getPermintaanSidang(): Response<List<PermintaanSidangResponseItem>>

    // Menyetujui atau menolak permintaan sidang
    @POST("sidang/{id}/approve-reject")
    suspend fun approveOrRejectSidang(
        @Path("id") id: Int,
        @Body requestBody: PermintaanSidangViewModel.SidangRequestBody
    ): Response<String>

}
