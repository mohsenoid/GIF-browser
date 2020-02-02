package com.mohsenoid.gifbrowser.data.mapper

import com.mohsenoid.gifbrowser.data.network.dto.Result
import com.mohsenoid.gifbrowser.domain.entities.GifEntity
import com.mohsenoid.gifbrowser.test.GifDataFactory
import org.amshove.kluent.shouldEqual
import org.junit.Before
import org.junit.Test

class GifEntityMapperTest {
    private lateinit var gitEntityMapper: Mapper<Result, GifEntity>

    @Before
    fun setUp() {
        gitEntityMapper = GifEntityMapper()
    }

    @Test
    fun map() {
        // GIVEN
        val result: Result = GifDataFactory.Network.makeResult()

        val expectedLocation = GifEntity(
            id = result.id,
            title = result.title,
            url = result.images.previewGif.url,
            thumbUrl = result.images.fixedHeight.url
        )

        // WHEN
        val actualLocation: GifEntity = gitEntityMapper.map(result)

        // THEN
        expectedLocation shouldEqual actualLocation
    }
}
