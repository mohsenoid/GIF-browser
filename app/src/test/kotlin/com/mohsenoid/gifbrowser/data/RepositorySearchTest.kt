package com.mohsenoid.gifbrowser.data

import com.mohsenoid.gifbrowser.data.exception.EndOfListException
import com.mohsenoid.gifbrowser.data.exception.NoResultException
import com.mohsenoid.gifbrowser.data.network.dto.Data
import com.mohsenoid.gifbrowser.data.network.dto.SearchResponse
import com.mohsenoid.gifbrowser.domain.Repository
import com.mohsenoid.gifbrowser.test.DataFactory
import com.mohsenoid.gifbrowser.test.GifDataFactory
import com.nhaarman.mockitokotlin2.eq
import kotlinx.coroutines.runBlocking
import org.amshove.kluent.Verify
import org.amshove.kluent.VerifyNoFurtherInteractions
import org.amshove.kluent.When
import org.amshove.kluent.any
import org.amshove.kluent.called
import org.amshove.kluent.calling
import org.amshove.kluent.itReturns
import org.amshove.kluent.on
import org.amshove.kluent.that
import org.amshove.kluent.was
import org.junit.Test
import retrofit2.Response

class RepositorySearchTest : RepositoryTest() {

    @Test
    fun `test if getEpisodes calls networkClient`() {
        runBlocking {
            // GIVEN
            val query = DataFactory.randomString()
            val page = 0
            stubNetworkClientSearch(GifDataFactory.Network.searchResponse())

            // WHEN
            repository.search(query, page)

            // THEN
            Verify on networkClient that networkClient.search(
                apiKey = any(),
                query = eq(query),
                limit = eq(Repository.LIMIT),
                offset = eq(Repository.LIMIT * page)
            ) was called
            VerifyNoFurtherInteractions on networkClient
        }
    }

    @Test(expected = NoResultException::class)
    fun `test if getEpisodes throws NoResultException if result is empty on page 0`() {
        runBlocking {
            // GIVEN
            val query = DataFactory.randomString()
            val page = 0
            val data: List<Data> = GifDataFactory.Network.makeResultss(0)
            stubNetworkClientSearch(GifDataFactory.Network.searchResponse(data))

            // WHEN
            repository.search(query, page)

            // THEN
            // throws NoResultException
        }
    }

    @Test(expected = EndOfListException::class)
    fun `test if getEpisodes throws EndOfListException if result is empty on page other than 0`() {
        runBlocking {
            // GIVEN
            val query = DataFactory.randomString()
            val page = 1
            val data: List<Data> = GifDataFactory.Network.makeResultss(0)
            stubNetworkClientSearch(GifDataFactory.Network.searchResponse(data))

            // WHEN
            repository.search(query, page)

            // THEN
            // throws EndOfListException
        }
    }

    private suspend fun stubNetworkClientSearch(searchResponse: Response<SearchResponse>) {
        When calling networkClient.search(
            apiKey = any(),
            query = any(),
            limit = any(),
            offset = any()
        ) itReturns searchResponse
    }
}
