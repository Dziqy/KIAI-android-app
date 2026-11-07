package com.im.kiai.network.message.response

import com.im.kiai.model.User

data class LoginResponse (
    val success: Boolean,
    val message: String,
    val data: User?
)