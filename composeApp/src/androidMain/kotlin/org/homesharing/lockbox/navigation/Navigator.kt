package org.homesharing.lockbox.navigation

import androidx.navigation3.runtime.NavKey

internal class Navigator(val state: NavigationState) {
    val currentBackStack
        get() = state.backStacks[state.topLevelRoute]
            ?: error("Back stack for ${state.topLevelRoute} doesn't exist")


    fun navigate(route: NavKey) {
        if (route in state.backStacks.keys) {
            state.topLevelRoute = route
        } else {
            state.backStacks[state.topLevelRoute]?.add(route)
        }
    }

    fun navBack() {
        val currentRoute = currentBackStack.last()

        if (currentRoute == state.topLevelRoute) {
            state.topLevelRoute = state.startRoute
        } else {
            currentBackStack.removeLastOrNull()
        }
    }
}
