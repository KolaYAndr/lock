package presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import coil3.compose.AsyncImage
import org.homesharing.lockbox.R
import presentation.theme.LockBoxTheme

@Composable
fun NetworkImage(model: String, modifier: Modifier = Modifier) {
    AsyncImage(
        model = model,
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