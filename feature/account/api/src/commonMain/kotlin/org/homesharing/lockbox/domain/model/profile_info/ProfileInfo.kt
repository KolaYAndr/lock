package org.homesharing.lockbox.domain.model.profile_info

data class ProfileInfo(
    val contactInfo: ContactInfo,
    val id: String,
    val legalInfo: LegalInfo
)

data class ContactInfo(
    val email: String,
    val phoneNumber: String
)

data class LegalInfo(
    val fullName: String,
    val livingAddress: String,
    val passport: Passport
)

data class Passport(
    val issuer: String,
    val passportNumber: String,
    val passportSeries: String
)
