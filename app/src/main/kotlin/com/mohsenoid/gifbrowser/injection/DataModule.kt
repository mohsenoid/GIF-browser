package com.mohsenoid.gifbrowser.injection

import com.mohsenoid.gifbrowser.data.RepositoryImpl
import com.mohsenoid.gifbrowser.data.mapper.GifEntityMapper
import com.mohsenoid.gifbrowser.domain.Repository
import com.mohsenoid.gifbrowser.injection.qualifier.QualifierName
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule = dataMapperModule + dataNetworkModule + module {

    single<Repository> {
        RepositoryImpl(
            apiKey = getProperty(QualifierName.API_KEY),
            networkClient = get(),
            ioDispatcher = get(named(QualifierName.IO_DISPATCHER)),
            gifEntityMapper = get(named<GifEntityMapper>())
        )
    }
}
