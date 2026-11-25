package org.homesharing.lockbox.presentation.guest_invitation_flow.guest_invited

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
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import lockbox.sharedui.generated.resources.Res
import lockbox.sharedui.generated.resources.create_one_more
import lockbox.sharedui.generated.resources.go_to_check_in
import lockbox.sharedui.generated.resources.go_to_main_screen
import lockbox.sharedui.generated.resources.invitation_sent
import lockbox.sharedui.generated.resources.ic_letter
import lockbox.sharedui.generated.resources.we_sent_link_to_guest_email
import org.homesharing.lockbox.presentation.uikit.composable.button.HomesharingButton
import org.homesharing.lockbox.presentation.uikit.composable.button.HomesharingButtonDefaults
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme

@Composable
internal fun GuestInvitedScreen() {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(HomesharingTheme.colors.white)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(Res.drawable.ic_letter),
            contentDescription = null,
            modifier = Modifier
                .size(200.dp)
                .padding(horizontal = 16.dp),
        )

        Text(
            text = stringResource(Res.string.invitation_sent),
            textAlign = TextAlign.Center,
            fontWeight = FontWeight.Bold,
            style = HomesharingTheme.typography.titleLarge,
            modifier = Modifier
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp)
        )

        Text(
            text = stringResource(Res.string.we_sent_link_to_guest_email),
            textAlign = TextAlign.Center,
            style = HomesharingTheme.typography.bodyMedium,
            modifier = Modifier
                .padding(top = 8.dp)
                .padding(horizontal = 16.dp)
        )

        HomesharingButton(
            title = stringResource(Res.string.go_to_check_in),
            onClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
                .padding(horizontal = 11.dp)
        )

        HomesharingButton(
            title = stringResource(Res.string.create_one_more),
            onClick = {},
            style = HomesharingButtonDefaults.secondary(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp)
                .padding(horizontal = 11.dp)
        )

        HomesharingButton(
            title = stringResource(Res.string.go_to_main_screen),
            onClick = {},
            style = HomesharingButtonDefaults.secondary(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 8.dp, bottom = 16.dp)
                .padding(horizontal = 11.dp)
                .navigationBarsPadding()
        )
    }
}

@Preview
@Composable
private fun PreviewGuestInvitedScreen() {
    HomesharingTheme {
        GuestInvitedScreen()
    }
}