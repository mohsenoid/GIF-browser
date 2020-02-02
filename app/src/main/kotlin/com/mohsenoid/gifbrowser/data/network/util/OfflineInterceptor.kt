package com.mohsenoid.gifbrowser.data.network.util

import com.mohsenoid.gifbrowser.util.config.ConfigProvider
import okhttp3.CacheControl
import okhttp3.Interceptor
import okhttp3.Response
import java.util.concurrent.TimeUnit

class OfflineInterceptor(
    private val configProvider: ConfigProvider,
    private val maxStaleDay: Int
) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        var request = chain.request()

        if (!configProvider.isOnline()) {
            val cacheControl = CacheControl.Builder()
                .maxStale(maxStaleDay, TimeUnit.DAYS)
                .build()

            request = request.newBuilder()
                .cacheControl(cacheControl)
                .build()
        }

        return chain.proceed(request)
    }
}
