package org.homesharing.lockbox.presentation.guest_invitaion_flow.check_in_setup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import lockbox.sharedui.generated.resources.Res
import lockbox.sharedui.generated.resources.check_in_report
import lockbox.sharedui.generated.resources.check_in_setup
import lockbox.sharedui.generated.resources.check_out_report
import lockbox.sharedui.generated.resources.guest_invitation
import lockbox.sharedui.generated.resources.guest_will_send_report_before_checking_out
import lockbox.sharedui.generated.resources.guest_will_send_report_when_checking_in
import lockbox.sharedui.generated.resources.guest_will_sing_deal_in_app
import lockbox.sharedui.generated.resources.how_to_find_entrance_and_etc
import lockbox.sharedui.generated.resources.ic_chevron_left
import lockbox.sharedui.generated.resources.proceed
import lockbox.sharedui.generated.resources.request_passport
import lockbox.sharedui.generated.resources.sign_deal_online
import lockbox.sharedui.generated.resources.we_will_request_passport_before_check_in
import lockbox.sharedui.generated.resources.we_will_send_guest_check_in_guide
import org.homesharing.lockbox.domain.model.check_in_setup.CheckInSetup
import org.homesharing.lockbox.presentation.uikit.composable.button.HomesharingButton
import org.homesharing.lockbox.presentation.uikit.composable.checkbox.HomesharingCheckBoxItem
import org.homesharing.lockbox.presentation.uikit.composable.input.HomesharingInput
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun CheckInSetupScreen(
    viewModel: CheckInViewModel = koinViewModel(),
    onProceed: () -> Unit,
    onNavBack: () -> Unit,
) {
    val checkInState by viewModel.checkInState.collectAsStateWithLifecycle()
    CheckInSetupScreen(
        uiState = checkInState,
        onCheck = viewModel::onCheck,
        onChangeComment = viewModel::onChangeComment,
        onProceed = onProceed,
        onNavBack = onNavBack,
        modifier = Modifier.fillMaxSize(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CheckInSetupScreen(
    uiState: CheckInUiState,
    onCheck: (CheckInSetup) -> Unit,
    onChangeComment: (String) -> Unit,
    onProceed: () -> Unit,
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
            text = stringResource(Res.string.check_in_setup),
            style = HomesharingTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = HomesharingTheme.colors.black,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        )

        Text(
            text = stringResource(Res.string.we_will_send_guest_check_in_guide),
            style = HomesharingTheme.typography.bodyMedium,
            color = HomesharingTheme.colors.black,
            modifier = Modifier
                .padding(top = 8.dp, bottom = 23.dp)
                .padding(horizontal = 16.dp)
        )

        uiState.checkInOptions.forEach { perk ->
            HomesharingCheckBoxItem(
                title = when (perk) {
                    CheckInSetup.CheckInReport -> stringResource(Res.string.check_in_report)
                    CheckInSetup.CheckOutReport -> stringResource(Res.string.check_out_report)
                    CheckInSetup.RequestPassport -> stringResource(Res.string.request_passport)
                    CheckInSetup.SignDealOnline -> stringResource(Res.string.sign_deal_online)
                },
                subtitle = when (perk) {
                    CheckInSetup.CheckInReport -> stringResource(Res.string.guest_will_send_report_when_checking_in)
                    CheckInSetup.CheckOutReport -> stringResource(Res.string.guest_will_send_report_before_checking_out)
                    CheckInSetup.RequestPassport -> stringResource(Res.string.we_will_request_passport_before_check_in)
                    CheckInSetup.SignDealOnline -> stringResource(Res.string.guest_will_sing_deal_in_app)
                },
                isChecked = perk in uiState.selected,
                onCheck = { onCheck(perk) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 11.dp),
            )
        }

        val focusManager = LocalFocusManager.current
        HomesharingInput(
            value = uiState.commentForGuest,
            onValueChange = onChangeComment,
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
            ),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                }
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 11.dp)
                .padding(top = 18.dp)
                .heightIn(min = 96.dp)
        )

        Text(
            modifier = Modifier
                .padding(top = 4.dp)
                .padding(horizontal = 16.dp),
            text = stringResource(Res.string.how_to_find_entrance_and_etc),
            style = HomesharingTheme.typography.bodySmall,
            color = HomesharingTheme.colors.black.copy(alpha = .5f)
        )

        HomesharingButton(
            title = stringResource(Res.string.proceed),
            onClick = onProceed,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 11.dp, vertical = 19.dp)
                .navigationBarsPadding()
        )
    }
}

@Preview
@Composable
private fun PreviewCheckInScreen() {
    HomesharingTheme {
        CheckInSetupScreen(
            uiState = CheckInUiState.Initial,
            onCheck = {},
            onChangeComment = {},
            onProceed = {},
            onNavBack = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}