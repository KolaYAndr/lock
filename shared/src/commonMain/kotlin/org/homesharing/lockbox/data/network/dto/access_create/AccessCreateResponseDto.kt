package org.homesharing.lockbox.data.network.dto.access_create

import org.homesharing.lockbox.data.network.dto.accessed.AccessDto
import kotlin.jvm.JvmInline

@JvmInline
internal value class AccessCreateResponseDto(val access: AccessDto)
