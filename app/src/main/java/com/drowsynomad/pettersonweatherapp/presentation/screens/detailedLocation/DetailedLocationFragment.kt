package com.drowsynomad.pettersonweatherapp.presentation.screens.detailedLocation

import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import coil.ImageLoader
import com.drowsynomad.pettersonweatherapp.R
import com.drowsynomad.pettersonweatherapp.core.base.BaseFragment
import com.drowsynomad.pettersonweatherapp.data.models.LocationWeather
import com.drowsynomad.pettersonweatherapp.databinding.FragmentDetailedLocationBinding
import com.drowsynomad.pettersonweatherapp.presentation.screens.detailedLocation.model.DetailedLocationEvent
import com.drowsynomad.pettersonweatherapp.presentation.screens.detailedLocation.model.DetailedLocationState
import com.drowsynomad.pettersonweatherapp.utils.loadImage
import com.drowsynomad.pettersonweatherapp.utils.visibility
import org.koin.android.ext.android.inject
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 * @author Roman Voloshyn (Created on 26.09.2024)
 */

class DetailedLocationFragment :
    BaseFragment<DetailedLocationState, FragmentDetailedLocationBinding, DetailedLocationViewModel>() {

    companion object {
        private const val KEY_PLACE_ID = "place_id"

        fun getInstance(place: String) = DetailedLocationFragment().apply {
            arguments = bundleOf(KEY_PLACE_ID to place)
        }
    }

    override fun prepareViewBinding() = FragmentDetailedLocationBinding.inflate(layoutInflater)

    override fun prepareViewModel(): DetailedLocationViewModel = getViewModel()

    private val imageLoader: ImageLoader by inject()

    private val place by lazy {
        arguments?.getString(KEY_PLACE_ID) ?: ""
    }

    override fun onStart() {
        super.onStart()
        viewModel.handleEvent(DetailedLocationEvent.LoadWeather(place))
    }

    override fun initUI(state: LiveData<DetailedLocationState>) {
        binding.ibBack.setOnClickListener { popBackStack() }
        state.observe {
            with(binding) {
                progress.visibility(false)

                when (it) {
                    DetailedLocationState.Loading -> progress.visibility(true)
                    is DetailedLocationState.Success -> {
                        progress.visibility(false)
                        tvWarning.visibility(true)

                        tvCity.text = it.locationWeather.city
                        tvCurrentTemp.text = it.locationWeather.currentTemp
                        tvMaxTemp.text =
                            getString(R.string.max_temp, "${it.locationWeather.maxTemp} / ")
                        tvMinTemp.text = getString(R.string.min_temp, it.locationWeather.minTemp)
                        tvWeatherTitle.text = it.locationWeather.weatherTitle
                        tvFeelsLike.text =
                            getString(R.string.label_feels_like_s, it.locationWeather.feelsLikeTemp)
                        tvWarning.text =
                            if (it.locationWeather.isActualData) getString(R.string.label_actual_data)
                            else getString(R.string.label_not_actual_data)

                        ivWeatherIcon.loadImage(it.locationWeather.icon, imageLoader)

                        updateActionButton(it.isLocationSaved, it.locationWeather)
                    }

                    is DetailedLocationState.ActionButtonChanged -> {
                        updateActionButton(it.isLocationSaved, it.locationWeather)
                    }

                    DetailedLocationState.Error -> showToast(R.string.error_something_wrong)
                }
            }
        }
    }

    private fun updateActionButton(
        isLocationSaved: Boolean,
        locationWeather: LocationWeather,
    ) {
        binding.actionButton.text =
            if (isLocationSaved) getString(R.string.button_remove)
            else getString(R.string.button_save)

        binding.actionButton.setOnClickListener {
            viewModel.handleEvent(
                if (isLocationSaved) DetailedLocationEvent.RemoveLocation(locationWeather)
                else DetailedLocationEvent.SaveLocation(locationWeather)
            )
        }
    }
}