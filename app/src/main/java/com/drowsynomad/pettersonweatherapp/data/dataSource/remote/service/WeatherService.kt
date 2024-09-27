package com.drowsynomad.pettersonweatherapp.data.dataSource.remote.service

import com.drowsynomad.pettersonweatherapp.data.dataSource.remote.Constants
import com.drowsynomad.pettersonweatherapp.data.dataSource.remote.models.response.WeatherResponse
import retrofit2.http.GET
import retrofit2.http.Query

/**
 * @author Roman Voloshyn (Created on 25.09.2024)
 */

interface WeatherService {
    @GET("data/2.5/weather")
    suspend fun fetchWeatherByCityName(
        @Query("q") city: String,
        @Query("units") units: String = Constants.celsius
    ): WeatherResponse?
}