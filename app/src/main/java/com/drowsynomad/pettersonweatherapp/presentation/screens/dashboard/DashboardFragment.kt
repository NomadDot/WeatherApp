package com.drowsynomad.pettersonweatherapp.presentation.screens.dashboard

import android.os.Bundle
import androidx.lifecycle.LiveData
import com.drowsynomad.pettersonweatherapp.core.base.BaseFragment
import com.drowsynomad.pettersonweatherapp.databinding.FragmentDashboardBinding
import com.drowsynomad.pettersonweatherapp.presentation.screens.dashboard.model.DashboardEvent
import com.drowsynomad.pettersonweatherapp.presentation.screens.dashboard.model.DashboardState
import com.drowsynomad.pettersonweatherapp.presentation.screens.home.HomeFragment
import com.drowsynomad.pettersonweatherapp.presentation.screens.main.MainNavigation
import com.drowsynomad.pettersonweatherapp.presentation.screens.savedLocaitons.SavedLocationsFragment
import org.koin.androidx.viewmodel.ext.android.getViewModel

/**
 * @author Roman Voloshyn (Created on 26.09.2024)
 */

class DashboardFragment: BaseFragment<DashboardState, FragmentDashboardBinding, DashboardViewModel>() {

    companion object {
        val instance get() = DashboardFragment()
    }

    override fun prepareViewBinding() = FragmentDashboardBinding.inflate(layoutInflater)

    override fun prepareViewModel(): DashboardViewModel = getViewModel()

    override fun initUI(state: LiveData<DashboardState>) {
        binding.bottomNavigation.setOnItemSelectedListener { item ->
            when (item.itemId) {
                MainNavigation.HOME.id -> viewModel.handleEvent(DashboardEvent.ShowHome)
                MainNavigation.SAVED_LOCATIONS.id -> viewModel.handleEvent(DashboardEvent.ShowSavedLocation)
                else -> {}
            }
            true
        }

        state.observe {
            when(it) {
                DashboardState.HomeNavigation -> navigateToHome()
                DashboardState.SavedLocationNavigation -> navigateToListOfSavedLocations()
            }
        }
    }

    private fun navigateToHome() = showInContainer<HomeFragment>()

    private fun navigateToListOfSavedLocations() = showInContainer<SavedLocationsFragment>()
}