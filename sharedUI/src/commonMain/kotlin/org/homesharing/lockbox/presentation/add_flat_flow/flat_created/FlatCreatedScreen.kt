package org.homesharing.lockbox.presentation.add_flat_flow.flat_created

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lockbox.sharedui.generated.resources.Res
import lockbox.sharedui.generated.resources.add_guest
import lockbox.sharedui.generated.resources.flat_created
import lockbox.sharedui.generated.resources.go_to_flat
import lockbox.sharedui.generated.resources.ic_flat
import lockbox.sharedui.generated.resources.now_you_can_add_guest
import org.homesharing.lockbox.presentation.uikit.composable.button.HomesharingButton
import org.homesharing.lockbox.presentation.uikit.composable.button.HomesharingButtonDefaults
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun FlatCreatedScreen(
    onProceed: () -> Unit,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HomesharingTheme.colors.white)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(Res.drawable.ic_flat),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .padding(horizontal = 16.dp),
        )

        Text(
            text = stringResource(Res.string.flat_created),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = HomesharingTheme.typography.titleLarge,
            modifier = Modifier
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp)
        )

        Text(
            text = stringResource(Res.string.now_you_can_add_guest),
            textAlign = TextAlign.Center,
            style = HomesharingTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(top = 8.dp)
                .padding(horizontal = 16.dp)
        )

        HomesharingButton(
            title = stringResource(Res.string.add_guest),
            onClick = onProceed,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
                .padding(horizontal = 16.dp)
        )

        HomesharingButton(
            title = stringResource(Res.string.go_to_flat),
            onClick = {},
            style = HomesharingButtonDefaults.secondary(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 16.dp)
                .padding(horizontal = 16.dp)
                .navigationBarsPadding()
        )
    }
}

@Preview
@Composable
private fun PreviewFlatCreatedScreen() {
    HomesharingTheme {
        FlatCreatedScreen { }
    }
}