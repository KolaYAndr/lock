package org.homesharing.lockbox.data.network.dto.contract

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ContractProfileDto(
    @SerialName("contact_info")
    val contactInfo: ContactInfoDto,
    @SerialName("legal_info")
    val legalInfo: LegalInfoDto,
) {
    @Serializable
    internal data class ContactInfoDto(
        val email: String,
        @SerialName("phone_number")
        val phoneNumber: String,
    )

    @Serializable
    internal data class LegalInfoDto(
        @SerialName("full_name")
        val fullName: String,
        @SerialName("living_address")
        val livingAddress: String,
        @SerialName("birth_date")
        val birthDate: String?,
        val passport: PassportDto,
    ) {
        @Serializable
        internal data class PassportDto(
            @SerialName("passport_series")
            val passportSeries: String,
            @SerialName("passport_number")
            val passportNumber: String,
            val issuer: String,
            @SerialName("issued_at")
            val issuedAt: String?,
            @SerialName("division_code")
            val divisionCode: String?,
        )
    }
}
