package com.drowsynomad.pettersonweatherapp.di

import com.drowsynomad.pettersonweatherapp.data.dataSource.local.WeatherDatabase
import com.drowsynomad.pettersonweatherapp.data.dataSource.remote.RestApiClient
import com.drowsynomad.pettersonweatherapp.presentation.screens.dashboard.DashboardFragment
import com.drowsynomad.pettersonweatherapp.presentation.screens.detailedLocation.DetailedLocationFragment
import com.drowsynomad.pettersonweatherapp.presentation.screens.home.HomeFragment
import com.drowsynomad.pettersonweatherapp.presentation.screens.main.MainActivity
import com.drowsynomad.pettersonweatherapp.presentation.screens.savedLocaitons.SavedLocationsFragment
import com.drowsynomad.pettersonweatherapp.utils.location.ILocationProvider
import com.drowsynomad.pettersonweatherapp.utils.location.LocationProvider
import com.drowsynomad.pettersonweatherapp.utils.network.IConnectionStatusProvider
import com.drowsynomad.pettersonweatherapp.utils.network.NetworkStatusProvider
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.fragment.dsl.fragment
import org.koin.dsl.module

/**
 * @author Roman Voloshyn (Created on 24.09.2024)
 */

val appModule = module {
    single {
        RestApiClient.instance
    }

    single {
        WeatherDatabase.getInstance(androidContext())
    }

    single {
        LocationProvider(get(), androidContext()) as ILocationProvider
    }

    single {
        NetworkStatusProvider(androidContext()) as IConnectionStatusProvider
    }

    scope<MainActivity> {
        fragment { DashboardFragment.instance }
        fragment { HomeFragment.instance }
        fragment { SavedLocationsFragment.instance }
    }
}