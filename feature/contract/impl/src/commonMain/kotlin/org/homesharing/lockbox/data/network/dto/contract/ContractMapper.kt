package org.homesharing.lockbox.data.network.dto.contract

import org.homesharing.lockbox.domain.model.contract.Contract
import org.homesharing.lockbox.domain.model.contract.ContractBooking
import org.homesharing.lockbox.domain.model.contract.ContractContact
import org.homesharing.lockbox.domain.model.contract.ContractDraft
import org.homesharing.lockbox.domain.model.contract.ContractFlat
import org.homesharing.lockbox.domain.model.contract.ContractLegalInfo
import org.homesharing.lockbox.domain.model.contract.ContractPage
import org.homesharing.lockbox.domain.model.contract.ContractPassport
import org.homesharing.lockbox.domain.model.contract.ContractPdf
import org.homesharing.lockbox.domain.model.contract.ContractProfile
import org.homesharing.lockbox.domain.model.contract.ContractSummary

internal fun ContractDto.toDomain(): Contract = Contract(
    booking = booking.toDomain(),
    contractCity = contractCity,
    contractDate = contractDate,
    flat = flat.toDomain(),
    id = id,
    landlordInfo = landlordInfo.toDomain(),
    renterInfo = renterInfo.toDomain(),
)

internal fun ContractDraft.toDto(): ContractDraftDto = ContractDraftDto(
    booking = booking.toDto(),
    contractCity = contractCity,
    contractDate = contractDate,
    flat = flat.toDto(),
    landlordInfo = landlordInfo.toDto(),
    renterInfo = renterInfo.toDto(),
)

internal fun ContractBookingDto.toDomain(): ContractBooking = ContractBooking(
    dayPrice = dayPrice,
    deposit = deposit,
    endTime = endTime,
    numberOfPeople = numberOfPeople,
    startTime = startTime,
    totalPrice = totalPrice,
)

internal fun ContractBooking.toDto(): ContractBookingDto = ContractBookingDto(
    dayPrice = dayPrice,
    deposit = deposit,
    endTime = endTime,
    numberOfPeople = numberOfPeople,
    startTime = startTime,
    totalPrice = totalPrice,
)

internal fun ContractFlatDto.toDomain(): ContractFlat = ContractFlat(
    address = address,
    id = id,
    livingArea = livingArea,
)

internal fun ContractFlat.toDto(): ContractFlatDto = ContractFlatDto(
    address = address,
    id = id,
    livingArea = livingArea,
)

internal fun ContractProfileDto.toDomain(): ContractProfile = ContractProfile(
    contactInfo = contactInfo.toDomain(),
    legalInfo = legalInfo.toDomain(),
)

internal fun ContractProfile.toDto(): ContractProfileDto = ContractProfileDto(
    contactInfo = contactInfo.toDto(),
    legalInfo = legalInfo.toDto(),
)

internal fun ContractContactDto.toDomain(): ContractContact = ContractContact(
    email = email,
    phoneNumber = phoneNumber,
)

internal fun ContractContact.toDto(): ContractContactDto = ContractContactDto(
    email = email,
    phoneNumber = phoneNumber,
)

internal fun ContractLegalInfoDto.toDomain(): ContractLegalInfo = ContractLegalInfo(
    fullName = fullName,
    livingAddress = livingAddress,
    passport = passport.toDomain(),
)

internal fun ContractLegalInfo.toDto(): ContractLegalInfoDto = ContractLegalInfoDto(
    fullName = fullName,
    livingAddress = livingAddress,
    passport = passport.toDto(),
)

internal fun ContractPassportDto.toDomain(): ContractPassport = ContractPassport(
    issuer = issuer,
    passportNumber = passportNumber,
    passportSeries = passportSeries,
)

internal fun ContractPassport.toDto(): ContractPassportDto = ContractPassportDto(
    issuer = issuer,
    passportNumber = passportNumber,
    passportSeries = passportSeries,
)

internal fun ContractSummaryDto.toDomain(): ContractSummary = ContractSummary(
    city = city,
    date = date,
    documentStatus = documentStatus,
    flatAddress = flatAddress,
    id = id,
)

internal fun ContractPageDto.toDomain(): ContractPage = ContractPage(
    items = items.map(ContractSummaryDto::toDomain),
    nextCursor = nextCursor,
)

internal fun ContractPdfDto.toDomain(): ContractPdf = ContractPdf(
    documentStatus = documentStatus,
    downloadUrl = downloadUrl,
    id = id,
)
