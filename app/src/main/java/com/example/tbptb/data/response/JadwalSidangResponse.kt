package com.example.tbptb.data.response

import com.google.gson.annotations.SerializedName
import java.util.Date

// Model Data (JadwalSidangResponse.kt)
data class JadwalSidangResponse(
	// Menyesuaikan dengan array JSON
	@SerializedName("jadwalSidangResponse")
	val jadwalSidangResponse: List<JadwalSidangResponseItem>?
)

data class JadwalSidangResponseItem(
	@SerializedName("id")
	val id: Int?,

	@SerializedName("permintaan_ta_id")
	val permintaanTaId: Int?,

	@SerializedName("waktu")
	val waktu: String?,  // Menggunakan String untuk menangani format waktu dengan fleksibel

	@SerializedName("tempat")
	val tempat: String?,

	@SerializedName("dosen_penguji_id")
	val dosenPengujiId: Int?,

	@SerializedName("status")
	val status: String?,

	@SerializedName("keterangan")
	val keterangan: String?,

	@SerializedName("dokumen")
	val dokumen: String?,

	@SerializedName("PermintaanTum") // Menambahkan objek PermintaanTum sesuai dengan data JSON
	val permintaanTum: PermintaanTum?
)

data class PermintaanTum1(
	@SerializedName("id")
	val id: Int?,

	@SerializedName("mahasiswa_id")
	val mahasiswaId: Int?,

	@SerializedName("judul")
	val judul: String?,

	@SerializedName("deskripsi_singkat")
	val deskripsiSingkat: String?,

	@SerializedName("status")
	val status: String?,

	@SerializedName("keterangan")
	val keterangan: String?,

	@SerializedName("dosen_pembimbing_id")
	val dosenPembimbingId: Int?,

	@SerializedName("Mahasiswa")
	val mahasiswa: Mahasiswa?
)

data class Mahasiswa1(
	@SerializedName("id")
	val id: Int?,

	@SerializedName("NIM")
	val nim: String?,

	@SerializedName("nama")
	val nama: String?,

	@SerializedName("nohp")
	val nohp: String?,

	@SerializedName("foto")
	val foto: String?
)

data class Dosen(
	@SerializedName("id")
	val id: Int, // ID dosen

	@SerializedName("nama")
	val nama: String // Nama dosen
)

data class Peserta(
	val nama: String,
	val nim: String
)

