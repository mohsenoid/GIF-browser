package com.mohsenoid.gifbrowser.domain

import com.mohsenoid.gifbrowser.domain.entities.GifEntity

interface Repository {

    suspend fun search(query: String, page: Int): List<GifEntity>

    companion object {
        const val LIMIT = 30
    }
}
