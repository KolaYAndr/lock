package org.homesharing.lockbox.domain.model.profile_info

import org.homesharing.lockbox.domain.model.role.UserRole

data class ProfileInfo(
    val id: String,
    val emailVerified: Boolean?,
    val activeRole: UserRole?,
    val availableRoles: List<UserRole>,
    val identityComplete: Boolean?,
    val missingIdentityFields: List<String>,
    val firstName: String?,
    val lastName: String?,
    val middleName: String?,
    val contactInfo: ContactInfo?,
    val legalInfo: LegalInfo?,
) {
    data class ContactInfo(
        val email: String,
        val phoneNumber: String,
    )

    data class LegalInfo(
        val fullName: String,
        val livingAddress: String,
        val birthDate: String?,
        val passport: Passport,
    ) {
        data class Passport(
            val passportSeries: String,
            val passportNumber: String,
            val issuer: String,
            val issuedAt: String?,
            val divisionCode: String?,
        )
    }
}
