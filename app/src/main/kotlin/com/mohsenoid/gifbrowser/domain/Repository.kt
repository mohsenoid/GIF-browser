package com.mohsenoid.gifbrowser.domain

import com.mohsenoid.gifbrowser.domain.entities.GifEntity

interface Repository {

    fun getLastSearchResult(): List<GifEntity>?

    suspend fun search(query: String, page: Int): List<GifEntity>

    companion object {
        const val LIMIT = 30
    }
}
