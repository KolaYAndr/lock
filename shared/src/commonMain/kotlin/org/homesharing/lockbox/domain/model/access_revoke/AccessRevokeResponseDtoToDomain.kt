package org.homesharing.lockbox.domain.model.access_revoke

import org.homesharing.lockbox.data.network.dto.access_revoke.AccessRevokeResponseDto

internal fun AccessRevokeResponseDto.toDomain(): AccessRevoke = AccessRevoke(result)
