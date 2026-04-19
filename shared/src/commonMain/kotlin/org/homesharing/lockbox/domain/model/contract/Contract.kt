package org.homesharing.lockbox.domain.model.contract

import kotlinx.datetime.LocalDate

data class Contract(
    val city: String,
    val date: LocalDate,
    val flatAddress: String,
    val id: String
)
