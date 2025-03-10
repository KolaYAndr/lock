package org.homesharing.lockbox.data.network.token.refresh_manager

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class RefreshManager {
    actual val currentTime: Long
        get() = System.currentTimeMillis()

    actual fun needsRefresh(needsRefreshIn: Long): Boolean = currentTime + REFRESH_THRESHOLD >= needsRefreshIn
}