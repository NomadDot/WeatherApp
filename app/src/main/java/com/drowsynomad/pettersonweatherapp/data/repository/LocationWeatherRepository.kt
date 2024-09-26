package com.drowsynomad.pettersonweatherapp.data.repository

import com.drowsynomad.pettersonweatherapp.core.common.Dispatcher
import com.drowsynomad.pettersonweatherapp.data.dataSource.local.dao.WeatherDao
import com.drowsynomad.pettersonweatherapp.data.dataSource.local.entities.LocationEntity
import com.drowsynomad.pettersonweatherapp.data.dataSource.local.errors.LocalServiceException
import com.drowsynomad.pettersonweatherapp.data.dataSource.remote.models.errors.RemoteServiceException
import com.drowsynomad.pettersonweatherapp.data.dataSource.remote.service.WeatherService
import com.drowsynomad.pettersonweatherapp.data.models.Location
import com.drowsynomad.pettersonweatherapp.data.models.LocationWeather
import com.drowsynomad.pettersonweatherapp.data.repository.mapper.WeatherModelMapper.toEntity
import com.drowsynomad.pettersonweatherapp.data.repository.mapper.WeatherModelMapper.toWeatherMainData
import com.drowsynomad.pettersonweatherapp.data.repository.mapper.toLocation
import kotlinx.coroutines.withContext

/**
 * @author Roman Voloshyn (Created on 25.09.2024)
 */

interface ILocationWeatherRepository {
    suspend fun loadWeatherByPlace(
        isNetworkEnabled: Boolean,
        autoSave: Boolean = true,
        place: String
    ): LocationWeather

    suspend fun loadLocations(): List<Location>

    suspend fun removeLocation(weatherData: LocationWeather)
    suspend fun saveLocation(weatherData: LocationWeather)
}

class LocationWeatherRepository(
    private val dispatcher: Dispatcher.IO,
    private val weatherService: WeatherService,
    private val weatherDao: WeatherDao,
) : ILocationWeatherRepository {

    override suspend fun loadWeatherByPlace(
        isNetworkEnabled: Boolean,
        autoSave: Boolean,
        place: String,
    ): LocationWeather  =
        withContext(dispatcher.pool) {
            if (isNetworkEnabled) {
                val remoteData = weatherService.fetchWeatherByCityName(place)
                val weatherData = remoteData?.toWeatherMainData() ?: throw RemoteServiceException()

                if(autoSave)
                    saveWeatherLocally(weatherData)

                weatherData
            } else {
                val localData = weatherDao.loadWeatherForPlace(place)
                localData?.toWeatherMainData() ?: throw LocalServiceException()
            }
        }

    override suspend fun loadLocations(): List<Location> =
        withContext(dispatcher.pool) {
            weatherDao.loadSavedLocations().map(LocationEntity::toLocation)
        }

    override suspend fun removeLocation(weatherData: LocationWeather) =
        withContext(dispatcher.pool) {
            weatherDao.deleteLocation(weatherData.city)
        }

    override suspend fun saveLocation(weatherData: LocationWeather) =
        withContext(dispatcher.pool) {
            saveWeatherLocally(weatherData)
        }

    private suspend fun saveWeatherLocally(weatherData: LocationWeather) {
        val locationAndWeather = weatherData.toEntity()
        weatherDao.saveLocation(locationAndWeather.location)
        weatherDao.saveWeather(locationAndWeather.weather)
    }
}