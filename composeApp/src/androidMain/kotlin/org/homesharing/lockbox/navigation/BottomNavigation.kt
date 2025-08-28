package org.homesharing.lockbox.navigation

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
import androidx.navigation.NavBackStackEntry
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.NavDestination.Companion.hierarchy
import org.homesharing.lockbox.navigation.routes.Routes
import org.homesharing.lockbox.navigation.routes.TopLevelRoute
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme

@Composable
internal fun BottomNavigation(
    topLevelRoutes: List<TopLevelRoute<out Routes>>,
    navBackStackEntry: NavBackStackEntry?,
    onClick: (Routes) -> Unit,
) {
    val grey800 = LockBoxTheme.colors.grey800
    val width = with(LocalDensity.current) { 0.5.dp.toPx() }
    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .drawBehind {
                drawLine(
                    grey800, Offset(0f, 0f), Offset(size.width, 0f), width
                )
            },
        containerColor = LockBoxTheme.colors.grey1000,
        contentColor = LockBoxTheme.colors.green500
    ) {
        val currentDestination = navBackStackEntry?.destination
        topLevelRoutes.forEach { topLevelRoute ->
            NavigationBarItem(
                selected = currentDestination?.hierarchy?.any {
                    it.hasRoute(topLevelRoute.route::class)
                } ?: false,
                onClick = {
                    onClick(topLevelRoute.route)
                },
                label = {
                    Text(
                        style = LockBoxTheme.typography.footnote,
                        text = stringResource(topLevelRoute.name)
                    )
                },
                icon = {
                    Icon(
                        painter = painterResource(topLevelRoute.icon),
                        contentDescription = stringResource(topLevelRoute.name)
                    )
                },
                colors = NavigationBarItemDefaults.colors(
                    selectedIconColor = LockBoxTheme.colors.green500,
                    selectedTextColor = LockBoxTheme.colors.green500,
                    indicatorColor = Color.Transparent,
                    unselectedIconColor = LockBoxTheme.colors.grey500,
                    unselectedTextColor = LockBoxTheme.colors.grey500
                )
            )
        }
    }
}
