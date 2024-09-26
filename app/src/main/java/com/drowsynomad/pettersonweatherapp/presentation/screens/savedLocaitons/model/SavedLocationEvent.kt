package com.drowsynomad.pettersonweatherapp.presentation.screens.savedLocaitons.model

import com.drowsynomad.pettersonweatherapp.core.common.UiEvent

/**
 * @author Roman Voloshyn (Created on 25.09.2024)
 */

sealed class SavedLocationEvent: UiEvent {
    data object LoadSavedLocations: SavedLocationEvent()
}