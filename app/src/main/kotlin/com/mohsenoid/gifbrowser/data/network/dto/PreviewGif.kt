package com.mohsenoid.gifbrowser.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PreviewGif(

    @SerialName("width")
    val width: String,

    @SerialName("height")
    val height: String,

    @SerialName("size")
    val size: String,

    @SerialName("url")
    val url: String
)
