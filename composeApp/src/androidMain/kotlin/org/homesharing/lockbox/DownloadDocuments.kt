package org.homesharing.lockbox

import android.app.DownloadManager
import android.content.Context
import android.net.Uri
import android.os.Environment

private const val USER_AGREEMENT_URL = "https://api.lockboxes.ru:9000/policies/user_agreement_black.pdf"
private const val PRIVACY_POLICY_URL = "https://api.lockboxes.ru:9000/policies/privacy_policy_black.pdf"
private const val USER_AGREEMENT_PDF = "lockbox_user_agreement.pdf"
private const val PRIVACY_POLICY_PDF = "lockbox_privacy_policy.pdf"

fun downloadUserAgreement(context: Context) {
    val uri = Uri.parse(USER_AGREEMENT_URL)
    val title = context.getString(R.string.user_agreement)
    val request = DownloadManager.Request(uri).apply {
        setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        setAllowedOverRoaming(false)
        setTitle(title)
        setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, USER_AGREEMENT_PDF)
        setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    }

    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    downloadManager.enqueue(request)
}


fun downloadPrivacyPolicy(context: Context) {
    val uri = Uri.parse(PRIVACY_POLICY_URL)
    val title = context.getString(R.string.privacy_policy)
    val request = DownloadManager.Request(uri).apply {
        setAllowedNetworkTypes(DownloadManager.Request.NETWORK_WIFI or DownloadManager.Request.NETWORK_MOBILE)
        setAllowedOverRoaming(false)
        setTitle(title)
        setDestinationInExternalPublicDir(Environment.DIRECTORY_DOWNLOADS, PRIVACY_POLICY_PDF)
        setNotificationVisibility(DownloadManager.Request.VISIBILITY_VISIBLE_NOTIFY_COMPLETED)
    }

    val downloadManager = context.getSystemService(Context.DOWNLOAD_SERVICE) as DownloadManager
    downloadManager.enqueue(request)
}
