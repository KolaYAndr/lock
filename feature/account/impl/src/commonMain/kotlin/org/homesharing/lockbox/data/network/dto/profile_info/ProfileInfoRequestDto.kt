package org.homesharing.lockbox.data.network.dto.profile_info

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ProfileInfoRequestDto(
    @SerialName("contact_info")
    val contactInfo: ContactInfoRequestDto? = null,
    @SerialName("legal_info")
    val legalInfo: LegalInfoRequestDto? = null
)

@Serializable
internal data class ContactInfoRequestDto(
    val email: String? = null,
    @SerialName("phone_number")
    val phoneNumber: String? = null
)

@Serializable
internal data class LegalInfoRequestDto(
    @SerialName("full_name")
    val fullName: String? = null,
    @SerialName("living_address")
    val livingAddress: String? = null,
    val passport: PassportRequestDto? = null
)

@Serializable
internal data class PassportRequestDto(
    val issuer: String? = null,
    @SerialName("passport_number")
    val passportNumber: String? = null,
    @SerialName("passport_series")
    val passportSeries: String? = null
)
