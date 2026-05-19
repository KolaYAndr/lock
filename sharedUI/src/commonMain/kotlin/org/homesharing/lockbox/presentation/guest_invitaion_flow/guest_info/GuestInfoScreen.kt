package org.homesharing.lockbox.presentation.guest_invitaion_flow.guest_info

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
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
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalFocusManager
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import lockbox.sharedui.generated.resources.Res
import lockbox.sharedui.generated.resources.ic_chevron_left
import lockbox.sharedui.generated.resources.ic_close
import lockbox.sharedui.generated.resources.full_name
import lockbox.sharedui.generated.resources.guest_info
import lockbox.sharedui.generated.resources.guest_invitation
import lockbox.sharedui.generated.resources.placeholder_email
import lockbox.sharedui.generated.resources.proceed
import lockbox.sharedui.generated.resources.will_send_invitation_on_that_email
import org.homesharing.lockbox.presentation.uikit.composable.button.HomesharingButton
import org.homesharing.lockbox.presentation.uikit.composable.input.HomesharingInput
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun GuestInfoScreen(
    viewModel: GuestInfoViewModel = koinViewModel(),
    onProceed: () -> Unit,
    onNavBack: () -> Unit,
) {
    val guestInfo by viewModel.guestInfo.collectAsStateWithLifecycle()
    GuestInfoScreen(
        guestInfo = guestInfo,
        onChangeEmail = viewModel::updateEmail,
        onChangeName = viewModel::updateFullName,
        onProceed = onProceed,
        onNavBack = onNavBack,
        modifier = Modifier.fillMaxSize(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun GuestInfoScreen(
    guestInfo: GuestInfoUiState,
    onChangeEmail: (String) -> Unit,
    onChangeName: (String) -> Unit,
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
            text = stringResource(Res.string.guest_info),
            style = HomesharingTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = HomesharingTheme.colors.black,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        )


        val emailFocusRequester = remember { FocusRequester() }
        val fullNameFocusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current

        LaunchedEffect(Unit) {
            emailFocusRequester.requestFocus()
        }

        HomesharingInput(
            value = guestInfo.email,
            onValueChange = onChangeEmail,
            placeholderText = stringResource(Res.string.placeholder_email),
            keyboardActions = KeyboardActions(
                onNext = { fullNameFocusRequester.requestFocus() }
            ),
            trailingIcon = painterResource(Res.drawable.ic_close),
            trailingIconAction = { onChangeEmail("") },
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Next,
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 11.dp)
                .padding(top = 24.dp)
                .focusRequester(emailFocusRequester)
        )

        Text(
            text = stringResource(Res.string.will_send_invitation_on_that_email),
            style = HomesharingTheme.typography.bodySmall,
            color = HomesharingTheme.colors.black.copy(alpha = .5f),
            modifier = Modifier
                .padding(top = 4.dp)
                .padding(horizontal = 16.dp)
        )

        HomesharingInput(
            value = guestInfo.fullName,
            onValueChange = onChangeName,
            placeholderText = stringResource(Res.string.full_name),
            keyboardActions = KeyboardActions(
                onDone = {
                    focusManager.clearFocus()
                    onProceed()
                }
            ),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Email,
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Words
            ),
            trailingIcon = painterResource(Res.drawable.ic_close),
            trailingIconAction = { onChangeName("") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 11.dp)
                .padding(top = 16.dp)
                .focusRequester(fullNameFocusRequester)
        )

        HomesharingButton(
            title = stringResource(Res.string.proceed),
            onClick = onProceed,
            enabled = guestInfo.fullName.isNotEmpty() && guestInfo.email.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 11.dp)
                .padding(top = 24.dp, bottom = 16.dp)
                .navigationBarsPadding()
        )
    }
}

@Preview
@Composable
private fun PreviewGuestInfoScreen() {
    HomesharingTheme {
        GuestInfoScreen(
            guestInfo = GuestInfoUiState.Initial,
            onChangeEmail = {},
            onChangeName = {},
            onProceed = {},
            onNavBack = {},
            modifier = Modifier.fillMaxSize(),
        )
    }
}