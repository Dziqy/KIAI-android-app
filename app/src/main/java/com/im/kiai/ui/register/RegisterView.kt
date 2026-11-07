package com.im.kiai.ui.register

import com.im.kiai.model.User


interface RegisterView {
    fun registerSuccess()
    fun showMessage(message: String)
}