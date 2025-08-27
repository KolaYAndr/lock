package org.homesharing.lockbox.navigation.routes

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import kotlinx.serialization.Serializable
import org.homesharing.lockbox.R

internal sealed interface Routes {
    @Serializable
    data object Main : Routes

    @Serializable
    data object Settings : Routes

    @Serializable
    data object Documents: Routes
}

internal data class TopLevelRoute<T : Routes>(
    @field:StringRes val name: Int,
    val route: T,
    @field:DrawableRes val icon: Int,
)

internal val topLevelRoutes = listOf(
    TopLevelRoute(R.string.main, Routes.Main, R.drawable.house),
//    TopLevelRoute(R.string.documents, Routes.Documents, R.drawable.document),
    TopLevelRoute(R.string.settings, Routes.Settings, R.drawable.settings)
)
