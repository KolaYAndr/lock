package org.homesharing.lockbox.data.network.dto.profile_info

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ProfileInfoRequestDto(
    @SerialName("first_name")
    val firstName: String?,
    @SerialName("last_name")
    val lastName: String?,
    @SerialName("middle_name")
    val middleName: String?,
    @SerialName("contact_info")
    val contactInfo: ContactInfoRequestDto?,
    @SerialName("legal_info")
    val legalInfo: LegalInfoRequestDto?,
) {
    @Serializable
    internal data class ContactInfoRequestDto(
        @SerialName("phone_number")
        val phoneNumber: String?,
        val email: String?,
    )

    @Serializable
    internal data class LegalInfoRequestDto(
        @SerialName("living_address")
        val livingAddress: String?,
        @SerialName("birth_date")
        val birthDate: String?,
        val passport: PassportRequestDto?,
    ) {
        @Serializable
        internal data class PassportRequestDto(
            @SerialName("passport_series")
            val passportSeries: String?,
            @SerialName("passport_number")
            val passportNumber: String?,
            val issuer: String?,
            @SerialName("issued_at")
            val issuedAt: String?,
            @SerialName("division_code")
            val divisionCode: String?,
        )
    }
}
