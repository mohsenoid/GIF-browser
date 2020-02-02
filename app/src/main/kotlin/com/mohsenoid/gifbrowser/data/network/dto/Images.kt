package com.mohsenoid.gifbrowser.data.network.dto

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class Images(

    @SerialName("preview_gif")
    val previewGif: PreviewGif,

    @SerialName("fixed_height")
    val fixedHeight: FixedHeight
)
