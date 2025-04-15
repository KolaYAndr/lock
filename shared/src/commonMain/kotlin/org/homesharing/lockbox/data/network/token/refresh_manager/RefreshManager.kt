package org.homesharing.lockbox.data.network.token.refresh_manager

internal const val REFRESH_THRESHOLD = 1_500L

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
internal expect class RefreshManager {
    val currentTime: Long

    fun needsRefresh(needsRefreshIn: Long): Boolean
}
