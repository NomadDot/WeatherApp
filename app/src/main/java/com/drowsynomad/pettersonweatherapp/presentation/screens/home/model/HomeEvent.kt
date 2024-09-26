package com.drowsynomad.pettersonweatherapp.presentation.screens.home.model

import com.drowsynomad.pettersonweatherapp.core.common.UiEvent

/**
 * @author Roman Voloshyn (Created on 25.09.2024)
 */

sealed class HomeEvent: UiEvent {
    data class LoadCurrentWeather(val place: String): HomeEvent()
    data class FindLocationWeather(val place: String): HomeEvent()

    data object CantGetLocation: HomeEvent()
    data object GpsDisable: HomeEvent()
    data object PermissionDenied: HomeEvent()

    data object Loading: HomeEvent()
}