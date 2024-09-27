package com.drowsynomad.pettersonweatherapp.data.models

/**
 * @author Roman Voloshyn (Created on 25.09.2024)
 */

data class LocationWeather(
    val id: Int = 0,
    val city: String = "",
    val currentTemp: String = "",
    val minTemp: String = "",
    val maxTemp: String = "",
    val feelsLikeTemp: String = "",
    val weatherTitle: String = "",
    val icon: String = "",
    val isActualData: Boolean = true
)

data class Location(
    val name: String
)