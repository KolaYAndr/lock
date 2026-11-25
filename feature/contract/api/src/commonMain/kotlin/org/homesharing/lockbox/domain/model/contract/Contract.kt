package org.homesharing.lockbox.domain.model.contract

data class Contract(
    val booking: ContractBooking,
    val contractCity: String,
    val contractDate: String,
    val flat: ContractFlat,
    val id: String,
    val landlordInfo: ContractProfile,
    val renterInfo: ContractProfile,
)

data class ContractDraft(
    val booking: ContractBooking,
    val contractCity: String,
    val contractDate: String,
    val flat: ContractFlat,
    val landlordInfo: ContractProfile,
    val renterInfo: ContractProfile,
)

data class ContractBooking(
    val dayPrice: Int,
    val deposit: Int,
    val endTime: String,
    val numberOfPeople: Int,
    val startTime: String,
    val totalPrice: Int,
)

data class ContractFlat(
    val address: String,
    val id: String,
    val livingArea: Double,
)

data class ContractProfile(
    val contactInfo: ContractContact,
    val legalInfo: ContractLegalInfo,
)

data class ContractContact(
    val email: String,
    val phoneNumber: String,
)

data class ContractLegalInfo(
    val fullName: String,
    val livingAddress: String,
    val passport: ContractPassport,
)

data class ContractPassport(
    val issuer: String,
    val passportNumber: String,
    val passportSeries: String,
)

data class ContractSummary(
    val city: String,
    val date: String,
    val documentStatus: String,
    val flatAddress: String,
    val id: String,
)

data class ContractPage(
    val items: List<ContractSummary>,
    val nextCursor: String? = null,
)

data class ContractPdf(
    val documentStatus: String,
    val id: String,
    val downloadUrl: String? = null,
)
