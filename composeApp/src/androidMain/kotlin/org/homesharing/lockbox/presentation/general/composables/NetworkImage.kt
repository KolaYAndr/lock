package org.homesharing.lockbox.presentation.general.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import coil3.request.CachePolicy
import coil3.request.ImageRequest
import coil3.request.crossfade
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme

private const val CROSSFADE_DURATION = 300

@Composable
internal fun NetworkImage(url: String, modifier: Modifier = Modifier) {
    val request = ImageRequest.Builder(LocalContext.current)
        .data(url)
        .diskCachePolicy(CachePolicy.DISABLED)
        .memoryCachePolicy(CachePolicy.DISABLED)
        .crossfade(CROSSFADE_DURATION)
        .build()

    AsyncImage(
        model = request,
        contentDescription = null,
        modifier = modifier
            .border(
                width = 0.5.dp,
                color = LockBoxTheme.colors.grey700,
                shape = LockBoxTheme.shape.circleShape
            )
            .background(
                color = LockBoxTheme.colors.grey700,
                shape = LockBoxTheme.shape.circleShape
            )
            .clip(LockBoxTheme.shape.circleShape)
            .size(64.dp),
        contentScale = ContentScale.Crop,
        placeholder = painterResource(R.drawable.user),
        clipToBounds = true,
    )
}
