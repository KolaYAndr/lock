package org.homesharing.lockbox.data.network.token.refresh_manager

internal const val REFRESH_THRESHOLD = 10_000L

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
expect class RefreshManager {
    val currentTime: Long

    fun needsRefresh(needsRefreshIn: Long): Boolean
}