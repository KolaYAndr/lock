package org.homesharing.lockbox.data.network.dto.flat_list

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.homesharing.lockbox.data.network.dto.flat_detail.FlatStatusDto
import org.homesharing.lockbox.data.network.dto.premise_type.PremiseTypeDto

@Serializable
internal data class FlatSummaryListDto(
    val items: List<FlatSummaryDto>,
    @SerialName("next_cursor")
    val nextCursor: String?,
) {
    @Serializable
    internal data class FlatSummaryDto(
        val id: String,
        val name: String,
        val description: String?,
        @SerialName("premise_type")
        val premiseType: PremiseTypeDto,
        @SerialName("full_address")
        val fullAddress: String,
        @SerialName("living_area")
        val livingArea: Double?,
        val status: FlatStatusDto,
        @SerialName("cover_photo_url")
        val coverPhotoUrl: String?,
        @SerialName("active_invites_count")
        val activeInvitesCount: Int,
    )
}
