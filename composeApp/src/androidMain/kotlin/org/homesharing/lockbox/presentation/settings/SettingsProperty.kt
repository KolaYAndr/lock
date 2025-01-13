package org.homesharing.lockbox.presentation.settings

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import org.homesharing.lockbox.R
import org.homesharing.lockbox.navigation.routes.SettingsRoutes

internal data class SettingsProperty(
    @DrawableRes val icon: Int,
    @StringRes val title: Int,
    val route: SettingsRoutes? = null
)

internal val accountProperties = listOf(
    SettingsProperty(
        R.drawable.user,
        R.string.account,
        SettingsRoutes.Account
    ),
    SettingsProperty(
        R.drawable.shield,
        R.string.security,
        SettingsRoutes.Security
    )
)

internal val aboutProperties = listOf(
    SettingsProperty(
        R.drawable.circle_alert,
        R.string.about_app,
        SettingsRoutes.AboutApp
    ),
    SettingsProperty(
        R.drawable.headset,
        R.string.help
    )
)