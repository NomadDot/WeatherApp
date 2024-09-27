package com.drowsynomad.pettersonweatherapp.data.dataSource.remote.models.response


import com.google.gson.annotations.SerializedName

data class Main(
    @SerializedName("feels_like")
    val feelsLike: Double?,
    @SerializedName("temp")
    val temp: Double?,
    @SerializedName("temp_max")
    val tempMax: Double?,
    @SerializedName("temp_min")
    val tempMin: Double?
)