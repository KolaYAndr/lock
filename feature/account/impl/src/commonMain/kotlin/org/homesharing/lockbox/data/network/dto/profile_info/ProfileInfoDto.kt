package org.homesharing.lockbox.data.network.dto.profile_info

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ProfileInfoDto(
    @SerialName("contact_info")
    val contactInfo: ContactInfoDto,
    val id: String,
    @SerialName("legal_info")
    val legalInfo: LegalInfoDto
)

@Serializable
internal data class ContactInfoDto(
    val email: String,
    @SerialName("phone_number")
    val phoneNumber: String
)

@Serializable
internal data class LegalInfoDto(
    @SerialName("full_name")
    val fullName: String,
    @SerialName("living_address")
    val livingAddress: String,
    val passport: PassportDto
)

@Serializable
internal data class PassportDto(
    val issuer: String,
    @SerialName("passport_number")
    val passportNumber: String,
    @SerialName("passport_series")
    val passportSeries: String
)
