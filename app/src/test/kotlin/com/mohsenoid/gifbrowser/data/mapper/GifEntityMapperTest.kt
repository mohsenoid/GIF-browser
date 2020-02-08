package com.mohsenoid.gifbrowser.data.mapper

import com.mohsenoid.gifbrowser.data.network.dto.Data
import com.mohsenoid.gifbrowser.domain.entities.GifEntity
import com.mohsenoid.gifbrowser.test.GifDataFactory
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class GifEntityMapperTest {
    private lateinit var gitEntityMapper: Mapper<Data, GifEntity>

    @Before
    fun setUp() {
        gitEntityMapper = GifEntityMapper()
    }

    @Test
    fun map() {
        // GIVEN
        val data: Data = GifDataFactory.Network.makeResult()

        val expectedLocation = GifEntity(
            id = data.id,
            title = data.title,
            url = data.images.previewGif.url,
            thumbUrl = data.images.fixedHeight.url
        )

        // WHEN
        val actualLocation: GifEntity = gitEntityMapper.map(data)

        // THEN
        expectedLocation shouldEqual actualLocation
    }
}
