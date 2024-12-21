package org.homesharing.lockbox.navigation.routes

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.serialization.Serializable
import org.homesharing.lockbox.R

sealed class Routes {
    @Serializable
    data object Main : Routes()

    @Serializable
    data object Menu : Routes()
}

sealed class MainRoutes {
    @Serializable
    data object Main

    @Serializable
    data object Login
}

data class TopLevelRoute<T : Any>(
    @StringRes val name: Int,
    val route: T,
    @DrawableRes val icon: Int,
)

internal val topLevelRoutes = listOf(
    TopLevelRoute(R.string.main, Routes.Main, R.drawable.house),
    TopLevelRoute(R.string.menu, Routes.Menu, R.drawable.menu)
)
