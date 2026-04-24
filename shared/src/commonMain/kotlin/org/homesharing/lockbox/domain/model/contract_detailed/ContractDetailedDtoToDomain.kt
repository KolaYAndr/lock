package org.homesharing.lockbox.domain.model.contract_detailed

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.homesharing.lockbox.data.network.dto.contract_detailed.ContractDetailedDto
import org.homesharing.lockbox.domain.model.flat.Flat

internal fun ContractDetailedDto.toDomain() = ContractDetailed(
    booking = Booking(
        dayPrice = booking.dayPrice,
        deposit = booking.deposit,
        endTime = LocalDateTime.parse(booking.endTime),
        numberOfPeople = booking.numberOfPeople,
        startTime = LocalDateTime.parse(booking.startTime),
        totalPrice = booking.totalPrice
    ),
    contractCity = contractCity,
    contractDate = LocalDate.parse(contractDate),
    flat = Flat(
        address = flat.address,
        livingArea = flat.livingArea,
        id = id,
    ),
    id = id,
    landlordInfo = LandlordInfo(
        contactInfo = ContactInfo(
            email = landlordInfo.contactInfo.email,
            phoneNumber = landlordInfo.contactInfo.phoneNumber
        ),
        legalInfo = LegalInfo(
            fullName = landlordInfo.legalInfo.fullName,
            livingAddress = landlordInfo.legalInfo.livingAddress,
            passport = Passport(
                issuer = landlordInfo.legalInfo.passport.issuer,
                passportNumber = landlordInfo.legalInfo.passport.passportNumber,
                passportSeries = landlordInfo.legalInfo.passport.passportSeries
            )
        )
    ),
    renterInfo = RenterInfo(
        contactInfo = ContactInfo(
            email = renterInfo.contactInfo.email,
            phoneNumber = renterInfo.contactInfo.phoneNumber
        ),
        legalInfo = LegalInfo(
            fullName = renterInfo.legalInfo.fullName,
            livingAddress = renterInfo.legalInfo.livingAddress,
            passport = Passport(
                issuer = renterInfo.legalInfo.passport.issuer,
                passportNumber = renterInfo.legalInfo.passport.passportNumber,
                passportSeries = renterInfo.legalInfo.passport.passportSeries
            )
        )
    )
)