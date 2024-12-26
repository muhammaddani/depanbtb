package com.example.tbptb.data.response

import com.google.gson.annotations.SerializedName

data class PermintaanSidangResponse(
	@SerializedName("permintaanSidangResponse")
	val permintaanSidangResponse: List<PermintaanSidangResponseItem>?
)

data class Mahasiswa(

	@field:SerializedName("NIM")
	val nIM: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("foto")
	val foto: Any? = null,

	@field:SerializedName("nohp")
	val nohp: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class DosenPembimbing(

	@field:SerializedName("PembimbingSatu")
	val pembimbingSatu: PembimbingSatu? = null,

	@field:SerializedName("PembimbingDua")
	val pembimbingDua: PembimbingDua? = null,

	@field:SerializedName("pembimbing_dua_id")
	val pembimbingDuaId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("pembimbing_satu_id")
	val pembimbingSatuId: Int? = null
)

data class PermintaanSidangResponseItem(

	@field:SerializedName("keterangan")
	val keterangan: Any? = null,

	@field:SerializedName("tempat")
	val tempat: Any? = null,

	@field:SerializedName("dosen_penguji_id")
	val dosenPengujiId: Any? = null,

	@field:SerializedName("PermintaanTum")
	val permintaanTum: PermintaanTum? = null,

	@field:SerializedName("waktu")
	val waktu: Any? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("permintaan_ta_id")
	val permintaanTaId: Int? = null,

	@field:SerializedName("status")
	val status: Any? = null
)

data class PermintaanTum(

	@field:SerializedName("deskripsi_singkat")
	val deskripsiSingkat: String? = null,

	@field:SerializedName("Mahasiswa")
	val mahasiswa: Mahasiswa? = null,

	@field:SerializedName("keterangan")
	val keterangan: String? = null,

	@field:SerializedName("dokumen")
	val dokumen: Any? = null,

	@field:SerializedName("DosenPembimbing")
	val dosenPembimbing: DosenPembimbing? = null,

	@field:SerializedName("dosen_pembimbing_id")
	val dosenPembimbingId: Int? = null,

	@field:SerializedName("id")
	val id: Int? = null,

	@field:SerializedName("mahasiswa_id")
	val mahasiswaId: Int? = null,

	@field:SerializedName("judul")
	val judul: String? = null,

	@field:SerializedName("status")
	val status: String? = null
)

data class PembimbingSatu(

	@field:SerializedName("NIP")
	val nIP: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("foto")
	val foto: Any? = null,

	@field:SerializedName("nohp")
	val nohp: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class PembimbingDua(

	@field:SerializedName("NIP")
	val nIP: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("foto")
	val foto: Any? = null,

	@field:SerializedName("nohp")
	val nohp: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class PengujiSatu(

	@field:SerializedName("NIP")
	val nIP: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("foto")
	val foto: Any? = null,

	@field:SerializedName("nohp")
	val nohp: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class PengujiDua(

	@field:SerializedName("NIP")
	val nIP: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("foto")
	val foto: Any? = null,

	@field:SerializedName("nohp")
	val nohp: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)

data class PengujiTiga(

	@field:SerializedName("NIP")
	val nIP: String? = null,

	@field:SerializedName("nama")
	val nama: String? = null,

	@field:SerializedName("foto")
	val foto: Any? = null,

	@field:SerializedName("nohp")
	val nohp: String? = null,

	@field:SerializedName("id")
	val id: Int? = null
)
