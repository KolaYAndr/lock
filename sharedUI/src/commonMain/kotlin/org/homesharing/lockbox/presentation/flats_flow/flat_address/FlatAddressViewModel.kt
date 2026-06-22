package org.homesharing.lockbox.presentation.flats_flow.flat_address

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.api.response.Response
import org.homesharing.lockbox.domain.model.premise_type.PremiseType
import org.homesharing.lockbox.domain.repository.FlatRepository

internal class FlatAddressViewModel(
    private val repository: FlatRepository,
) : ViewModel() {
    private val _flatAddress = MutableStateFlow(FlatAddress.Initial)
    val flatAddress = _flatAddress.asStateFlow()

    private val _onSuccess = MutableSharedFlow<Unit>()
    val onSuccess: SharedFlow<Unit> = _onSuccess.asSharedFlow()

    fun onIntent(intent: FlatAddressIntent) {
        when (intent) {
            is FlatAddressIntent.CityChanged ->
                _flatAddress.update { it.copy(city = intent.value) }

            is FlatAddressIntent.StreetChanged ->
                _flatAddress.update { it.copy(street = intent.value) }

            is FlatAddressIntent.NumberChanged ->
                _flatAddress.update { it.copy(number = intent.value) }

            is FlatAddressIntent.EntranceChanged ->
                _flatAddress.update { it.copy(entrance = intent.value) }

            is FlatAddressIntent.FlatNumberChanged ->
                _flatAddress.update { it.copy(flatNumber = intent.value) }

            is FlatAddressIntent.BuildingChanged ->
                _flatAddress.update { it.copy(building = intent.value) }

            is FlatAddressIntent.StructureChanged ->
                _flatAddress.update { it.copy(structure = intent.value) }

            is FlatAddressIntent.LiterChanged ->
                _flatAddress.update { it.copy(liter = intent.value) }

            is FlatAddressIntent.CommentChanged ->
                _flatAddress.update { it.copy(comment = intent.value) }

            is FlatAddressIntent.NameChanged ->
                _flatAddress.update { it.copy(name = intent.value) }

            is FlatAddressIntent.FlatTypeChanged ->
                _flatAddress.update { it.copy(flatType = intent.value) }

            is FlatAddressIntent.Proceed -> proceed()
        }
    }

    private fun proceed() {
        val current = _flatAddress.value
        if (
            current.city.isNotBlank() &&
            current.street.isNotBlank() &&
            current.number.isNotBlank() &&
            current.name.isNotBlank() &&
            current.flatType != null
        ) {
            viewModelScope.launch {
                val response = repository.createFlat(
                    name = current.name,
                    premiseType = current.flatType,
                    city = current.city,
                    street = current.street,
                    house = current.number,
                    entrance = current.entrance,
                    apartment = current.flatNumber,
                    block = current.structure,
                    building = current.building,
                    litera = current.liter,
                    comment = current.comment,
                    livingArea = null,
                    roomNames = emptyList(),
                )

                if (response is Response.Success) {
                    _onSuccess.emit(Unit)
                }
            }
        }
    }
}

internal data class FlatAddress(
    val city: String,
    val street: String,
    val number: String,
    val entrance: String,
    val flatNumber: String,
    val building: String,
    val structure: String,
    val liter: String,
    val comment: String,
    val name: String,
    val flatType: PremiseType?,
) {

    companion object {
        val Initial = FlatAddress(
            city = "",
            street = "",
            number = "",
            entrance = "",
            flatNumber = "",
            building = "",
            structure = "",
            liter = "",
            comment = "",
            name = "",
            flatType = null
        )
    }
}

internal sealed interface FlatAddressIntent {
    data class CityChanged(val value: String) : FlatAddressIntent
    data class StreetChanged(val value: String) : FlatAddressIntent
    data class NumberChanged(val value: String) : FlatAddressIntent
    data class EntranceChanged(val value: String) : FlatAddressIntent
    data class FlatNumberChanged(val value: String) : FlatAddressIntent
    data class BuildingChanged(val value: String) : FlatAddressIntent
    data class StructureChanged(val value: String) : FlatAddressIntent
    data class LiterChanged(val value: String) : FlatAddressIntent
    data class CommentChanged(val value: String) : FlatAddressIntent
    data class NameChanged(val value: String) : FlatAddressIntent
    data class FlatTypeChanged(val value: PremiseType) : FlatAddressIntent
    data object Proceed : FlatAddressIntent
}