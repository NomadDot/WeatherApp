package com.drowsynomad.pettersonweatherapp.presentation.screens.dashboard

import com.drowsynomad.pettersonweatherapp.core.base.BaseViewModel
import com.drowsynomad.pettersonweatherapp.presentation.screens.dashboard.model.DashboardEvent
import com.drowsynomad.pettersonweatherapp.presentation.screens.dashboard.model.DashboardState

/**
 * @author Roman Voloshyn (Created on 26.09.2024)
 */

class DashboardViewModel: BaseViewModel<DashboardState, DashboardEvent>(
    DashboardState.HomeNavigation
) {
    override fun handleEvent(uiEvent: DashboardEvent) {
        when(uiEvent) {
            DashboardEvent.ShowHome -> updateState { DashboardState.HomeNavigation }
            DashboardEvent.ShowSavedLocation -> updateState { DashboardState.SavedLocationNavigation }
        }
    }
}