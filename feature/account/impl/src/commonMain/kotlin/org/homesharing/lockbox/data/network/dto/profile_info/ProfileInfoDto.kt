package org.homesharing.lockbox.data.network.dto.profile_info

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable
import org.homesharing.lockbox.data.network.dto.role.UserRoleDto

@Serializable
internal data class ProfileInfoDto(
    val id: String,
    @SerialName("email_verified")
    val emailVerified: Boolean? = null,
    @SerialName("active_role")
    val activeRole: UserRoleDto? = null,
    @SerialName("available_roles")
    val availableRoles: List<UserRoleDto> = emptyList(),
    @SerialName("identity_complete")
    val identityComplete: Boolean? = null,
    @SerialName("missing_identity_fields")
    val missingIdentityFields: List<String> = emptyList(),
    @SerialName("first_name")
    val firstName: String? = null,
    @SerialName("last_name")
    val lastName: String? = null,
    @SerialName("middle_name")
    val middleName: String? = null,
    @SerialName("contact_info")
    val contactInfo: ContactInfoDto? = null,
    @SerialName("legal_info")
    val legalInfo: LegalInfoDto? = null,
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
        val birthDate: String? = null,
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
            val issuedAt: String? = null,
            @SerialName("division_code")
            val divisionCode: String? = null,
        )
    }

}
