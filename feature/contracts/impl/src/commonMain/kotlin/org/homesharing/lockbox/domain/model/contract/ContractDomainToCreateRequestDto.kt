package org.homesharing.lockbox.domain.model.contract

import org.homesharing.lockbox.data.network.dto.contract.ContractCreateRequestDto
import org.homesharing.lockbox.data.network.dto.contract.ContractFlatDto
import org.homesharing.lockbox.data.network.dto.contract.ContractProfileDto

internal fun Contract.Profile.toRequestDto() = ContractProfileDto(
    contactInfo = contactInfo.toRequestDto(),
    legalInfo = legalInfo.toRequestDto(),
)

internal fun Contract.Profile.ContactInfo.toRequestDto() = ContractProfileDto.ContactInfoDto(
    email = email,
    phoneNumber = phoneNumber,
)

internal fun Contract.Profile.LegalInfo.toRequestDto() = ContractProfileDto.LegalInfoDto(
    fullName = fullName,
    livingAddress = livingAddress,
    birthDate = birthDate,
    passport = passport.toRequestDto(),
)

internal fun Contract.Profile.LegalInfo.Passport.toRequestDto() =
    ContractProfileDto.LegalInfoDto.PassportDto(
        passportSeries = passportSeries,
        passportNumber = passportNumber,
        issuer = issuer,
        issuedAt = issuedAt,
        divisionCode = divisionCode,
    )

internal fun Contract.Flat.toRequestDto() = ContractFlatDto(
    id = id,
    address = address,
    livingArea = livingArea,
)

internal fun Contract.Booking.toRequestDto() = ContractCreateRequestDto.BookingRequestDto(
    startTime = startTime,
    endTime = endTime,
    dayPrice = dayPrice,
    totalPrice = totalPrice,
    numberOfPeople = numberOfPeople,
    prepayment = prepayment,
    deposit = deposit,
)
