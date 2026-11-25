package org.homesharing.lockbox.data.network.dto.contract

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ContractRejectRequestDto(
    @SerialName("version_id")
    val versionId: String,
    val reason: String?,
)
