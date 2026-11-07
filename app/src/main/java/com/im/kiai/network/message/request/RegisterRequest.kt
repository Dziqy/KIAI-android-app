package com.im.kiai.network.message.request

data class RegisterRequest (
    var id: Int?,
    val name: String,
    val gender: String,
    val phone: String,
    val email: String,
    val password: String
)
