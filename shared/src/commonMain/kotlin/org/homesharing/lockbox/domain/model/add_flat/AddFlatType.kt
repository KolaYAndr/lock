package org.homesharing.lockbox.domain.model.add_flat

sealed interface AddFlatType {
    data object Import : AddFlatType
    data object ByHand : AddFlatType
}