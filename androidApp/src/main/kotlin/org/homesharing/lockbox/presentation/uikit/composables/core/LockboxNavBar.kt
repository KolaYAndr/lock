package org.homesharing.lockbox.presentation.uikit.composables.core

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawBehind
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation3.runtime.NavKey
import org.homesharing.lockbox.navigation.routes.Route
import org.homesharing.lockbox.navigation.routes.TopLevelRouteInfo
import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme

@Composable
internal fun LockboxNavBar(
    topLevelRoutes: Map<out NavKey, TopLevelRouteInfo>,
    selectedKey: Route,
    onSelectKey: (Route) -> Unit,
) {
    val grey800 = LockBoxTheme.colors.grey800
    val width = with(LocalDensity.current) { 0.5.dp.toPx() }
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    color = grey800,
                    start = Offset(0f, 0f),
                    end = Offset(size.width, 0f),
                    strokeWidth = width
                )
            },
        containerColor = LockBoxTheme.colors.grey1000,
        contentColor = LockBoxTheme.colors.green500
    ) {
        topLevelRoutes.forEach { (topLevelDestination, data) ->
            NavigationBarItem(
                selected = selectedKey == topLevelDestination,
                onClick = { onSelectKey(topLevelDestination as Route) },
                icon = {
                    Icon(
                        painter = painterResource(data.icon),
                        contentDescription = stringResource(data.name)
                    )
                },
                label = {
                    Text(
                        style = LockBoxTheme.typography.footnote,
                        text = stringResource(data.name)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = LockBoxTheme.colors.green500,
                    selectedTextColor = LockBoxTheme.colors.green500,
                    indicatorColor = Color.Transparent,
                    unselectedIconColor = LockBoxTheme.colors.grey500,
                    unselectedTextColor = LockBoxTheme.colors.grey500
                ),
            )
        }
    }
}
