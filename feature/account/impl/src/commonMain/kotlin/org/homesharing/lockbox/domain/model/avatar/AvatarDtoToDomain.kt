package org.homesharing.lockbox.domain.model.avatar

import org.homesharing.lockbox.data.network.dto.avatar.AvatarDto

internal fun AvatarDto.toDomain() = Avatar(downloadUrl = downloadUrl)
