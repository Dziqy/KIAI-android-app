package com.im.kiai.ui.home

import com.im.kiai.model.Kajian


interface MainView {
    fun setLatestKajian(data: List<Kajian>)
    fun showMessage(message: String)
}