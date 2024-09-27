package com.drowsynomad.pettersonweatherapp.data.dataSource.remote.models.response

import com.google.gson.annotations.SerializedName

data class SystemInfo(
    @SerializedName("country")
    val country: String?,
    @SerializedName("id")
    val id: Int?,
    @SerializedName("sunrise")
    val sunrise: Int?,
    @SerializedName("sunset")
    val sunset: Int?,
    @SerializedName("type")
    val type: Int?
)