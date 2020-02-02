package com.mohsenoid.gifbrowser.util.config

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build

class ConfigProviderImpl(private val context: Context) : ConfigProvider {

    override fun isOnline(): Boolean {
        val connectivityManager: ConnectivityManager =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager
                ?: return false

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            val activeNetwork: Network = connectivityManager.activeNetwork ?: return false

            val networkCapabilities: NetworkCapabilities =
                connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false

            return networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ||
                networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET)
        } else {
            @Suppress("DEPRECATION")
            val networksInfo: Array<NetworkInfo> =
                connectivityManager.allNetworkInfo ?: return false

            networksInfo.forEach { networkInfo ->
                @Suppress("DEPRECATION")
                if (networkInfo.isConnected) return true
            }
        }

        return false
    }
}
