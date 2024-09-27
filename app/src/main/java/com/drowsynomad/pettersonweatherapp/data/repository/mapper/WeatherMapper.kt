package com.drowsynomad.pettersonweatherapp.data.repository.mapper

import com.drowsynomad.pettersonweatherapp.data.dataSource.local.entities.LocationEntity
import com.drowsynomad.pettersonweatherapp.data.dataSource.local.entities.WeatherEntity
import com.drowsynomad.pettersonweatherapp.data.dataSource.local.model.LocationAndWeather
import com.drowsynomad.pettersonweatherapp.data.dataSource.remote.models.response.WeatherResponse
import com.drowsynomad.pettersonweatherapp.data.models.LocationWeather
import com.drowsynomad.pettersonweatherapp.utils.clearTemperature
import com.drowsynomad.pettersonweatherapp.utils.round
import kotlin.random.Random

/**
 * @author Roman Voloshyn (Created on 25.09.2024)
 */

object WeatherModelMapper {
    fun WeatherResponse.toWeatherMainData(): LocationWeather =
        LocationWeather(
            city = cityName ?: "",
            currentTemp = "${main?.temp}".round(),
            minTemp = "${main?.tempMin}".round(),
            maxTemp = "${main?.tempMax}".round(),
            feelsLikeTemp = "${main?.feelsLike}".round(),
            weatherTitle = weather?.firstOrNull()?.main ?: "",
            icon = weather?.firstOrNull()?.icon ?: "",
            isActualData = true
        )

    fun LocationAndWeather.toWeatherMainData(): LocationWeather =
        LocationWeather(
            city = this.location.place,
            currentTemp = "${weather.temperature.current}".round(),
            minTemp = "${weather.temperature.min}".round(),
            maxTemp = "${weather.temperature.max}".round(),
            feelsLikeTemp = "${weather.temperature.feelsLike}".round(),
            weatherTitle = this.weather.info.title,
            icon = this.weather.info.icon,
            isActualData = false
        )

    fun LocationWeather.toEntity(): LocationAndWeather {
        return LocationAndWeather(
            LocationEntity(
                id = this.city.hashCode(),
                place = this.city
            ),
            WeatherEntity(
                id = Random.nextInt(),
                placeId = this.city.hashCode(),
                temperature = WeatherEntity.Temperature(
                    current = this.currentTemp.clearTemperature().toDouble(),
                    min = this.minTemp.clearTemperature().toDouble(),
                    max = this.maxTemp.clearTemperature().toDouble(),
                    feelsLike = this.feelsLikeTemp.clearTemperature().toDouble()
                ),
                info = WeatherEntity.Weather(
                    title = this.weatherTitle,
                    icon = this.icon
                )
            )
        )
    }
}