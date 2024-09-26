package com.drowsynomad.pettersonweatherapp.presentation

import android.app.Application
import com.drowsynomad.pettersonweatherapp.data.dataSource.remote.Constants
import com.drowsynomad.pettersonweatherapp.data.dataSource.remote.RestApiClient
import com.drowsynomad.pettersonweatherapp.di.appModule
import com.drowsynomad.pettersonweatherapp.di.coroutineModule
import com.drowsynomad.pettersonweatherapp.di.localServiceModule
import com.drowsynomad.pettersonweatherapp.di.remoteServiceModule
import com.drowsynomad.pettersonweatherapp.di.repositoryModule
import com.drowsynomad.pettersonweatherapp.di.viewModelModule
import com.google.android.libraries.places.api.Places
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.androidx.fragment.koin.fragmentFactory
import org.koin.core.context.startKoin

/**
 * @author Roman Voloshyn (Created on 24.09.2024)
 */

class WeatherApplication: Application() {

    override fun onCreate() {
        super.onCreate()

        Places.initializeWithNewPlacesApiEnabled(this, Constants.placeApiKey)
        RestApiClient.instance.init()

        startKoin {
            androidLogger()
            androidContext(this@WeatherApplication)
            fragmentFactory()
            modules(
                appModule,
                repositoryModule,
                viewModelModule,
                localServiceModule,
                remoteServiceModule(),
                coroutineModule
            )
        }
    }
}