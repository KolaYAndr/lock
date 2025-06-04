package org.homesharing.lockbox.presentation.login

internal sealed interface EmailError {
    data object DoesntMatchEmailPattern: EmailError
}
