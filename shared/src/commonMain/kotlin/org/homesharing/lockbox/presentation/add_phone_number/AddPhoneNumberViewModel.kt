package org.homesharing.lockbox.presentation.add_phone_number

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.StateFlow

abstract class AddPhoneNumberViewModel: ViewModel() {
    abstract val uiState: StateFlow<AddPhoneNumberUiState>

    abstract fun onNumberChange(newNumber: String)
    abstract fun toStep(step: AddPhoneNumberStep)
    abstract fun onConfirmationCodeChanged(newCode: String)
    abstract fun sendConfirmationCode()
}

data class AddPhoneNumberUiState(
    val phoneNumber: String = "",
    val numberError: Boolean = false,
    val step: AddPhoneNumberStep = AddPhoneNumberStep.WantToChange,
    val confirmationCode: String = "",
)

sealed class AddPhoneNumberStep {
    data object WantToChange : AddPhoneNumberStep()
    data object NewNumberInput : AddPhoneNumberStep()
    data object ConfirmPhoneNumber : AddPhoneNumberStep()
    data object PhoneNumberChangedSuccessfully : AddPhoneNumberStep()
}