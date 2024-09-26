package com.drowsynomad.pettersonweatherapp.data.repository.mapper

import com.drowsynomad.pettersonweatherapp.data.dataSource.local.entities.LocationEntity
import com.drowsynomad.pettersonweatherapp.data.dataSource.local.entities.WeatherEntity
import com.drowsynomad.pettersonweatherapp.data.dataSource.local.model.LocationAndWeather
import com.drowsynomad.pettersonweatherapp.data.dataSource.remote.models.response.WeatherResponse
import com.drowsynomad.pettersonweatherapp.data.models.LocationWeather

/**
 * @author Roman Voloshyn (Created on 25.09.2024)
 */

object WeatherModelMapper {
    fun WeatherResponse.toWeatherMainData(): LocationWeather =
        LocationWeather(
            city = name ?: "",
            currentTemp = "${main?.temp}",
            minTemp = "${main?.tempMin}",
            maxTemp = "${main?.tempMax}",
            weatherTitle = ""
        )

    fun LocationAndWeather.toWeatherMainData(): LocationWeather =
        LocationWeather(
            city = this.location.place,
            currentTemp = "${weather.temperature.current}",
            minTemp = "${weather.temperature.min}",
            maxTemp = "${weather.temperature.max}",
            weatherTitle = "",
            isActualData = false
        )

    fun LocationWeather.toEntity(): LocationAndWeather {
        return LocationAndWeather(
            LocationEntity(
                id = this.city.hashCode(),
                place = this.city
            ),
            WeatherEntity(
                id = 0,
                placeId = this.city.hashCode(),
                temperature = WeatherEntity.Temperature(
                    current = this.currentTemp.toDouble(),
                    min = this.minTemp.toDouble(),
                    max =this.maxTemp.toDouble()
                )
            )
        )
    }
}