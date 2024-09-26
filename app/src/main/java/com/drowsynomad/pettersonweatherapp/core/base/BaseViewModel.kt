package com.drowsynomad.pettersonweatherapp.core.base

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.drowsynomad.pettersonweatherapp.core.common.UiEvent
import com.drowsynomad.pettersonweatherapp.core.common.UiState
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineExceptionHandler
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob
import kotlinx.coroutines.launch

/**
 * @author Roman Voloshyn (Created on 24.09.2024)
 */

abstract class BaseViewModel<S: UiState, E: UiEvent>(
    initialState: S
): ViewModel() {
    private val _uiState: MutableLiveData<S> = MutableLiveData(initialState)
    val uiState: LiveData<S> = _uiState
    abstract fun handleEvent(uiEvent: E)

    fun updateState(
        onNewState: (state: S) -> S
    ) {
        _uiState.value = onNewState.invoke(_uiState.value as S)
    }

    private val exceptionHandler = CoroutineExceptionHandler { context, throwable ->
        Log.e("$context", throwable.message.toString())
    }

    fun launch(
        dispatcher: CoroutineDispatcher = Dispatchers.Main.immediate,
        exceptionHandler: CoroutineExceptionHandler = this.exceptionHandler,
        action: suspend CoroutineScope.() -> Unit
    ) {
        viewModelScope.launch(exceptionHandler + SupervisorJob() + dispatcher, block = action)
    }
}