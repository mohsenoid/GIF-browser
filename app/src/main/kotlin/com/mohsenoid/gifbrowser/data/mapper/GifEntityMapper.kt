package com.mohsenoid.gifbrowser.data.mapper

import com.mohsenoid.gifbrowser.data.network.dto.Data
import com.mohsenoid.gifbrowser.domain.entities.GifEntity

class GifEntityMapper : Mapper<Data, GifEntity> {

    override fun map(input: Data): GifEntity {
        return GifEntity(
            id = input.id,
            title = input.title,
            url = input.images.previewGif.url,
            thumbUrl = input.images.fixedHeight.url
        )
    }
}
