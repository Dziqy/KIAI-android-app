package com.im.kiai.model

import android.os.Parcelable
import com.google.gson.annotations.SerializedName
import kotlinx.android.parcel.Parcelize

@Parcelize
data class Kajian (
    @SerializedName("user_id")
    val userID: Int,

    val judul: String,
    val asatidz: String?,
    val poster: String?,
    val deskripsi: String? = null,
    val streaming: String? = null,
    val tanggal: String,
    val waktu: String,
    val jenis: String,
    val jamaah: String?,
    val status: String,

    @SerializedName("created_at")
    val createdAt: String,

    val nama: String,
    val provinsi: String,
    val kabupaten: String,
    val kecamatan: String,
    val desa: String,
    val alamat: String,
    val latitude: String,
    val longitude: String,

    @SerializedName("kajian_id")
    val kajianID: Int,
    val distance: Double?
): Parcelable