package com.mohsenoid.gifbrowser.injection

import com.mohsenoid.gifbrowser.data.mapper.GifEntityMapper
import com.mohsenoid.gifbrowser.data.mapper.Mapper
import com.mohsenoid.gifbrowser.data.network.dto.Result
import com.mohsenoid.gifbrowser.domain.entities.GifEntity
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataMapperModule = module {

    single<Mapper<Result, GifEntity>>(named<GifEntityMapper>()) { GifEntityMapper() }
}
