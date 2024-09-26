package com.drowsynomad.pettersonweatherapp.presentation.screens.detailedLocation

import com.drowsynomad.pettersonweatherapp.core.base.BaseViewModel
import com.drowsynomad.pettersonweatherapp.data.models.LocationWeather
import com.drowsynomad.pettersonweatherapp.data.repository.ILocationWeatherRepository
import com.drowsynomad.pettersonweatherapp.presentation.screens.detailedLocation.model.DetailedLocationEvent
import com.drowsynomad.pettersonweatherapp.presentation.screens.detailedLocation.model.DetailedLocationState
import com.drowsynomad.pettersonweatherapp.presentation.screens.detailedLocation.model.DetailedLocationState.Loading
import com.drowsynomad.pettersonweatherapp.utils.network.IConnectionStatusProvider

/**
 * @author Roman Voloshyn (Created on 26.09.2024)
 */

class DetailedLocationViewModel(
    private val networkStatusProvider: IConnectionStatusProvider,
    private val weatherRepository: ILocationWeatherRepository
): BaseViewModel<DetailedLocationState, DetailedLocationEvent>(
    initialState = Loading
) {
    override fun handleEvent(uiEvent: DetailedLocationEvent) {
        when(uiEvent) {
            is DetailedLocationEvent.LoadWeather -> loadLocationWeather(uiEvent.place)
            is DetailedLocationEvent.RemoveLocation -> removeLocation(uiEvent.locationWeather)
            is DetailedLocationEvent.SaveLocation -> saveLocation(uiEvent.locationWeather)
        }
    }

    private fun loadLocationWeather(place: String) {
        launch(
            action = {
                val network = networkStatusProvider.isNetworkEnabled()
                val data = weatherRepository
                    .loadWeatherByPlace(
                        isNetworkEnabled = network,
                        autoSave = false,
                        place = place
                    )

                updateState {
                    DetailedLocationState.Success(
                        locationWeather = with(data) {
                            copy(
                                city = city,
                                currentTemp = "${currentTemp}°",
                                minTemp = "${minTemp}°",
                                maxTemp = "${maxTemp}°",
                            )
                        },
                        isLocationSaved = data.isActualData
                    )
                }
            })
    }

    private fun removeLocation(locationWeather: LocationWeather) =
        launch {
            weatherRepository.removeLocation(locationWeather)
            updateState {
                DetailedLocationState.ActionButtonChanged(false, locationWeather)
            }
        }

    private fun saveLocation(locationWeather: LocationWeather) =
        launch {
            weatherRepository.saveLocation(locationWeather)
            updateState {
                DetailedLocationState.ActionButtonChanged(true, locationWeather)
            }
        }
}