package com.mohsenoid.gifbrowser.injection

import com.mohsenoid.gifbrowser.injection.qualifier.QualifierName
import com.mohsenoid.gifbrowser.util.config.ConfigProvider
import com.mohsenoid.gifbrowser.util.config.ConfigProviderImpl
import com.mohsenoid.gifbrowser.util.dispatcher.AppDispatcherProvider
import com.mohsenoid.gifbrowser.util.dispatcher.DispatcherProvider
import org.koin.android.ext.koin.androidContext
import org.koin.core.qualifier.named
import org.koin.dsl.module

val appModule = module {

    single<ConfigProvider> { ConfigProviderImpl(context = androidContext()) }

    single<DispatcherProvider> { AppDispatcherProvider() }

    single(named(QualifierName.MAIN_DISPATCHER)) { get<DispatcherProvider>().mainDispatcher }

    single(named(QualifierName.IO_DISPATCHER)) { get<DispatcherProvider>().ioDispatcher }

    single(named(QualifierName.CACHE_DIR)) { androidContext().cacheDir }
}
