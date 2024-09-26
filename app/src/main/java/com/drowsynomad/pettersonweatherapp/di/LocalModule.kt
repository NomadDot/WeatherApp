package com.drowsynomad.pettersonweatherapp.di

import com.drowsynomad.pettersonweatherapp.data.dataSource.local.WeatherDatabase
import org.koin.dsl.module

/**
 * @author Roman Voloshyn (Created on 25.09.2024)
 */

val localServiceModule = module {
    single {
        get<WeatherDatabase>().weatherDao()
    }
}