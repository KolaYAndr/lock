package org.homesharing.lockbox.presentation.guest_invitaion_flow.invitation_details

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
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
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import lockbox.sharedui.generated.resources.Res
import lockbox.sharedui.generated.resources.check_in_details
import lockbox.sharedui.generated.resources.check_in_time_placeholder
import lockbox.sharedui.generated.resources.check_out_time_placeholder
import lockbox.sharedui.generated.resources.deposit_placeholder
import lockbox.sharedui.generated.resources.guest_invitation
import lockbox.sharedui.generated.resources.guest_number_placeholder
import lockbox.sharedui.generated.resources.ic_chevron_left
import lockbox.sharedui.generated.resources.proceed
import org.homesharing.lockbox.presentation.ActionOnSharedFlow
import org.homesharing.lockbox.presentation.uikit.composable.button.HomesharingButton
import org.homesharing.lockbox.presentation.uikit.composable.input.HomesharingDateInput
import org.homesharing.lockbox.presentation.uikit.composable.input.HomesharingInput
import org.homesharing.lockbox.presentation.uikit.composable.input.HomesharingTimeInput
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun GuestInvitationScreen(
    viewModel: GuestInvitationViewModel = koinViewModel(),
    onProceed: () -> Unit,
    onNavBack: () -> Unit,
) {
    ActionOnSharedFlow(
        lifecycleOwner = LocalLifecycleOwner.current,
        flow = viewModel.proceed
    ) {
        onProceed()
    }
    val uiState by viewModel.guestInvitation.collectAsStateWithLifecycle()
    GuestInvitationScreen(
        uiState = uiState,
        onIntent = viewModel::onIntent,
        onNavBack = onNavBack,
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GuestInvitationScreen(
    uiState: GuestInvitationUiState,
    onIntent: (GuestInvitationIntent) -> Unit,
    onNavBack: () -> Unit,
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
                    text = stringResource(Res.string.guest_invitation),
                    style = HomesharingTheme.typography.bodyMedium,
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = HomesharingTheme.colors.white,
                titleContentColor = HomesharingTheme.colors.black,
            ),
            navigationIcon = {
                IconButton(
                    onClick = onNavBack,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = HomesharingTheme.colors.black.copy(alpha = .5f)
                    )
                ) {
                    Icon(
                        painter = painterResource(Res.drawable.ic_chevron_left),
                        contentDescription = null,
                    )
                }
            },
            modifier = Modifier.statusBarsPadding()
        )

        Text(
            text = stringResource(Res.string.check_in_details),
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
            onDateRangeChanged = { start, end ->
                onIntent(
                    GuestInvitationIntent.DateRangeChanged(
                        start,
                        end
                    )
                )
            },
            onClearDates = { onIntent(GuestInvitationIntent.ClearDates) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 11.dp)
                .padding(top = 19.dp)
        )

        val checkOutRequester = remember { FocusRequester() }
        val guestNumberRequester = remember { FocusRequester() }
        val depositRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 11.dp)
                .padding(top = 11.dp),
            horizontalArrangement = Arrangement.spacedBy(11.dp, Alignment.Start),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            HomesharingTimeInput(
                value = uiState.checkInTime,
                onValueChange = { onIntent(GuestInvitationIntent.CheckInTimeChanged(it)) },
                placeholderText = stringResource(Res.string.check_in_time_placeholder),
                keyboardActions = KeyboardActions(
                    onNext = { checkOutRequester.requestFocus() }
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number,
                ),
                modifier = Modifier.weight(1f)
            )

            HomesharingTimeInput(
                value = uiState.checkOutTime,
                onValueChange = { onIntent(GuestInvitationIntent.CheckOutTimeChanged(it)) },
                placeholderText = stringResource(Res.string.check_out_time_placeholder),
                keyboardActions = KeyboardActions(
                    onNext = { guestNumberRequester.requestFocus() }
                ),
                keyboardOptions = KeyboardOptions(
                    imeAction = ImeAction.Next,
                    keyboardType = KeyboardType.Number,
                ),
                modifier = Modifier
                    .weight(1f)
                    .focusRequester(checkOutRequester)
            )
        }

        HomesharingInput(
            value = uiState.guestNumber?.toString().orEmpty(),
            onValueChange = { onIntent(GuestInvitationIntent.GuestNumberChanged(it)) },
            placeholderText = stringResource(Res.string.guest_number_placeholder),
            keyboardActions = KeyboardActions(
                onNext = { depositRequester.requestFocus() }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                keyboardType = KeyboardType.Number,
            ),
            modifier = Modifier
                .padding(horizontal = 11.dp)
                .padding(top = 11.dp)
                .fillMaxWidth()
                .focusRequester(guestNumberRequester)
        )

        HomesharingInput(
            value = uiState.deposit?.toString().orEmpty(),
            onValueChange = { onIntent(GuestInvitationIntent.DepositChanged(it)) },
            placeholderText = stringResource(Res.string.deposit_placeholder),
            keyboardActions = KeyboardActions(
                onDone = { focusManager.clearFocus() }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Number,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 11.dp)
                .padding(top = 11.dp)
                .focusRequester(depositRequester)
        )

        HomesharingButton(
            title = stringResource(Res.string.proceed),
            enabled = uiState.isProceedEnabled,
            onClick = { onIntent(GuestInvitationIntent.ProceedClicked) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 11.dp)
                .padding(top = 19.dp, bottom = 11.dp)
                .navigationBarsPadding()
        )
    }
}

@Preview
@Composable
private fun PreviewGuestInvitationScreen() {
    HomesharingTheme {
        GuestInvitationScreen(
            uiState = GuestInvitationUiState.Initial,
            onIntent = {},
            onNavBack = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}