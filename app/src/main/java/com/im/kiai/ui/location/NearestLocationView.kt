package com.im.kiai.ui.location

import com.im.kiai.model.Kajian

interface NearestLocationView {
    fun showMessage(message: String)
    fun showKajian(list: List<Kajian>)
}