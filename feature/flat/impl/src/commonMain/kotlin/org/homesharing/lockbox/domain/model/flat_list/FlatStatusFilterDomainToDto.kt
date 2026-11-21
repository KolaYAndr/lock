package org.homesharing.lockbox.domain.model.flat_list

import org.homesharing.lockbox.data.network.dto.flat_list.FlatStatusFilterDto

internal fun FlatStatusFilter.toDto(): FlatStatusFilterDto = when (this) {
    FlatStatusFilter.Active -> FlatStatusFilterDto.Active
    FlatStatusFilter.Archived -> FlatStatusFilterDto.Archived
    FlatStatusFilter.All -> FlatStatusFilterDto.All
}
