package com.example.tbptb.data.response

import com.google.gson.annotations.SerializedName
import java.util.Date

// Request body untuk permintaan sidang
data class PermintaanSidangRequest(
    @SerializedName("status")
    val status: String, // Status sidang (disetujui/ditolak)

    @SerializedName("waktu")
    val waktu: Date?,  // Waktu sidang

    @SerializedName("tempat")
    val tempat: String?, // Tempat sidang

    @SerializedName("penguji_satu_id")
    val pengujiSatuId: Int?, // ID penguji 1 (bisa null)

    @SerializedName("penguji_dua_id")
    val pengujiDuaId: Int?, // ID penguji 2 (bisa null)

    @SerializedName("penguji_tiga_id")
    val pengujiTigaId: Int?, // ID penguji 3 (bisa null)

    @SerializedName("keterangan")
    val keterangan: String?
)