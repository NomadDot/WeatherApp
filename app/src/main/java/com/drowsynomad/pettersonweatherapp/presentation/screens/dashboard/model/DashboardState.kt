package com.drowsynomad.pettersonweatherapp.presentation.screens.dashboard.model

import com.drowsynomad.pettersonweatherapp.core.common.UiState

/**
 * @author Roman Voloshyn (Created on 26.09.2024)
 */

sealed class DashboardState: UiState {
    data object HomeNavigation: DashboardState()
    data object SavedLocationNavigation: DashboardState()
}