package com.drowsynomad.pettersonweatherapp.core.base

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import com.drowsynomad.pettersonweatherapp.databinding.ActivityMainBinding
import org.koin.androidx.fragment.android.setupKoinFragmentFactory

/**
 * @author Roman Voloshyn (Created on 26.09.2024)
 */

abstract class BaseNavigationActivity: AppCompatActivity() {

    companion object {
        private const val IS_INITIALIZED = "is_initialized"
    }

    abstract fun navigateTo(screen: Fragment)
    abstract fun popBackStack()

    private var isInitialized: Boolean = false

    override fun onCreate(savedInstanceState: Bundle?) {
        setupKoinFragmentFactory()
        super.onCreate(savedInstanceState)
        isInitialized = savedInstanceState?.getBoolean(IS_INITIALIZED, false) == true
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        if(!isInitialized) {
            singleInitUI()
            isInitialized = true
        }
    }

    abstract fun singleInitUI()

    override fun onSaveInstanceState(outState: Bundle) {
        outState.putBoolean(IS_INITIALIZED, true)
        super.onSaveInstanceState(outState)
    }

}