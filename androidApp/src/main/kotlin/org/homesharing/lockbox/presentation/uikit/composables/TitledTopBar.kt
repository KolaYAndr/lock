package org.homesharing.lockbox.presentation.uikit.composables

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
internal fun TitledTopBar(
    title: String,
    onNavigate: () -> Unit,
    modifier: Modifier = Modifier,
) {
    TopAppBar(
        title = {
            Text(
                text = title,
                style = LockBoxTheme.typography.titleSmall,
                color = LockBoxTheme.colors.grey100,
                modifier = Modifier.padding(horizontal = 4.dp)
            )
        },
        modifier = modifier,
        navigationIcon = {
            IconButton(
                onClick = onNavigate
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_back),
                    contentDescription = null,
                )
            }
        },
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = LockBoxTheme.colors.grey950,
            navigationIconContentColor = LockBoxTheme.colors.grey500,
            titleContentColor = LockBoxTheme.colors.grey100,
        ),
        expandedHeight = TopAppBarDefaults.TopAppBarExpandedHeight
    )
//    val topPadding = WindowInsets.statusBars.only(WindowInsetsSides.Top).asPaddingValues()
//        .calculateTopPadding()
//    Column(
//        modifier = modifier.padding(top = topPadding)
//    ) {
//        Row(
//            modifier = Modifier.fillMaxWidth(),
//            horizontalArrangement = Arrangement.Start,
//            verticalAlignment = Alignment.CenterVertically
//        ) {
//            IconButton(
//                onClick = onNavigate
//            ) {
//                Icon(
//                    painter = painterResource(R.drawable.arrow_back),
//                    contentDescription = null,
//                    tint = LockBoxTheme.colors.grey500
//                )
//            }
//
//            Text(
//                text = title,
//                style = LockBoxTheme.typography.titleSmall,
//                color = LockBoxTheme.colors.grey100,
//                modifier = Modifier.padding(vertical = 16.dp, horizontal = 4.dp)
//            )
//        }
//        LockboxHorizontalDivider(modifier = Modifier.fillMaxWidth())
//    }
}

@Preview
@Composable
private fun PreviewTopBar() {
    LockBoxTheme {
        TitledTopBar(
            title = stringResource(R.string.account),
            onNavigate = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}
