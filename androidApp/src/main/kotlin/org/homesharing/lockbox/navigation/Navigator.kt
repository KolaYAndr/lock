package org.homesharing.lockbox.navigation

import org.homesharing.lockbox.navigation.routes.Route

internal class Navigator(val state: NavigationState) {
    val currentBackStack
        get() = state.backStacks[state.topLevelRoute]
            ?: error("Back stack for ${state.topLevelRoute} doesn't exist")

    val currentScreen
        get() = currentBackStack.last()


    fun navigate(route: Route) {
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
