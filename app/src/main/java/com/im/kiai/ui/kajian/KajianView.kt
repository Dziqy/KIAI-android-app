package com.im.lapor.ui.headofrt.report

import com.im.kiai.model.Kajian

interface KajianView {
    fun showMessage(message: String)
    fun showKajian(list: List<Kajian>)
}