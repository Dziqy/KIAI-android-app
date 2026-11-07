package com.im.kiai.ui.login

import com.im.kiai.model.User


interface LoginView {
    fun setData(data: User)
    fun showMessage(message: String)
}