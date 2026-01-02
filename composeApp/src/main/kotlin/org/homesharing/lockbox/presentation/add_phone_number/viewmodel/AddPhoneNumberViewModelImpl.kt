package org.homesharing.lockbox.presentation.add_phone_number.viewmodel

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.update

internal class AddPhoneNumberViewModelImpl() : AddPhoneNumberViewModel() {
    override val uiState
        = MutableStateFlow(AddPhoneNumberUiState())
    private val phoneNumberPattern = Regex("^\\+?\\d+$")

    override fun onNumberChange(newNumber: String) {
        uiState.update {
            it.copy(
                phoneNumber = newNumber
            )
        }
    }

    override fun toStep(step: AddPhoneNumberStep) {
        uiState.update {
            it.copy(
                step = step
            )
        }
    }

    override fun onConfirmationCodeChanged(newCode: String) {
        uiState.update {
            it.copy(
                confirmationCode = newCode
            )
        }
    }

    override fun sendConfirmationCode() {
        if (phoneNumberPattern.matches(uiState.value.phoneNumber)) {
            // TODO: добавить запрос для отправки номера телефона
            toStep(AddPhoneNumberStep.ConfirmPhoneNumber)
        } else uiState.update {
            it.copy(numberError = true)
        }
    }
}