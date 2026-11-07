package com.im.kiai.ui.astidz

import com.im.kiai.network.message.response.Asatidz

interface AsatidzView {
    fun showMessage(message: String)
    fun showAsatidz(list: List<Asatidz>)
}