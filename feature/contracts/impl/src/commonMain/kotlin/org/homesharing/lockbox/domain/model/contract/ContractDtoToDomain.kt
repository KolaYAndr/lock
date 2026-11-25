package org.homesharing.lockbox.domain.model.contract

import org.homesharing.lockbox.data.network.dto.contract.ContractDto
import org.homesharing.lockbox.data.network.dto.contract.ContractFlatDto
import org.homesharing.lockbox.data.network.dto.contract.ContractPartyDto
import org.homesharing.lockbox.data.network.dto.contract.ContractProfileDto
import org.homesharing.lockbox.domain.model.contract_list.toDomain

internal fun ContractDto.toDomain() = Contract(
    id = id,
    status = status.toDomain(),
    documentStatus = documentStatus.toDomain(),
    currentVersionId = currentVersionId,
    confirmations = confirmations.map { it.toDomain() },
    events = events.map { it.toDomain() },
    contractCity = contractCity,
    contractDate = contractDate,
    landlordInfo = landlordInfo.toDomain(),
    renterInfo = renterInfo.toDomain(),
    flat = flat.toDomain(),
    booking = booking.toDomain(),
)

internal fun ContractProfileDto.toDomain() = Contract.Profile(
    contactInfo = contactInfo.toDomain(),
    legalInfo = legalInfo.toDomain(),
)

internal fun ContractProfileDto.ContactInfoDto.toDomain() = Contract.Profile.ContactInfo(
    email = email,
    phoneNumber = phoneNumber,
)

internal fun ContractProfileDto.LegalInfoDto.toDomain() = Contract.Profile.LegalInfo(
    fullName = fullName,
    livingAddress = livingAddress,
    birthDate = birthDate,
    passport = passport.toDomain(),
)

internal fun ContractProfileDto.LegalInfoDto.PassportDto.toDomain() =
    Contract.Profile.LegalInfo.Passport(
        passportSeries = passportSeries,
        passportNumber = passportNumber,
        issuer = issuer,
        issuedAt = issuedAt,
        divisionCode = divisionCode,
    )

internal fun ContractFlatDto.toDomain() = Contract.Flat(
    id = id,
    address = address,
    livingArea = livingArea,
)

internal fun ContractDto.BookingDto.toDomain() = Contract.Booking(
    startTime = startTime,
    endTime = endTime,
    dayPrice = dayPrice,
    totalPrice = totalPrice,
    numberOfPeople = numberOfPeople,
    prepayment = prepayment,
    remainingPrice = remainingPrice,
    deposit = deposit,
)

internal fun ContractDto.ContractConfirmationDto.toDomain() = Contract.Confirmation(
    id = id,
    versionId = versionId,
    party = party.toDomain(),
    actorId = actorId,
    email = email,
    snapshotSha256 = snapshotSha256,
    pdfSha256 = pdfSha256,
    confirmedAt = confirmedAt,
)

internal fun ContractDto.ContractEventDto.toDomain() = Contract.Event(
    id = id,
    type = type,
    actorId = actorId,
    versionId = versionId,
    reason = reason,
    createdAt = createdAt,
)

internal fun ContractPartyDto.toDomain(): ContractParty = when (this) {
    ContractPartyDto.Landlord -> ContractParty.Landlord
    ContractPartyDto.Guest -> ContractParty.Guest
}
