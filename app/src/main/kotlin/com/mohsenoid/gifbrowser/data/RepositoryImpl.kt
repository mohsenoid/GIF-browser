package com.mohsenoid.gifbrowser.data

import com.mohsenoid.gifbrowser.data.exception.EndOfListException
import com.mohsenoid.gifbrowser.data.exception.NoResultException
import com.mohsenoid.gifbrowser.data.exception.ServerException
import com.mohsenoid.gifbrowser.data.mapper.Mapper
import com.mohsenoid.gifbrowser.data.network.NetworkClient
import com.mohsenoid.gifbrowser.data.network.dto.Result
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
    private val gifEntityMapper: Mapper<Result, GifEntity>
) : Repository {

    override suspend fun search(query: String, page: Int): List<GifEntity> {
        return withContext(ioDispatcher) {
            val searchResult: List<Result> = fetchNetworkSearch(query, page)
            val gifEntities = searchResult.map(gifEntityMapper::map)

            if (gifEntities.isEmpty()) {
                when (page) {
                    0 -> throw NoResultException(query)
                    else -> throw EndOfListException()
                }
            }

            return@withContext gifEntities
        }
    }

    private suspend fun fetchNetworkSearch(query: String, page: Int): List<Result> {
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
}
