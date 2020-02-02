package com.mohsenoid.gifbrowser.data.network.util

import okhttp3.Interceptor
import okhttp3.Response

class RetryInterceptor(private val retryCount: Int) : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        var response = chain.proceed(request)

        var tryCount = 0
        while (!response.isSuccessful && tryCount < retryCount) {
            response.close()
            response = chain.proceed(request)
            tryCount++
        }

        return response
    }
}
