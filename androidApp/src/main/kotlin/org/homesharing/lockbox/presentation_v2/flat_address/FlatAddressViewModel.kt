package org.homesharing.lockbox.presentation_v2.flat_address

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

internal class FlatAddressViewModel : ViewModel() {
    private val _flatAddress = MutableStateFlow(FlatAddress.Initial)
    val flatAddress = _flatAddress.asStateFlow()

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
    val flatType: FlatType?,
) {
    internal sealed interface FlatType {
        data object Living : FlatType
        data object Commercial : FlatType
    }

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
    data class FlatTypeChanged(val value: FlatAddress.FlatType?) : FlatAddressIntent
}