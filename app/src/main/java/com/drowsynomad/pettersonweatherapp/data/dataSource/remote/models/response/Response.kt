package com.drowsynomad.pettersonweatherapp.data.dataSource.remote.models.response

import com.google.gson.annotations.SerializedName

/**
 * @author Roman Voloshyn (Created on 25.09.2024)
 */

data class Response<T>(
    @SerializedName("")
    val data: T?
) {
    val isEmpty = data == null
}

