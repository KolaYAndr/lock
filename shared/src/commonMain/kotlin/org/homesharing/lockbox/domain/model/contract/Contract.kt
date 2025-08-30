package org.homesharing.lockbox.domain.model.contract

import kotlinx.datetime.LocalDateTime

data class Contract(
    val id: String,
    val name: String,
    val creationDate: LocalDateTime,
    val downloadLink: String
)
