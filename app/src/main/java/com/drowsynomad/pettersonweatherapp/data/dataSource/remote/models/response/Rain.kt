package com.drowsynomad.pettersonweatherapp.data.dataSource.remote.models.response


import com.google.gson.annotations.SerializedName

data class Rain(
    @SerializedName("1h")
    val h: Double?
)