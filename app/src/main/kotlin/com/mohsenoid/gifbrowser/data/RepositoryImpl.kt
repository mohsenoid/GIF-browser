package com.mohsenoid.gifbrowser.data

import com.mohsenoid.gifbrowser.data.exception.EndOfListException
import com.mohsenoid.gifbrowser.data.exception.NoResultException
import com.mohsenoid.gifbrowser.data.exception.ServerException
import com.mohsenoid.gifbrowser.data.mapper.Mapper
import com.mohsenoid.gifbrowser.data.network.NetworkClient
import com.mohsenoid.gifbrowser.data.network.dto.Data
import com.mohsenoid.gifbrowser.data.network.dto.SearchResponse
import com.mohsenoid.gifbrowser.domain.Repository
import com.mohsenoid.gifbrowser.domain.Repository.Companion.LIMIT
import com.mohsenoid.gifbrowser.domain.entities.GifEntity
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import retrofit2.Response

class RepositoryImpl(
    private val apiKey: String,
    private val networkClient: NetworkClient,
    private val ioDispatcher: CoroutineDispatcher,
    private val gifEntityMapper: Mapper<Data, GifEntity>
) : Repository {

    private var lastSearchResult: MutableList<GifEntity>? = null

    override suspend fun search(query: String, page: Int): List<GifEntity> {
        return withContext(ioDispatcher) {
            val searchData: List<Data> = fetchNetworkSearch(query, page)
            val gifEntities: List<GifEntity> = searchData.map(gifEntityMapper::map)

            saveLastSearchResult(gifEntities, page)

            if (gifEntities.isEmpty()) {
                when (page) {
                    0 -> throw NoResultException(query)
                    else -> throw EndOfListException()
                }
            }

            return@withContext gifEntities
        }
    }

    private suspend fun fetchNetworkSearch(query: String, page: Int): List<Data> {
        val searchResponse: Response<SearchResponse> =
            networkClient.search(apiKey, query, LIMIT, page * LIMIT)

        if (searchResponse.isSuccessful) {
            val response: SearchResponse? = searchResponse.body()

            response ?: throw ServerException(
                code = searchResponse.code(),
                error = "Response body is empty!"
            )

            return response.data
        } else {
            throw ServerException(
                code = searchResponse.code(),
                error = searchResponse.errorBody().toString()
            )
        }
    }

    private fun saveLastSearchResult(gifEntities: List<GifEntity>, page: Int) {
        lastSearchResult = when (page) {
            0 -> gifEntities
            else -> lastSearchResult?.plus(gifEntities)
        }?.toMutableList()
    }

    override fun getLastSearchResult(): List<GifEntity>? {
        return lastSearchResult?.toList()
    }
}
