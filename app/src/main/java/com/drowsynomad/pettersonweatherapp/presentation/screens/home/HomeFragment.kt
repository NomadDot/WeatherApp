package com.drowsynomad.pettersonweatherapp.presentation.screens.home

import android.annotation.SuppressLint
import android.os.Bundle
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.LiveData
import com.drowsynomad.pettersonweatherapp.R
import com.drowsynomad.pettersonweatherapp.core.base.BaseFragment
import com.drowsynomad.pettersonweatherapp.databinding.FragmentDashboardBinding
import com.drowsynomad.pettersonweatherapp.databinding.FragmentHomeBinding
import com.drowsynomad.pettersonweatherapp.presentation.screens.home.model.HomeEvent
import com.drowsynomad.pettersonweatherapp.presentation.screens.home.model.HomeState
import com.drowsynomad.pettersonweatherapp.utils.location.Accuracy
import com.drowsynomad.pettersonweatherapp.utils.location.Accuracy.CURRENT
import com.drowsynomad.pettersonweatherapp.utils.location.Accuracy.LAST_KNOWN
import com.drowsynomad.pettersonweatherapp.utils.location.ILocationProvider
import com.drowsynomad.pettersonweatherapp.utils.location.Location
import com.drowsynomad.pettersonweatherapp.utils.onDone
import com.drowsynomad.pettersonweatherapp.utils.openGpsSettings
import com.drowsynomad.pettersonweatherapp.utils.openPermissionSettings
import com.drowsynomad.pettersonweatherapp.utils.permission.Permission.Companion.ensureFineLocationPermission
import com.drowsynomad.pettersonweatherapp.utils.visibility
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 * @author Roman Voloshyn (Created on 24.09.2024)
 */

class HomeFragment :
    BaseFragment<HomeState, FragmentHomeBinding, HomeViewModel>() {

    companion object {
        val instance get() = HomeFragment()
    }

    override fun prepareViewBinding() = FragmentHomeBinding.inflate(layoutInflater)
    override fun prepareViewModel(): HomeViewModel = getViewModel()

    private val locationProvider: ILocationProvider by inject()

    private val launcher = registerForActivityResult(
        ActivityResultContracts.RequestPermission()
    ) { isPermissionGranted ->
        if (isPermissionGranted)
            onLocationPermissionGranted()
        else
            onLocationPermissionDenied()
    }

    override fun onStart() {
        super.onStart()
        viewModel.handleEvent(HomeEvent.Loading)
        ensureFineLocationPermission(
            context = context as AppCompatActivity,
            launcher = launcher,
            onPermissionGranted = ::onLocationPermissionGranted,
            onPermissionDenied = ::onLocationPermissionDenied
        )
    }

    @SuppressLint("SetTextI18n")
    override fun initUI(state: LiveData<HomeState>) {
        binding.etSearch.onDone {
            viewModel.handleEvent(HomeEvent.FindLocationWeather(it))
        }

        state.observe {
            with(binding) {
                progress.visibility(false)
                tvWarning.visibility(false)
                actionButton.visibility(false)
                tvLocalData.visibility(false)

                when (it) {
                    HomeState.Loading -> progress.visibility(true)

                    HomeState.LocationError -> {
                        tvWarning.visibility(true)
                        tvWarning.text = getString(R.string.warning_try_again_location)
                        updateActionButton(getString(R.string.button_try_again)) {
                            provideLocation(CURRENT)
                        }
                    }

                    is HomeState.Success -> {
                        progress.visibility(false)

                        tvCity.text = it.locationWeather.city
                        tvCurrentTemp.text = it.locationWeather.currentTemp
                        tvMaxTemp.text =
                            getString(R.string.max_temp, "${it.locationWeather.maxTemp} / ")
                        tvMinTemp.text = getString(R.string.min_temp, it.locationWeather.minTemp)
                        tvWarning.visibility(false)
                        tvLocalData.visibility(!it.locationWeather.isActualData)

                        updateActionButton(getString(R.string.button_refresh_current_location)) {
                            if (locationProvider.isGpsProviderEnabled()) {
                                provideLocation(CURRENT)
                            } else
                                openGpsSettings()
                        }
                    }

                    HomeState.GpsDisableError -> {
                        tvWarning.visibility(true)
                        tvWarning.text = getString(R.string.warning_gps_turn_on)
                        updateActionButton(getString(R.string.button_enable_gps)) {
                            openGpsSettings()
                        }
                    }

                    HomeState.PermissionNotGranted -> {
                        tvWarning.visibility(true)
                        tvWarning.text = getString(R.string.warning_grant_permission)
                        updateActionButton(getString(R.string.button_grant_permission)) {
                            openPermissionSettings()
                        }
                    }

                    HomeState.Error ->
                        Toast.makeText(requireContext(),
                            getString(R.string.error_something_wrong), Toast.LENGTH_SHORT
                        ).show()
                }
            }
        }
    }

    private fun updateActionButton(
        title: String,
        onClick: () -> Unit,
    ) = binding.actionButton.apply {
        visibility(true)
        text = title
        setOnClickListener { onClick.invoke() }
    }

    private fun onLocationPermissionGranted() = ensureGpsEnabled()

    private fun ensureGpsEnabled() {
        if (locationProvider.isGpsProviderEnabled()) {
            provideLocation(accuracy = LAST_KNOWN)
        } else {
            viewModel.handleEvent(HomeEvent.GpsDisable)
        }
    }

    private fun provideLocation(accuracy: Accuracy) {
        viewModel.handleEvent(HomeEvent.Loading)

        val onLocationFetched: (Location) -> Unit = {
            viewModel.handleEvent(HomeEvent.LoadCurrentWeather(it.city))
        }
        val onError: () -> Unit = { viewModel.handleEvent(HomeEvent.CantGetLocation) }

        when (accuracy) {
            CURRENT ->
                locationProvider.provideCurrentLocation(onLocationFetched, onError)

            LAST_KNOWN ->
                locationProvider.provideLastKnownLocation(onLocationFetched, onError)
        }
    }

    private fun onLocationPermissionDenied() {
        viewModel.handleEvent(HomeEvent.PermissionDenied)
    }
}