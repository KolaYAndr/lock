package org.homesharing.lockbox.presentation.add_phone_number

import android.util.Patterns
import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class AddPhoneNumberViewModel : ViewModel() {
    private val _uiState: MutableStateFlow<AddPhoneNumberUiState> =
        MutableStateFlow(AddPhoneNumberUiState())
    val uiState: StateFlow<AddPhoneNumberUiState> get() = _uiState.asStateFlow()
    private val phonePattern = Patterns.PHONE

    fun onNumberChange(newNumber: String) {
        _uiState.update {
            it.copy(phoneNumber = newNumber)
        }
    }

    fun toStep(step: AddPhoneNumberStep) {
        _uiState.update {
            it.copy(step = step)
        }
    }

    fun onConfirmationCodeChanged(code: String) {
        _uiState.update {
            it.copy(confirmationCode = code)
        }
    }

    fun confirmCode() {

    }

    fun  sendConfirmationCode() {
        if (phonePattern.matcher(uiState.value.phoneNumber).matches()) {
            toStep(AddPhoneNumberStep.ConfirmPhoneNumber)
        } else _uiState.update { it.copy(numberError = "Doesn't match phone number pattern") }
    }
}

data class AddPhoneNumberUiState(
    val phoneNumber: String = "",
    val numberError: String? = "",
    val step: AddPhoneNumberStep = AddPhoneNumberStep.WantToChange,
    val confirmationCode: String = ""
)

sealed class AddPhoneNumberStep {
    data object WantToChange : AddPhoneNumberStep()
    data object NewNumberInput : AddPhoneNumberStep()
    data object ConfirmPhoneNumber : AddPhoneNumberStep()
    data object PhoneNumberChangedSuccessfully : AddPhoneNumberStep()
}
