package com.drowsynomad.pettersonweatherapp.presentation.screens.home.model

import com.drowsynomad.pettersonweatherapp.core.common.UiState
import com.drowsynomad.pettersonweatherapp.data.models.LocationWeather

/**
 * @author Roman Voloshyn (Created on 25.09.2024)
 */

sealed class HomeState: UiState {
    data class Success(
        val locationWeather: LocationWeather = LocationWeather()
    ): HomeState()
    data object LocationError: HomeState()
    data object Loading: HomeState()
    data object Error: HomeState()

    data object PermissionNotGranted: HomeState()
    data object GpsDisableError: HomeState()
}
