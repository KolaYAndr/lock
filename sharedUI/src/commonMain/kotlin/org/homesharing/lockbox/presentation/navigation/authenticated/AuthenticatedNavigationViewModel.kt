package org.homesharing.lockbox.presentation.navigation.authenticated

import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.launch
import org.homesharing.lockbox.domain.model.auth.AuthEntryType
import org.homesharing.lockbox.domain.repository.AuthRepository

internal class AuthenticatedNavigationViewModel(
    private val authRepository: AuthRepository,
) : ViewModel() {
    val backstack: SnapshotStateList<AuthenticatedRoutes> = mutableStateListOf()

    init {
        findInitialRoute()
    }

    private fun findInitialRoute() {
        viewModelScope.launch {
            authRepository.authEntryType
                .collectLatest { entryType ->
                    backstack.clear()
                    backstack.add(startRouteFor(entryType))
                }
        }
    }

    fun navigateTo(route: AuthenticatedRoutes) {
        backstack.add(route)
    }

    fun popBack() {
        if (backstack.size > 1) {
            backstack.removeLastOrNull()
        }
    }

    fun popUpToOrAdd(target: AuthenticatedRoutes) {
        val index = backstack.indexOfLast { it == target }
        if (index >= 0) {
            while (backstack.size > index + 1) {
                backstack.removeLastOrNull()
            }
        } else {
            backstack.add(target)
        }
    }

    private fun startRouteFor(entryType: AuthEntryType?): AuthenticatedRoutes =
        when (entryType) {
            AuthEntryType.NewUser -> AuthenticatedRoutes.NameSetup
            AuthEntryType.ExistingUser -> AuthenticatedRoutes.PickRole
            null -> AuthenticatedRoutes.FlatList
        }
}
