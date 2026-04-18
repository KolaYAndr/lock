package org.homesharing.lockbox.domain.model.access_revoke

import org.homesharing.lockbox.data.network.dto.access_revoke.AccessRevokeDto

internal fun AccessRevokeDto.toDomain() = AccessRevoke(result)
