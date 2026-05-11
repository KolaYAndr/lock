package org.homesharing.lockbox.presentation_v2.guest_invitaion_flow.invitation_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation_v2.uikit.composable.input.HomesharingDateInput
import org.homesharing.lockbox.presentation_v2.uikit.theme.HomesharingTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun GuestInvitationScreen(
    viewModel: GuestInvitationViewModel = koinViewModel()
) {
    val uiState by viewModel.datesState.collectAsStateWithLifecycle()
    GuestInvitationScreen(
        uiState = uiState,
        onDateSelectionChanged = viewModel::onDateRangeChanged,
        onClearDates = viewModel::clearDates,
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GuestInvitationScreen(
    uiState: GuestInvitationUiState,
    onDateSelectionChanged: (Long?, Long?) -> Unit,
    onClearDates: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(HomesharingTheme.colors.white)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.guest_invitation),
                    style = HomesharingTheme.typography.bodyLarge,
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = HomesharingTheme.colors.white,
                titleContentColor = HomesharingTheme.colors.black,
            ),
            navigationIcon = {
                IconButton(
                    onClick = {},
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = HomesharingTheme.colors.black.copy(alpha = .5f)
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.chevron_left),
                        contentDescription = null,
                    )
                }
            },
            modifier = Modifier.statusBarsPadding()
        )

        Text(
            text = stringResource(R.string.check_in_details),
            style = HomesharingTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = HomesharingTheme.colors.black,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        )

        HomesharingDateInput(
            startDateMillis = uiState.startDateMillis,
            endDateMillis = uiState.endDateMillis,
            onDateRangeChanged = onDateSelectionChanged,
            onClearDates = onClearDates,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 11.dp)
                .padding(top = 24.dp)
        )

    }
}

@Preview
@Composable
private fun PreviewGuestInvitationScreen() {
    HomesharingTheme {
        GuestInvitationScreen(
            uiState = GuestInvitationUiState.Initial,
            onDateSelectionChanged = { _, _ -> },
            onClearDates = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}