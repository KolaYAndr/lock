package org.homesharing.lockbox.presentation.uikit

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import androidx.annotation.StringRes
import androidx.core.net.toUri
import org.homesharing.lockbox.R

private const val USER_AGREEMENT_URL =
    "https://api.lockboxes.ru:9000/policies/user_agreement_black.pdf"
private const val PRIVACY_POLICY_URL =
    "https://api.lockboxes.ru:9000/policies/privacy_policy_black.pdf"

internal fun downloadUserAgreement(context: Context) =
    downloadDocument(
        context = context,
        uri = USER_AGREEMENT_URL.toUri(),
        fileResourceName = R.string.user_agreement
    )

internal fun downloadPrivacyPolicy(context: Context) =
    downloadDocument(
        context = context,
        uri = PRIVACY_POLICY_URL.toUri(),
        fileResourceName = R.string.privacy_policy
    )

internal fun downloadDocument(context: Context, uri: Uri, fileName: String) {
    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val destinationUri = context.externalCacheDir ?: return

    val request = DownloadManager.Request(uri).apply {
        setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        setAllowedOverRoaming(false)
        setTitle(fileName)
        setDestinationUri(destinationUri.toUri())
        setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    }

    downloadManager.enqueue(request)
}

internal fun downloadDocument(context: Context, uri: Uri, @StringRes fileResourceName: Int) {
    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    val destinationUri = context.externalCacheDir ?: return
    val title = context.getString(fileResourceName)

    val request = DownloadManager.Request(uri).apply {
        setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        setAllowedOverRoaming(false)
        setTitle(title)
        setDestinationUri(destinationUri.toUri())
        setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    }

    downloadManager.enqueue(request)
}
