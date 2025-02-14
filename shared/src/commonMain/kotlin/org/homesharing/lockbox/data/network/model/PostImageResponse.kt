package org.homesharing.lockbox.data.network.model

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
data class PostImageResponse(
    val body: PostImageBody,
    val error: Boolean,
    @SerialName("error_text")
    val errorText: String?
)

@Serializable
data class PostImageBody(
    val link: String
)
