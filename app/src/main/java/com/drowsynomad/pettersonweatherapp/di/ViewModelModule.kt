package com.drowsynomad.pettersonweatherapp.di

import com.drowsynomad.pettersonweatherapp.presentation.screens.dashboard.DashboardViewModel
import com.drowsynomad.pettersonweatherapp.presentation.screens.detailedLocation.DetailedLocationViewModel
import com.drowsynomad.pettersonweatherapp.presentation.screens.home.HomeViewModel
import com.drowsynomad.pettersonweatherapp.presentation.screens.savedLocaitons.SavedLocationsViewModel
import org.koin.dsl.module

/**
 * @author Roman Voloshyn (Created on 25.09.2024)
 */

val viewModelModule = module {
    factory { HomeViewModel(get(), get()) }
    factory { SavedLocationsViewModel(get()) }
    single { DashboardViewModel() }
    factory { DetailedLocationViewModel(get(), get()) }
}