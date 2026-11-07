package com.im.kiai.model

import com.google.gson.annotations.SerializedName


data class User (
    val id: Int,
    val name: String,
    val email: String,
    val phone: String,
    val gender: String,

    @SerializedName("email_verified_at")
    val emailVerifiedAt: String,

    val status: String,

    @SerializedName("created_at")
    val createdAt: String,

    @SerializedName("updated_at")
    val updatedAt: String
)
