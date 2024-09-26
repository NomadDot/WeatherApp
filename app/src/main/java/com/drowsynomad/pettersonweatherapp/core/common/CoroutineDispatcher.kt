package com.drowsynomad.pettersonweatherapp.core.common

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers

/**
 * @author Roman Voloshyn (Created on 25.09.2024)
 */

sealed class Dispatcher(
    open val pool: CoroutineDispatcher
) {
    data class IO(override val pool: CoroutineDispatcher = Dispatchers.IO): Dispatcher(pool)
    data class Main(override val pool: CoroutineDispatcher = Dispatchers.Main.immediate): Dispatcher(pool)
}