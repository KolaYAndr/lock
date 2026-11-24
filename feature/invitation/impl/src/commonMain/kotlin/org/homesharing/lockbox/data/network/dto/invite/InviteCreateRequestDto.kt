package org.homesharing.lockbox.data.network.dto.invite

import kotlinx.serialization.SerialName
import kotlinx.serialization.Serializable

@Serializable
internal data class InviteCreateRequestDto(
    @SerialName("flat_id")
    val flatId: String,
    val guest: GuestRequestDto,
    @SerialName("check_in")
    val checkIn: CheckInDetailsRequestDto,
    val settings: CheckInSettingsRequestDto?,
) {
    @Serializable
    internal data class GuestRequestDto(
        val email: String,
        val name: String,
    )

    @Serializable
    internal data class CheckInDetailsRequestDto(
        @SerialName("start_date")
        val startDate: String,
        @SerialName("end_date")
        val endDate: String,
        @SerialName("number_of_guests")
        val numberOfGuests: Int,
        @SerialName("check_in_time")
        val checkInTime: String?,
        @SerialName("check_out_time")
        val checkOutTime: String?,
        val prepayment: Int?,
        val deposit: Int?,
        @SerialName("day_price")
        val dayPrice: Int?,
        val comment: String?,
    )

    @Serializable
    internal data class CheckInSettingsRequestDto(
        @SerialName("request_passport")
        val requestPassport: Boolean?,
        @SerialName("sign_contract_online")
        val signContractOnline: Boolean?,
        @SerialName("room_report")
        val roomReport: Boolean?,
        @SerialName("exit_report")
        val exitReport: Boolean?,
        @SerialName("intercom_code")
        val intercomCode: String?,
        val comment: String?,
    )
}
