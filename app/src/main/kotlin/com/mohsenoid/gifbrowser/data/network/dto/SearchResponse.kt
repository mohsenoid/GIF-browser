package com.mohsenoid.gifbrowser.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class SearchResponse(

    @SerialName(value = "data")
    val data: List<Result>,

    @SerialName(value = "pagination")
    val pagination: Pagination
)
