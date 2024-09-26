package com.drowsynomad.pettersonweatherapp.utils.network

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkCapabilities.TRANSPORT_CELLULAR
import android.net.NetworkInfo
import android.os.Build

/**
 * @author Roman Voloshyn (Created on 26.09.2024)
 */

interface IConnectionStatusProvider {
    fun isNetworkEnabled(): Boolean
}

class NetworkStatusProvider(
    private val context: Context,
): IConnectionStatusProvider  {
    private val manager by lazy {
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    }

    override fun isNetworkEnabled(): Boolean {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork: Network? = manager.activeNetwork
            val capabilities: NetworkCapabilities? =
                manager.getNetworkCapabilities(activeNetwork)

            if (capabilities != null) {
                return if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI)) true
                else if (capabilities.hasTransport(TRANSPORT_CELLULAR)) true
                else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)) true
                else if (capabilities.hasTransport(NetworkCapabilities.TRANSPORT_VPN)) true
                else false
            }
        } else {
            try {
                val networkInfo: NetworkInfo? = manager.activeNetworkInfo
                return networkInfo != null && networkInfo.isConnected
            } catch (e: NullPointerException) {
                return false
            }
        }
        return false
    }
}