package org.homesharing.lockbox.presentation.login

sealed interface EmailError {
    data object DoesntMatchEmailPattern: EmailError
}
