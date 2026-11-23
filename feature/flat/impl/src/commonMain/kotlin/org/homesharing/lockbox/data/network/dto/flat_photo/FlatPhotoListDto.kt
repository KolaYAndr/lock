package org.homesharing.lockbox.data.network.dto.flat_photo

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class FlatPhotoListDto(
    val items: List<FlatPhotoDto>,
) {
    @Serializable
    internal data class FlatPhotoDto(
        val id: String,
        val url: String,
        val position: Int,
        @SerialName("file_name")
        val fileName: String?,
    )
}
