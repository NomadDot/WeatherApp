package com.drowsynomad.pettersonweatherapp.utils.location

import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.content.Context
import android.location.Geocoder
import android.location.LocationManager
import android.os.Build
import android.os.Looper
import androidx.annotation.RequiresPermission
import com.drowsynomad.pettersonweatherapp.utils.location.Accuracy.CURRENT
import com.drowsynomad.pettersonweatherapp.utils.network.IConnectionStatusProvider
import com.google.android.gms.location.CurrentLocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.CancellationToken
import com.google.android.gms.tasks.CancellationTokenSource
import com.google.android.gms.tasks.OnTokenCanceledListener
import java.util.Locale

/**
 * @author Roman Voloshyn (Created on 26.09.2024)
 */

interface ILocationProvider {
    fun isGpsProviderEnabled(): Boolean

    fun provideLastKnownLocation(
        onLocationFetched: (Location) -> Unit,
        onError: () -> Unit,
    )

    fun provideCurrentLocation(
        onLocationFetched: (Location) -> Unit,
        onError: () -> Unit,
    )
}

class LocationProvider(
    private val networkStatusProvider: IConnectionStatusProvider,
    val context: Context,
) : ILocationProvider {

    private val fusedLocationManager by lazy {
        LocationServices.getFusedLocationProviderClient(context)
    }

    private val geocoder by lazy { Geocoder(context, Locale.ENGLISH) }

    private val defaultLocationManager by lazy {
        context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
    }

    override fun isGpsProviderEnabled(): Boolean {
        return defaultLocationManager.isProviderEnabled(LocationManager.GPS_PROVIDER)
    }

    @RequiresPermission(value = ACCESS_FINE_LOCATION)
    override fun provideLastKnownLocation(
        onLocationFetched: (Location) -> Unit,
        onError: () -> Unit,
    ) {
        fusedLocationManager.lastLocation
            .addOnSuccessListener {
                handleLocation(Accuracy.LAST_KNOWN, it, onLocationFetched, onError)
            }
            .addOnFailureListener {
                onError.invoke()
            }
    }

    @RequiresPermission(value = ACCESS_FINE_LOCATION)
    override fun provideCurrentLocation(
        onLocationFetched: (Location) -> Unit,
        onError: () -> Unit,
    ) {
        if (networkStatusProvider.isNetworkEnabled())
            provideByFusedManager(onLocationFetched, onError)
        else provideByDefaultManager(onLocationFetched, onError)
    }

    @RequiresPermission(value = ACCESS_FINE_LOCATION)
    private fun provideByFusedManager(
        onLocationFetched: (Location) -> Unit,
        onError: () -> Unit,
    ) {
        fusedLocationManager.getCurrentLocation(
            CurrentLocationRequest.Builder().build(),
            object : CancellationToken() {
                override fun onCanceledRequested(listener: OnTokenCanceledListener): CancellationToken =
                    CancellationTokenSource().token

                override fun isCancellationRequested(): Boolean = false
            }
        ).addOnSuccessListener {
            handleLocation(CURRENT, it, onLocationFetched, onError)
        }.addOnFailureListener {
            onError()
        }
    }

    @RequiresPermission(value = ACCESS_FINE_LOCATION)
    private fun provideByDefaultManager(
        onLocationFetched: (Location) -> Unit,
        onError: () -> Unit,
    ) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.R) {
            defaultLocationManager.getCurrentLocation(
                LocationManager.GPS_PROVIDER,
                null,
                context.mainExecutor,
            ) { location: android.location.Location? ->
                handleLocation(CURRENT, location, onLocationFetched, onError)
            }
        } else {
            defaultLocationManager.requestSingleUpdate(
                LocationManager.GPS_PROVIDER,
                { location: android.location.Location? ->
                    handleLocation(CURRENT, location, onLocationFetched, onError)
                },
                Looper.getMainLooper()
            )
        }
    }

    @RequiresPermission(value = ACCESS_FINE_LOCATION)
    private fun handleLocation(
        accuracy: Accuracy,
        location: android.location.Location?,
        onLocationFetched: (Location) -> Unit,
        onError: () -> Unit,
    ) {
        if (location != null) {
            val latitude = location.latitude
            val longitude = location.longitude

            try {
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
                    geocoder.getFromLocation(latitude, longitude, 1) { address ->
                        val city = address.first().locality

                        onLocationFetched.invoke(
                            Location(city, latitude, longitude)
                        )
                    }
                } else {
                    val city = geocoder.getFromLocation(latitude, longitude, 1)
                        ?.first()?.locality ?: ""

                    onLocationFetched.invoke(
                        Location(city, latitude, longitude)
                    )
                }
            } catch (_: Exception) { onError.invoke() }
        } else {
            if (accuracy == Accuracy.LAST_KNOWN) provideCurrentLocation(onLocationFetched, onError)
            else onError.invoke()
        }
    }

}