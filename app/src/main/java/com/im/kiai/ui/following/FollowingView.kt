package com.im.kiai.ui.following

import com.im.kiai.network.message.response.Asatidz

interface FollowingView {
    fun showMessage(message: String)
    fun showAsatidz(list: List<Asatidz>)
}