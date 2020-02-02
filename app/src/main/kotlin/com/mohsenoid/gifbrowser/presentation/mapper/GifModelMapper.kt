package com.mohsenoid.gifbrowser.presentation.mapper

import com.mohsenoid.gifbrowser.domain.entities.GifEntity
import com.mohsenoid.gifbrowser.presentation.model.GifModel

class GifModelMapper : Mapper<GifEntity, GifModel> {

    override fun map(input: GifEntity): GifModel {
        return GifModel(
            id = input.id,
            title = input.title,
            url = input.url,
            thumbUrl = input.url
        )
    }
}
