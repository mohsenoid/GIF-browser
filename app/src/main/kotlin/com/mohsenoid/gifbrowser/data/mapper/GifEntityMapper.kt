package com.mohsenoid.gifbrowser.data.mapper

import com.mohsenoid.gifbrowser.data.network.dto.Result
import com.mohsenoid.gifbrowser.domain.entities.GifEntity

class GifEntityMapper : Mapper<Result, GifEntity> {

    override fun map(input: Result): GifEntity {
        return GifEntity(
            id = input.id,
            title = input.title,
            url = input.images.previewGif.url,
            thumbUrl = input.images.fixedHeight.url
        )
    }
}
