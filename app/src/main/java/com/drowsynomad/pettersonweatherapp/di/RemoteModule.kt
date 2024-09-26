package com.drowsynomad.pettersonweatherapp.di

import com.drowsynomad.pettersonweatherapp.data.dataSource.remote.ApiClient
import com.drowsynomad.pettersonweatherapp.data.dataSource.remote.service.WeatherService
import org.koin.dsl.module

/**
 * @author Roman Voloshyn (Created on 25.09.2024)
 */

fun remoteServiceModule() = module {
    single {
        get<ApiClient>().provideService(WeatherService::class.java)
    }
}