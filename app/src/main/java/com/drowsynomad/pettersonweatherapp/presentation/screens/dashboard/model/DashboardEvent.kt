package com.drowsynomad.pettersonweatherapp.presentation.screens.dashboard.model

import com.drowsynomad.pettersonweatherapp.core.common.UiEvent

/**
 * @author Roman Voloshyn (Created on 26.09.2024)
 */

sealed class DashboardEvent: UiEvent {
    data object ShowHome: DashboardEvent()
    data object ShowSavedLocation: DashboardEvent()
}