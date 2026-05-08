package org.homesharing.lockbox.domain.model.role

sealed interface UserRole {
    data object Guest: UserRole
    data object Owner: UserRole
}