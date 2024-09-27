package com.drowsynomad.pettersonweatherapp.presentation.screens.savedLocaitons

import android.widget.Toast
import androidx.lifecycle.LiveData
import com.drowsynomad.pettersonweatherapp.R
import com.drowsynomad.pettersonweatherapp.core.base.BaseFragment
import com.drowsynomad.pettersonweatherapp.databinding.FragmentSavedLocationsBinding
import com.drowsynomad.pettersonweatherapp.presentation.screens.detailedLocation.DetailedLocationFragment
import com.drowsynomad.pettersonweatherapp.presentation.screens.savedLocaitons.adapter.LocationAdapter
import com.drowsynomad.pettersonweatherapp.presentation.screens.savedLocaitons.model.SavedLocationEvent
import com.drowsynomad.pettersonweatherapp.presentation.screens.savedLocaitons.model.SavedLocationState
import com.drowsynomad.pettersonweatherapp.utils.visibility
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 * @author Roman Voloshyn (Created on 25.09.2024)
 */

class SavedLocationsFragment :
    BaseFragment<SavedLocationState, FragmentSavedLocationsBinding, SavedLocationsViewModel>() {

    companion object {
        val instance get() = SavedLocationsFragment()
    }

    override fun prepareViewBinding(): FragmentSavedLocationsBinding =
        FragmentSavedLocationsBinding.inflate(layoutInflater)

    override fun prepareViewModel(): SavedLocationsViewModel = getViewModel()

    private val locationAdapter by lazy {
        LocationAdapter(
            onItemClick = { place ->
                navigateToDetails(place)
            }
        )
    }

    override fun onStart() {
        super.onStart()
        viewModel.handleEvent(SavedLocationEvent.LoadSavedLocations)
    }

    override fun initUI(state: LiveData<SavedLocationState>) {
        with(binding) {
            rvLocations.adapter = locationAdapter

            state.observe {
                progress.visibility(false)
                tvNothingFound.visibility(false)

                when (it) {
                    SavedLocationState.Loading -> progress.visibility(true)
                    SavedLocationState.NothingFound -> tvNothingFound.visibility(true)
                    is SavedLocationState.Success -> {
                        locationAdapter.setData(it.locations)
                    }

                    SavedLocationState.Error ->
                        Toast.makeText(
                            requireContext(),
                            getString(R.string.error_something_wrong),
                            Toast.LENGTH_SHORT
                        ).show()
                }
            }
        }
    }

    private fun navigateToDetails(place: String) =
        navigateInActivity(DetailedLocationFragment.getInstance(place))
}