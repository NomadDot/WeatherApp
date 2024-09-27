package com.drowsynomad.pettersonweatherapp.data.dataSource.remote.models.response

import com.google.gson.annotations.SerializedName

data class WeatherResponse(
    @SerializedName("id")
    val id: Int?, @SerializedName("cod")
    val statusCode: Int?,
    @SerializedName("dt")
    val dateTime: Int?,
    @SerializedName("main")
    val main: Main?,
    @SerializedName("name")
    val cityName: String?,
    @SerializedName("sys")
    val cityInfo: SystemInfo?,
    @SerializedName("weather")
    val weather: List<Weather?>?,
)