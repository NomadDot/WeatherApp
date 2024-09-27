package com.drowsynomad.pettersonweatherapp.presentation.screens.home

import android.util.Log
import com.drowsynomad.pettersonweatherapp.core.base.BaseViewModel
import com.drowsynomad.pettersonweatherapp.data.dataSource.local.errors.LocalServiceException
import com.drowsynomad.pettersonweatherapp.data.dataSource.remote.models.errors.RemoteServiceException
import com.drowsynomad.pettersonweatherapp.data.repository.ILocationWeatherRepository
import com.drowsynomad.pettersonweatherapp.presentation.screens.home.model.HomeEvent
import com.drowsynomad.pettersonweatherapp.presentation.screens.home.model.HomeState
import com.drowsynomad.pettersonweatherapp.presentation.screens.home.model.HomeState.Loading
import com.drowsynomad.pettersonweatherapp.utils.formatTemperature
import com.drowsynomad.pettersonweatherapp.utils.network.IConnectionStatusProvider
import kotlinx.coroutines.CoroutineExceptionHandler

/**
 * @author Roman Voloshyn (Created on 25.09.2024)
 */

class HomeViewModel(
    private val networkStatusProvider: IConnectionStatusProvider,
    private val weatherRepository: ILocationWeatherRepository,
) : BaseViewModel<HomeState, HomeEvent>(
    initialState = Loading
) {
    override fun handleEvent(uiEvent: HomeEvent) {
        when (uiEvent) {
            is HomeEvent.LoadCurrentWeather -> loadLocationWeather(uiEvent.place)
            is HomeEvent.FindLocationWeather -> checkIfLocationExists(uiEvent.place)
            HomeEvent.CantGetLocation -> updateState { HomeState.LocationError }
            HomeEvent.PermissionDenied -> updateState { HomeState.PermissionNotGranted }
            HomeEvent.Loading -> updateState { Loading }
            HomeEvent.GpsDisable -> updateState { HomeState.GpsDisableError }
        }
    }

    private fun loadLocationWeather(place: String) {
        launch(
            exceptionHandler = CoroutineExceptionHandler { coroutineContext, throwable ->
                when (throwable) {
                    is RemoteServiceException -> updateState { HomeState.Error }
                    is LocalServiceException -> updateState { HomeState.ErrorNoRecordsFound }
                    else -> Log.e("suspend$coroutineContext", "${throwable.message}")
                }
            },
            action = {
                val network = networkStatusProvider.isNetworkEnabled()
                val data = weatherRepository
                    .loadWeatherByPlace(isNetworkEnabled = network, place = place)

                updateState {
                    HomeState.Success(
                        locationWeather = with(data) {
                            copy(
                                city = city,
                                currentTemp = currentTemp.formatTemperature(),
                                minTemp = minTemp.formatTemperature(),
                                maxTemp = maxTemp.formatTemperature(),
                                feelsLikeTemp = feelsLikeTemp.formatTemperature()
                            )
                        }
                    )
                }
            })
    }

    private fun checkIfLocationExists(place: String) {
        launch(
            exceptionHandler = CoroutineExceptionHandler { _, _ ->
                updateState { HomeState.SearchLocationNotFound }
            },
            action = {
                val data = weatherRepository
                    .loadWeatherByPlace(isNetworkEnabled = true, autoSave = false, place = place)

                updateState { HomeState.SearchLocationFound(data.city) }
            })
    }
}