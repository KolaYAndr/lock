package org.homesharing.lockbox

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import androidx.core.net.toUri

private const val USER_AGREEMENT_URL = "https://api.lockboxes.ru:9000/policies/user_agreement_black.pdf"
private const val PRIVACY_POLICY_URL = "https://api.lockboxes.ru:9000/policies/privacy_policy_black.pdf"

fun downloadUserAgreement(context: Context) {
    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val destinationUri = context.externalCacheDir ?: return

    val request = DownloadManager.Request(Uri.parse(USER_AGREEMENT_URL)).apply {
        setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        setAllowedOverRoaming(false)
        setTitle(context.getString(R.string.user_agreement))
        setDestinationUri(destinationUri.toUri())
        setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    }

    downloadManager.enqueue(request)
}

fun downloadPrivacyPolicy(context: Context) {
    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val destinationUri = context.externalCacheDir ?: return

    val request = DownloadManager.Request(Uri.parse(PRIVACY_POLICY_URL)).apply {
        setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        setAllowedOverRoaming(false)
        setTitle(context.getString(R.string.privacy_policy))
        setDestinationUri(destinationUri.toUri())
        setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    }

    downloadManager.enqueue(request)
}
