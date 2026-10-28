package org.homesharing.lockbox.data.network.dto.profile_info

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ProfileInfoRequest(
    @SerialName("contact_info")
    val contactInfo: ContactInfoRequest? = null,
    @SerialName("legal_info")
    val legalInfo: LegalInfoRequest? = null
)

@Serializable
internal data class ContactInfoRequest(
    val email: String? = null,
    @SerialName("phone_number")
    val phoneNumber: String? = null
)

@Serializable
internal data class LegalInfoRequest(
    @SerialName("full_name")
    val fullName: String? = null,
    @SerialName("living_address")
    val livingAddress: String? = null,
    val passport: PassportRequest? = null
)

@Serializable
internal data class PassportRequest(
    val issuer: String? = null,
    @SerialName("passport_number")
    val passportNumber: String? = null,
    @SerialName("passport_series")
    val passportSeries: String? = null
)
