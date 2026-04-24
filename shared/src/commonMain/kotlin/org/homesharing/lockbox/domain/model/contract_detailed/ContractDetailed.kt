package org.homesharing.lockbox.domain.model.contract_detailed

import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import org.homesharing.lockbox.domain.model.flat.Flat

data class ContractDetailed(
    val booking: Booking,
    val contractCity: String,
    val contractDate: LocalDate,
    val flat: Flat,
    val id: String,
    val landlordInfo: LandlordInfo,
    val renterInfo: RenterInfo
)


data class ContactInfo(
    val email: String,
    val phoneNumber: String
)


data class Booking(
    val dayPrice: Int,
    val deposit: Int,
    val endTime: LocalDateTime,
    val numberOfPeople: Int,
    val startTime: LocalDateTime,
    val totalPrice: Int
)

data class LandlordInfo(
    val contactInfo: ContactInfo,
    val legalInfo: LegalInfo
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

data class RenterInfo(
    val contactInfo: ContactInfo,
    val legalInfo: LegalInfo
)