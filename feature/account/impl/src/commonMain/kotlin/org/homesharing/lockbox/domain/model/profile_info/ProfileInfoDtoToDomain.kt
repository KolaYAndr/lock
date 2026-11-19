package org.homesharing.lockbox.domain.model.profile_info

import org.homesharing.lockbox.data.network.dto.profile_info.ProfileInfoDto
import org.homesharing.lockbox.domain.model.role.toDomain

internal fun ProfileInfoDto.toDomain() = ProfileInfo(
    id = id,
    emailVerified = emailVerified,
    activeRole = activeRole.toDomain(),
    availableRoles = availableRoles.map { it.toDomain() },
    identityComplete = identityComplete,
    missingIdentityFields = missingIdentityFields,
    firstName = firstName,
    lastName = lastName,
    middleName = middleName,
    contactInfo = contactInfo?.toDomain(),
    legalInfo = legalInfo?.toDomain(),
)

internal fun ProfileInfoDto.LegalInfoDto.toDomain() = ProfileInfo.LegalInfo(
    fullName = fullName,
    livingAddress = livingAddress,
    birthDate = birthDate,
    passport = passport.toDomain(),
)

internal fun ProfileInfoDto.LegalInfoDto.PassportDto.toDomain() = ProfileInfo.LegalInfo.Passport(
    passportSeries = passportSeries,
    passportNumber = passportNumber,
    issuer = issuer,
    issuedAt = issuedAt,
    divisionCode = divisionCode,
)

internal fun ProfileInfoDto.ContactInfoDto.toDomain() = ProfileInfo.ContactInfo(
    email = email,
    phoneNumber = phoneNumber,
)
