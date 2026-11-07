package com.im.kiai.network.message.response

import com.google.gson.annotations.SerializedName

data class LocationResponse (
    val success: Boolean,
    val message: String,
    val data: List<Locations>
)

data class Locations (
    val id: Int,

    @SerializedName("user_id")
    val userID: Long,

    val nama: String,
    val jenis: String,
    val provinsi: String,
    val kabupaten: String,
    val kecamatan: String,
    val desa: String,
    val alamat: String,
    val latitude: String,
    val longitude: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String,

    @SerializedName("regency_id")
    val regencyID: String,

    val name: String,

    @SerializedName("province_id")
    val provinceID: String,

    val distance: String?
)
