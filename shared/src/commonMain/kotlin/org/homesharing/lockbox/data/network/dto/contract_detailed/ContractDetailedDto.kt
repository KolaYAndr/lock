package org.homesharing.lockbox.data.network.dto.contract_detailed

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ContractDetailedDto(
    val booking: BookingDto,
    @SerialName("contract_city")
    val contractCity: String,
    @SerialName("contract_date")
    val contractDate: String,
    val flat: FlatParamsDto,
    val id: String,
    @SerialName("landlord_info")
    val landlordInfo: LandlordInfoDto,
    @SerialName("renter_info")
    val renterInfo: RenterInfoDto
)

@Serializable
internal data class ContactInfoDto(
    val email: String,
    @SerialName("phone_number")
    val phoneNumber: String
)

@Serializable
internal data class BookingDto(
    @SerialName("day_price")
    val dayPrice: Int,
    val deposit: Int,
    @SerialName("end_time")
    val endTime: String,
    @SerialName("number_of_people")
    val numberOfPeople: Int,
    @SerialName("start_time")
    val startTime: String,
    @SerialName("total_price")
    val totalPrice: Int
)

@Serializable
internal data class FlatParamsDto(
    val address: String,
    @SerialName("living_area")
    val livingArea: Double
)

@Serializable
internal data class LandlordInfoDto(
    @SerialName("contact_info")
    val contactInfo: ContactInfoDto,
    @SerialName("legal_info")
    val legalInfo: LegalInfoDto
)

@Serializable
internal data class LegalInfoDto(
    @SerialName("full_name")
    val fullName: String,
    @SerialName("living_address")
    val livingAddress: String,
    val passport: PassportDto
)

@Serializable
internal data class PassportDto(
    val issuer: String,
    @SerialName("passport_number")
    val passportNumber: String,
    @SerialName("passport_series")
    val passportSeries: String
)

@Serializable
internal data class RenterInfoDto(
    @SerialName("contact_info")
    val contactInfo: ContactInfoDto,
    @SerialName("legal_info")
    val legalInfo: LegalInfoDto
)