package com.drowsynomad.pettersonweatherapp.di

import com.drowsynomad.pettersonweatherapp.data.repository.ILocationWeatherRepository
import com.drowsynomad.pettersonweatherapp.data.repository.LocationWeatherRepository
import org.koin.dsl.module

/**
 * @author Roman Voloshyn (Created on 24.09.2024)
 */

val repositoryModule = module {
    factory {
        LocationWeatherRepository(get(), get(), get()) as ILocationWeatherRepository
    }
}