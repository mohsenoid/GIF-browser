package com.mohsenoid.gifbrowser.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Data(

    @SerialName(value = "id")
    val id: String,

    @SerialName(value = "type")
    val type: String,

    @SerialName(value = "title")
    val title: String,

    @SerialName(value = "url")
    val url: String,

    @SerialName(value = "images")
    val images: Images
)
