package org.homesharing.lockbox.data.network.dto.contract

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ContractDto(
    val booking: ContractBookingDto,
    @SerialName("contract_city")
    val contractCity: String,
    @SerialName("contract_date")
    val contractDate: String,
    val flat: ContractFlatDto,
    val id: String,
    @SerialName("landlord_info")
    val landlordInfo: ContractProfileDto,
    @SerialName("renter_info")
    val renterInfo: ContractProfileDto,
)

@Serializable
internal data class ContractDraftDto(
    val booking: ContractBookingDto,
    @SerialName("contract_city")
    val contractCity: String,
    @SerialName("contract_date")
    val contractDate: String,
    val flat: ContractFlatDto,
    @SerialName("landlord_info")
    val landlordInfo: ContractProfileDto,
    @SerialName("renter_info")
    val renterInfo: ContractProfileDto,
)

@Serializable
internal data class ContractBookingDto(
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
    val totalPrice: Int,
)

@Serializable
internal data class ContractFlatDto(
    val address: String,
    val id: String,
    @SerialName("living_area")
    val livingArea: Double,
)

@Serializable
internal data class ContractProfileDto(
    @SerialName("contact_info")
    val contactInfo: ContractContactDto,
    @SerialName("legal_info")
    val legalInfo: ContractLegalInfoDto,
)

@Serializable
internal data class ContractContactDto(
    val email: String,
    @SerialName("phone_number")
    val phoneNumber: String,
)

@Serializable
internal data class ContractLegalInfoDto(
    @SerialName("full_name")
    val fullName: String,
    @SerialName("living_address")
    val livingAddress: String,
    val passport: ContractPassportDto,
)

@Serializable
internal data class ContractPassportDto(
    val issuer: String,
    @SerialName("passport_number")
    val passportNumber: String,
    @SerialName("passport_series")
    val passportSeries: String,
)

@Serializable
internal data class ContractSummaryDto(
    val city: String,
    val date: String,
    @SerialName("document_status")
    val documentStatus: String,
    @SerialName("flat_address")
    val flatAddress: String,
    val id: String,
)

@Serializable
internal data class ContractPageDto(
    val items: List<ContractSummaryDto>,
    @SerialName("next_cursor")
    val nextCursor: String? = null,
)

@Serializable
internal data class ContractPdfDto(
    @SerialName("document_status")
    val documentStatus: String,
    val id: String,
    @SerialName("download_url")
    val downloadUrl: String? = null,
)
