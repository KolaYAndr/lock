package org.homesharing.lockbox.data.network.dto.contract

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class ContractCreateRequestDto(
    @SerialName("contract_city")
    val contractCity: String,
    @SerialName("contract_date")
    val contractDate: String,
    @SerialName("landlord_info")
    val landlordInfo: ContractProfileDto,
    @SerialName("renter_info")
    val renterInfo: ContractProfileDto,
    val flat: ContractFlatDto,
    val booking: BookingRequestDto,
) {
    @Serializable
    internal data class BookingRequestDto(
        @SerialName("start_time")
        val startTime: String,
        @SerialName("end_time")
        val endTime: String,
        @SerialName("day_price")
        val dayPrice: Int,
        @SerialName("total_price")
        val totalPrice: Int,
        @SerialName("number_of_people")
        val numberOfPeople: Int,
        val prepayment: Int?,
        val deposit: Int,
    )
}
