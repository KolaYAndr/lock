package org.homesharing.lockbox.domain.model.access

import kotlinx.datetime.LocalDateTime
import kotlin.jvm.JvmInline

@JvmInline
value class Accesses(val accessList: List<Access>)

data class Access(
    val name: String?,
    val startTime: LocalDateTime,
    val endTime: LocalDateTime,
    val macAddress: String,
    val token: String,
)