package com.drowsynomad.pettersonweatherapp.core.base

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.viewbinding.ViewBinding
import com.drowsynomad.pettersonweatherapp.R
import com.drowsynomad.pettersonweatherapp.core.common.UiState
import com.drowsynomad.pettersonweatherapp.presentation.screens.main.MainNavigation
import org.koin.androidx.fragment.android.replace

/**
 * @author Roman Voloshyn (Created on 24.09.2024)
 */

abstract class BaseFragment<S : UiState, V : ViewBinding, VM : BaseViewModel<S, *>> : Fragment() {

    protected val binding: V by lazy { prepareViewBinding() }
    protected val viewModel: VM by lazy { prepareViewModel() }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?,
    ): View = binding.root

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initUI(viewModel.uiState)
    }

    abstract fun prepareViewBinding(): V
    abstract fun prepareViewModel(): VM

    abstract fun initUI(state: LiveData<S>)

    protected fun LiveData<S>.observe(onStateChanged: (S) -> Unit) {
        this.observe(this@BaseFragment) {
            onStateChanged.invoke(it)
        }
    }

    protected fun navigateInActivity(screen: Fragment) {
        (requireActivity() as BaseNavigationActivity)
            .navigateTo(screen)
    }

    protected fun popBackStack(screen: Fragment) {
        (requireActivity() as BaseNavigationActivity)
            .popBackStack()
    }

    protected inline fun <reified F: Fragment> showInContainer() {
        childFragmentManager.beginTransaction()
            .replace<F>(R.id.fragmentContainer)
            .commit()
    }
}