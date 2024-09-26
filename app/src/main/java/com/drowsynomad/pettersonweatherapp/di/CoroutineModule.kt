package com.drowsynomad.pettersonweatherapp.di

import com.drowsynomad.pettersonweatherapp.core.common.Dispatcher
import org.koin.dsl.module

/**
 * @author Roman Voloshyn (Created on 25.09.2024)
 */

val coroutineModule = module {
    single { Dispatcher.IO() }
    single { Dispatcher.Main() }
}