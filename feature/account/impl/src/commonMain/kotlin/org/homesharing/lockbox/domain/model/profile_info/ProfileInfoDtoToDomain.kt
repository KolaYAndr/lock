package org.homesharing.lockbox.domain.model.profile_info

import org.homesharing.lockbox.data.network.dto.profile_info.ContactInfoDto
import org.homesharing.lockbox.data.network.dto.profile_info.LegalInfoDto
import org.homesharing.lockbox.data.network.dto.profile_info.PassportDto
import org.homesharing.lockbox.data.network.dto.profile_info.ProfileInfoDto

internal fun ProfileInfoDto.toDomain() = ProfileInfo(
    id = id,
    contactInfo = contactInfo.toDomain(),
    legalInfo = legalInfo.toDomain()
)

internal fun LegalInfoDto.toDomain() = LegalInfo(
    fullName = fullName,
    livingAddress = livingAddress,
    passport = passport.toDomain()
)

internal fun PassportDto.toDomain() = Passport(
    issuer = issuer,
    passportNumber = passportNumber,
    passportSeries = passportSeries
)

internal fun ContactInfoDto.toDomain() = ContactInfo(
    email = email,
    phoneNumber = phoneNumber
)
