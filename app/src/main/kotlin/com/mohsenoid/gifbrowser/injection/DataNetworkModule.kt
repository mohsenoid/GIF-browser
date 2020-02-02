package com.mohsenoid.gifbrowser.injection

import com.jakewharton.retrofit2.converter.kotlinx.serialization.asConverterFactory
import com.mohsenoid.gifbrowser.data.network.NetworkClient
import com.mohsenoid.gifbrowser.data.network.NetworkConstants
import com.mohsenoid.gifbrowser.data.network.util.CacheInterceptor
import com.mohsenoid.gifbrowser.data.network.util.OfflineInterceptor
import com.mohsenoid.gifbrowser.data.network.util.RetryInterceptor
import com.mohsenoid.gifbrowser.injection.qualifier.QualifierName
import com.mohsenoid.gifbrowser.util.config.ConfigProvider
import kotlinx.serialization.json.Json
import kotlinx.serialization.json.JsonConfiguration
import okhttp3.Cache
import okhttp3.HttpUrl
import okhttp3.HttpUrl.Companion.toHttpUrlOrNull
import okhttp3.MediaType.Companion.toMediaType
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import org.koin.core.qualifier.named
import org.koin.dsl.module
import retrofit2.Converter
import retrofit2.Retrofit
import java.io.File
import java.net.UnknownHostException
import java.util.concurrent.TimeUnit

val dataNetworkModule = module {

    single {
        val baseUrl: String = getProperty(QualifierName.BASE_URL)
        baseUrl.toHttpUrlOrNull() ?: throw UnknownHostException("Invalid host: $baseUrl")
    }

    single { Json(JsonConfiguration.Stable.copy(strictMode = false)) }

    single {
        val contentType = "application/json".toMediaType()
        get<Json>().asConverterFactory(contentType)
    }

    single {
        HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }
    }

    single {
        CacheInterceptor(NetworkConstants.CACHE_MAX_AGE_MIN)
    }

    single {
        val configProvider: ConfigProvider = get()
        OfflineInterceptor(configProvider, NetworkConstants.CACHE_MAX_STALE_DAY)
    }

    single {
        RetryInterceptor(NetworkConstants.RETRY_COUNT)
    }

    single {
        var cache: Cache? = null

        try {
            val cacheDir: File = get(named(QualifierName.CACHE_DIR))
            val cacheSize: Int = NetworkConstants.CACHE_SIZE_MB * 1024 * 1024
            cache = Cache(File(cacheDir.path, NetworkConstants.HTTP_CACHE_PATH), cacheSize.toLong())
        } catch (e: Exception) {
            e.printStackTrace()
        }

        return@single cache
    }

    single {
        OkHttpClient.Builder().apply {
            val isDebug: Boolean = getProperty(QualifierName.IS_DEBUG)
            if (isDebug) {
                val interceptor: HttpLoggingInterceptor = get()
                addInterceptor(interceptor)
            }

            val cacheInterceptor: CacheInterceptor = get()
            addInterceptor(cacheInterceptor)

            val offlineInterceptor: OfflineInterceptor = get()
            addInterceptor(offlineInterceptor)

            val cache: Cache = get()
            cache(cache)

            connectTimeout(
                NetworkConstants.NETWORK_CONNECTION_TIMEOUT_SEC.toLong(),
                TimeUnit.SECONDS
            )
        }
            .build()
    }

    single<Retrofit> {
        val baseUrl: HttpUrl = get()
        val converterFactory: Converter.Factory = get()
        val okHttpClient: OkHttpClient = get()

        Retrofit.Builder()
            .baseUrl(baseUrl)
            .addConverterFactory(converterFactory)
            .client(okHttpClient)
            .build()
    }

    single<NetworkClient> {
        val retrofit: Retrofit = get()
        retrofit.create(NetworkClient::class.java)
    }
}
