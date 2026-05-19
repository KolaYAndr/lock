//package org.homesharing.lockbox.navigation
//
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.MutableState
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.saveable.rememberSerializable
//import androidx.compose.runtime.setValue
//import androidx.compose.runtime.snapshots.SnapshotStateList
//import androidx.compose.runtime.toMutableStateList
//import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
//import androidx.navigation3.runtime.NavBackStack
//import androidx.navigation3.runtime.NavEntry
//import androidx.navigation3.runtime.NavKey
//import androidx.navigation3.runtime.rememberDecoratedNavEntries
//import androidx.navigation3.runtime.rememberNavBackStack
//import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
//import androidx.savedstate.compose.serialization.serializers.MutableStateSerializer
//import androidx.savedstate.serialization.SavedStateConfiguration
//import kotlinx.serialization.PolymorphicSerializer
//import kotlinx.serialization.modules.SerializersModule
//import kotlinx.serialization.modules.polymorphic
//import org.homesharing.lockbox.navigation.routes.Route
//
//internal class NavigationState(
//    val startRoute: Route,
//    topLevelRoute: MutableState<Route>,
//    val backStacks: Map<Route, NavBackStack<Route>>,
//) {
//    var topLevelRoute by topLevelRoute
//
//    val stacksInUse: List<Route>
//        get() = if (topLevelRoute == startRoute) {
//            listOf(startRoute)
//        } else {
//            listOf(startRoute, topLevelRoute)
//        }
//}
//
//@Composable
//internal fun rememberNavigationState(
//    startRoute: Route,
//    topLevelRoutes: Set<Route>,
//): NavigationState {
//    val topLevelRoute = rememberSerializable(
//        startRoute,
//        topLevelRoutes,
//        configuration = serializersConfig,
//        serializer = MutableStateSerializer(PolymorphicSerializer(Route::class))
//    ) {
//        mutableStateOf(startRoute)
//    }
//
//    val backStacks = topLevelRoutes.associateWith { key ->
//        rememberNavBackStack(
////            configuration = serializersConfig,
//            key
//        ) as NavBackStack<Route>
//    }
//
//    return remember(startRoute, topLevelRoutes) {
//        NavigationState(
//            startRoute = startRoute,
//            topLevelRoute = topLevelRoute,
//            backStacks = backStacks
//        )
//    }
//}
//
//private val serializersConfig = SavedStateConfiguration {
//    serializersModule = SerializersModule {
//        polymorphic(Route::class) {
//            subclass(Route.Boxes::class, Route.Boxes.serializer())
//            subclass(Route.Contracts::class, Route.Contracts.serializer())
//            subclass(Route.Settings::class, Route.Settings.serializer())
//        }
//    }
//}
//
//@Composable
//internal fun NavigationState.toEntries(
//    entryProvider: (Route) -> NavEntry<Route>,
//): SnapshotStateList<NavEntry<Route>> {
//    val decoratedEntries = backStacks.mapValues { (_, stack) ->
//        val decorators = listOf(
//            rememberSaveableStateHolderNavEntryDecorator<Route>(),
//            rememberViewModelStoreNavEntryDecorator(),
//        )
//        rememberDecoratedNavEntries(
//            backStack = stack,
//            entryDecorators = decorators,
//            entryProvider = entryProvider
//        )
//    }
//
//    return stacksInUse
//        .flatMap { route ->
//            val navKey = route as NavKey
//            decoratedEntries[navKey] ?: emptyList()
//        }
//        .toMutableStateList()
//}