package com.drowsynomad.pettersonweatherapp.presentation.screens.savedLocaitons

import com.drowsynomad.pettersonweatherapp.core.base.BaseViewModel
import com.drowsynomad.pettersonweatherapp.data.models.Location
import com.drowsynomad.pettersonweatherapp.data.repository.ILocationWeatherRepository
import com.drowsynomad.pettersonweatherapp.presentation.screens.savedLocaitons.adapter.LocationDH
import com.drowsynomad.pettersonweatherapp.presentation.screens.savedLocaitons.model.SavedLocationEvent
import com.drowsynomad.pettersonweatherapp.presentation.screens.savedLocaitons.model.SavedLocationState
import kotlinx.coroutines.CoroutineExceptionHandler

/**
 * @author Roman Voloshyn (Created on 25.09.2024)
 */

class SavedLocationsViewModel(
    private val locationRepository: ILocationWeatherRepository
): BaseViewModel<SavedLocationState, SavedLocationEvent>(
    initialState = SavedLocationState.Loading
) {
    override fun handleEvent(uiEvent: SavedLocationEvent) {
        when(uiEvent) {
            SavedLocationEvent.LoadSavedLocations -> loadLocations()
        }
    }

    private fun loadLocations() {
        launch(
            exceptionHandler = CoroutineExceptionHandler { _, _ ->
                SavedLocationState.Error
            },
            action = {
                val payload = locationRepository.loadLocations()
                    .map { it.toLocationDH() }

                updateState {
                    if(payload.isEmpty()) SavedLocationState.NothingFound
                    else SavedLocationState.Success(locations = payload)
                }
            }
        )
    }

    private fun Location.toLocationDH(): LocationDH = LocationDH(this.name)
}