package com.mohsenoid.gifbrowser.data.network

import com.mohsenoid.gifbrowser.data.network.dto.SearchResponse
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface NetworkClient {

    @GET(NetworkConstants.ENDPOINT_SEARCH)
    suspend fun search(
        @Query(NetworkConstants.PARAM_API_KEY) apiKey: String,
        @Query(NetworkConstants.PARAM_QUERY) query: String,
        @Query(NetworkConstants.PARAM_LIMIT) limit: Int,
        @Query(NetworkConstants.PARAM_OFFSET) offset: Int
    ): Response<SearchResponse>
}
