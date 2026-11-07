package com.im.kiai.network.message.response

import com.im.kiai.model.Kajian


data class KajianResponse (
    val success: Boolean,
    val message: String,
    val data: List<Kajian>
)

