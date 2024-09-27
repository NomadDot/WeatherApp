package com.drowsynomad.pettersonweatherapp.presentation.screens.main

import androidx.fragment.app.Fragment
import com.drowsynomad.pettersonweatherapp.R
import com.drowsynomad.pettersonweatherapp.core.base.BaseNavigationActivity
import com.drowsynomad.pettersonweatherapp.presentation.screens.dashboard.DashboardFragment
import org.koin.androidx.fragment.android.replace

class MainActivity : BaseNavigationActivity() {

    override fun navigateTo(screen: Fragment) {
        supportFragmentManager.beginTransaction()
            .setCustomAnimations(
                R.anim.anim_fragment_horizontal_enter,
                R.anim.anim_fragment_horizontal_exit,
                R.anim.anim_fragment_horizontal_pop_enter,
                R.anim.anim_fragment_horizontal_pop_exit,)
            .replace(R.id.rootContainer, screen)
            .addToBackStack(screen::class.simpleName)
            .commit()
    }

    override fun popBackStack() = supportFragmentManager.popBackStack()

    override fun singleInitUI() {
        supportFragmentManager.beginTransaction()
            .replace<DashboardFragment>(containerViewId = R.id.rootContainer)
            .commit()
    }
}