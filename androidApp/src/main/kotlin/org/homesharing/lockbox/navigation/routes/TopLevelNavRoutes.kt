package org.homesharing.lockbox.navigation.routes

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.homesharing.lockbox.R

internal data class TopLevelRouteInfo(
    @field:StringRes val name: Int,
    @field:DrawableRes val icon: Int,
)

internal val topLevelRoutes = mapOf(
    Route.Boxes to TopLevelRouteInfo(R.string.main, R.drawable.house),
    Route.Contracts to TopLevelRouteInfo(R.string.contracts, R.drawable.document),
    Route.Settings to TopLevelRouteInfo(R.string.settings, R.drawable.settings),
)