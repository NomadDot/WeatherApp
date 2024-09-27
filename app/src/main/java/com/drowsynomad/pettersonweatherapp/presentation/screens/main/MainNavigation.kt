package com.drowsynomad.pettersonweatherapp.presentation.screens.main

import androidx.annotation.IdRes
import com.drowsynomad.pettersonweatherapp.R

/**
 * @author Roman Voloshyn (Created on 26.09.2024)
 */

enum class MainNavigation(@IdRes val id: Int) {
    HOME(R.id.dashboard), SAVED_LOCATIONS(R.id.saved)
}