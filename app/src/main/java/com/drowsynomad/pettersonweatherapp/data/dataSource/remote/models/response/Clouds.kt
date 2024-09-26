package com.drowsynomad.pettersonweatherapp.data.dataSource.remote.models.response


import com.google.gson.annotations.SerializedName

data class Clouds(
    @SerializedName("all")
    val all: Int?
)