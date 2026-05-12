package org.homesharing.lockbox.domain.model.check_in_setup

sealed interface CheckInSetup {
    data object RequestPassport: CheckInSetup
    data object SignDealOnline: CheckInSetup
    data object CheckOutReport: CheckInSetup
    data object CheckInReport: CheckInSetup

    companion object {
        val entries = listOf(
            RequestPassport,
            SignDealOnline,
            CheckOutReport,
            CheckInReport,
        )
    }
}