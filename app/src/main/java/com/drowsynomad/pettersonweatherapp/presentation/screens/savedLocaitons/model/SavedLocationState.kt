package com.drowsynomad.pettersonweatherapp.presentation.screens.savedLocaitons.model

import com.drowsynomad.pettersonweatherapp.core.common.UiState
import com.drowsynomad.pettersonweatherapp.presentation.screens.savedLocaitons.adapter.LocationDH

/**
 * @author Roman Voloshyn (Created on 25.09.2024)
 */

sealed class SavedLocationState: UiState {
    data object Loading: SavedLocationState()
    data object NothingFound: SavedLocationState()
    data object Error: SavedLocationState()
    data class Success(val locations: List<LocationDH>): SavedLocationState()
}