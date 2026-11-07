package com.im.kiai.network.message.response


data class AsatidzResponse (
    val success: Boolean,
    val message: String,
    val data: List<Asatidz>
)

data class Asatidz (
    val id: Int,
    val name: String,
    val deskripsi: String?,
    val status: Int?,
    val following: Int?,
    val kajian: Int?
)
