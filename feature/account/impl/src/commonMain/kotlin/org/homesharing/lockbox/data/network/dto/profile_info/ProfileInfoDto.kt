package org.homesharing.lockbox.data.network.dto.profile_info

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ProfileInfoDto(
    val id: String,
    @SerialName("email_verified")
    val emailVerified: Boolean,
    @SerialName("active_role")
    val activeRole: String,
    @SerialName("available_roles")
    val availableRoles: List<String>,
    @SerialName("identity_complete")
    val identityComplete: Boolean,
    @SerialName("missing_identity_fields")
    val missingIdentityFields: List<String>,
    @SerialName("first_name")
    val firstName: String?,
    @SerialName("last_name")
    val lastName: String?,
    @SerialName("middle_name")
    val middleName: String?,
    @SerialName("contact_info")
    val contactInfo: ContactInfoDto?,
    @SerialName("legal_info")
    val legalInfo: LegalInfoDto?,
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
