package com.mohsenoid.gifbrowser.test

import com.mohsenoid.gifbrowser.data.network.dto.FixedHeight
import com.mohsenoid.gifbrowser.data.network.dto.Images
import com.mohsenoid.gifbrowser.data.network.dto.Pagination
import com.mohsenoid.gifbrowser.data.network.dto.PreviewGif
import com.mohsenoid.gifbrowser.data.network.dto.Data
import com.mohsenoid.gifbrowser.data.network.dto.SearchResponse
import com.mohsenoid.gifbrowser.domain.entities.GifEntity
import retrofit2.Response

object GifDataFactory {

    object Network {

        fun searchResponse(data: List<Data> = makeResultss(5)): Response<SearchResponse> {
            val pagination = Pagination(
                count = DataFactory.randomInt(),
                offset = DataFactory.randomInt(),
                totalCount = DataFactory.randomInt()
            )

            return Response.success(
                SearchResponse(
                    data = data,
                    pagination = pagination
                )
            )
        }

        fun makeImages(): Images {
            return Images(
                previewGif = PreviewGif(
                    width = DataFactory.randomString(),
                    height = DataFactory.randomString(),
                    size = DataFactory.randomString(),
                    url = DataFactory.randomString()
                ),
                fixedHeight = FixedHeight(
                    width = DataFactory.randomString(),
                    height = DataFactory.randomString(),
                    url = DataFactory.randomString()
                )
            )
        }

        fun makeResult(): Data {
            return Data(
                id = DataFactory.randomString(),
                type = DataFactory.randomString(),
                title = DataFactory.randomString(),
                url = DataFactory.randomString(),
                images = makeImages()
            )
        }

        fun makeResultss(count: Int): List<Data> {
            val characters: MutableList<Data> = ArrayList()
            for (i: Int in 0 until count) {
                val data: Data = makeResult()
                characters.add(data)
            }
            return characters
        }
    }

    object Entity {

        fun makeGif(): GifEntity {
            return GifEntity(
                id = DataFactory.randomString(),
                title = DataFactory.randomString(),
                url = DataFactory.randomString(),
                thumbUrl = DataFactory.randomString()
            )
        }

        fun makeGifs(count: Int): List<GifEntity> {
            val characters: MutableList<GifEntity> = ArrayList()
            for (i: Int in 0 until count) {
                val character: GifEntity = makeGif()
                characters.add(character)
            }
            return characters
        }
    }
}
