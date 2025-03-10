package org.homesharing.lockbox.data.network.token.refresh_manager

import platform.Foundation.NSDate
import platform.Foundation.timeIntervalSince1970

@Suppress("EXPECT_ACTUAL_CLASSIFIERS_ARE_IN_BETA_WARNING")
actual class RefreshManager {
    actual val currentTime: Long
        get() = (NSDate().timeIntervalSince1970 * 1000).toLong()

    actual fun needsRefresh(needsRefreshIn: Long): Boolean =
        currentTime - needsRefreshIn <= REFRESH_THRESHOLD
}