package com.drowsynomad.pettersonweatherapp.presentation.screens.detailedLocation.model

import com.drowsynomad.pettersonweatherapp.core.common.UiState
import com.drowsynomad.pettersonweatherapp.data.models.LocationWeather

/**
 * @author Roman Voloshyn (Created on 26.09.2024)
 */

sealed class DetailedLocationState: UiState {
    data object Loading: DetailedLocationState()
    data class Success(
        val locationWeather: LocationWeather,
        val isLocationSaved: Boolean
    ): DetailedLocationState()
    data class ActionButtonChanged(
        val isLocationSaved: Boolean,
        val locationWeather: LocationWeather
    ): DetailedLocationState()

    data object Error: DetailedLocationState()
}