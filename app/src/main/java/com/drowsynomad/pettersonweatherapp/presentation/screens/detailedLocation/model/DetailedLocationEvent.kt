package com.drowsynomad.pettersonweatherapp.presentation.screens.detailedLocation.model

import com.drowsynomad.pettersonweatherapp.core.common.UiEvent
import com.drowsynomad.pettersonweatherapp.data.models.LocationWeather

/**
 * @author Roman Voloshyn (Created on 26.09.2024)
 */

sealed class DetailedLocationEvent: UiEvent {
    data class LoadWeather(val place: String): DetailedLocationEvent()

    data class RemoveLocation(val locationWeather: LocationWeather): DetailedLocationEvent()
    data class SaveLocation(val locationWeather: LocationWeather): DetailedLocationEvent()
}