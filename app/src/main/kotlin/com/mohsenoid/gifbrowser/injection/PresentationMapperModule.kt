package com.mohsenoid.gifbrowser.injection

import com.mohsenoid.gifbrowser.domain.entities.GifEntity
import com.mohsenoid.gifbrowser.presentation.mapper.GifModelMapper
import com.mohsenoid.gifbrowser.presentation.mapper.Mapper
import com.mohsenoid.gifbrowser.presentation.model.GifModel
import org.koin.core.qualifier.named
import org.koin.dsl.module

val presentationMapperModule = module {

    single<Mapper<GifEntity, GifModel>>(named<GifModelMapper>()) { GifModelMapper() }
}
