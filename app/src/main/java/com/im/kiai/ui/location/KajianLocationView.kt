package com.im.kiai.ui.location

import com.im.kiai.network.message.response.Locations

interface KajianLocationView {
    fun showMessage(message: String)
    fun showLocation(list: List<Locations>)
}