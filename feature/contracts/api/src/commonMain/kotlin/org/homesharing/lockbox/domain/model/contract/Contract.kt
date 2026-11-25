package org.homesharing.lockbox.domain.model.contract

import org.homesharing.lockbox.domain.model.contract_list.ContractDocumentStatus
import org.homesharing.lockbox.domain.model.contract_list.ContractStatus

data class Contract(
    val id: String,
    val status: ContractStatus,
    val documentStatus: ContractDocumentStatus,
    val currentVersionId: String?,
    val confirmations: List<Confirmation>,
    val events: List<Event>,
    val contractCity: String,
    val contractDate: String,
    val landlordInfo: Profile,
    val renterInfo: Profile,
    val flat: Flat,
    val booking: Booking,
) {
    data class Profile(
        val contactInfo: ContactInfo,
        val legalInfo: LegalInfo,
    ) {
        data class ContactInfo(
            val email: String,
            val phoneNumber: String,
        )

        data class LegalInfo(
            val fullName: String,
            val livingAddress: String,
            val birthDate: String? = null,
            val passport: Passport,
        ) {
            data class Passport(
                val passportSeries: String,
                val passportNumber: String,
                val issuer: String,
                val issuedAt: String? = null,
                val divisionCode: String? = null,
            )
        }
    }

    data class Flat(
        val id: String,
        val address: String,
        val livingArea: Double,
    )

    data class Booking(
        val startTime: String,
        val endTime: String,
        val dayPrice: Int,
        val totalPrice: Int,
        val numberOfPeople: Int,
        val prepayment: Int? = null,
        val remainingPrice: Int? = null,
        val deposit: Int,
    )

    data class Confirmation(
        val id: String,
        val versionId: String,
        val party: ContractParty,
        val actorId: String,
        val email: String,
        val snapshotSha256: String,
        val pdfSha256: String,
        val confirmedAt: String,
    )

    data class Event(
        val id: Long,
        val type: String,
        val actorId: String?,
        val versionId: String?,
        val reason: String,
        val createdAt: String,
    )
}
