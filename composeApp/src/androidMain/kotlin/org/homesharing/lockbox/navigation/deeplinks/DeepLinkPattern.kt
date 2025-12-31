package org.homesharing.lockbox.navigation.deeplinks

import androidx.core.net.toUri
import org.homesharing.lockbox.navigation.routes.Route

private const val EMAIL_CHANGE_DEEPLINK = "https://lockboxes.ru/redirect/email-change"

internal val deepLinkMap = mapOf(
    EMAIL_CHANGE_DEEPLINK.toUri() to Route.EmailChangedSuccessfully
)