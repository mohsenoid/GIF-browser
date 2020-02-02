package com.mohsenoid.gifbrowser.data

import com.mohsenoid.gifbrowser.data.mapper.GifEntityMapper
import com.mohsenoid.gifbrowser.data.mapper.Mapper
import com.mohsenoid.gifbrowser.data.network.NetworkClient
import com.mohsenoid.gifbrowser.data.network.dto.Result
import com.mohsenoid.gifbrowser.domain.Repository
import com.mohsenoid.gifbrowser.domain.entities.GifEntity
import com.mohsenoid.gifbrowser.test.DataFactory
import com.mohsenoid.gifbrowser.test.TestDispatcherProvider
import com.mohsenoid.gifbrowser.util.dispatcher.DispatcherProvider
import org.junit.Before
import org.mockito.Mock
import org.mockito.MockitoAnnotations

abstract class RepositoryTest {

    @Mock
    lateinit var networkClient: NetworkClient

    lateinit var repository: Repository

    private val testDispatcherProvider: DispatcherProvider = TestDispatcherProvider()

    private val gifEntityMapper: Mapper<Result, GifEntity> = GifEntityMapper()

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)

        repository = RepositoryImpl(
            apiKey = DataFactory.randomString(),
            networkClient = networkClient,
            ioDispatcher = testDispatcherProvider.ioDispatcher,
            gifEntityMapper = gifEntityMapper
        )
    }
}
