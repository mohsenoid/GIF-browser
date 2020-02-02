package com.mohsenoid.gifbrowser.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Pagination(

    @SerialName(value = "count")
    val count: Int,

    @SerialName(value = "offset")
    val offset: Int,

    @SerialName(value = "total_count")
    val totalCount: Int
)
