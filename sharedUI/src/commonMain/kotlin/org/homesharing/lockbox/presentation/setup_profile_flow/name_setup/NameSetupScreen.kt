package org.homesharing.lockbox.presentation.setup_profile_flow.name_setup

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardCapitalization
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LocalLifecycleOwner
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import lockbox.sharedui.generated.resources.Res
import lockbox.sharedui.generated.resources.ic_close
import lockbox.sharedui.generated.resources.how_to_address_you
import lockbox.sharedui.generated.resources.placeholder_first_name
import lockbox.sharedui.generated.resources.placeholder_last_name
import lockbox.sharedui.generated.resources.tell_us_about_yourself
import lockbox.sharedui.generated.resources.save_and_proceed
import lockbox.sharedui.generated.resources.set_your_name
import org.homesharing.lockbox.presentation.ActionOnSharedFlow
import org.homesharing.lockbox.presentation.uikit.composable.button.HomesharingButton
import org.homesharing.lockbox.presentation.uikit.composable.input.HomesharingInput
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun NameSetupScreen(
    viewModel: NameSetupViewModel = koinViewModel(),
    onProceed: () -> Unit,
) {
    val profileSetup by viewModel.profileSetup.collectAsStateWithLifecycle()

    ActionOnSharedFlow(
        lifecycleOwner = LocalLifecycleOwner.current,
        flow = viewModel.onSuccess,
        onFlowCollected = { onProceed() }
    )

    NameSetupScreen(
        nameSetupState = profileSetup,
        onIntent = viewModel::onIntent,
        modifier = Modifier.fillMaxSize(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun NameSetupScreen(
    nameSetupState: NameSetupState,
    onIntent: (NameSetupAction) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.background(HomesharingTheme.colors.white),
        verticalArrangement = Arrangement.Top,
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(Res.string.tell_us_about_yourself),
                    style = HomesharingTheme.typography.bodyMedium,
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = HomesharingTheme.colors.white,
                titleContentColor = HomesharingTheme.colors.black,
            ),
            modifier = Modifier.statusBarsPadding()
        )

        Text(
            text = stringResource(Res.string.how_to_address_you),
            style = HomesharingTheme.typography.titleLarge,
            color = HomesharingTheme.colors.black,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp)
        )

        Text(
            text = stringResource(Res.string.set_your_name),
            style = HomesharingTheme.typography.bodyMedium,
            color = HomesharingTheme.colors.black.copy(alpha = .5f),
            modifier = Modifier
                .padding(top = 8.dp)
                .padding(horizontal = 16.dp)
        )

        val firstNameFocusRequester = remember { FocusRequester() }
        val lastNameFocusRequester = remember { FocusRequester() }
        LaunchedEffect(Unit) {
            firstNameFocusRequester.requestFocus()
        }

        HomesharingInput(
            value = nameSetupState.firstName,
            onValueChange = { onIntent(NameSetupAction.FirstNameChanged(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 19.dp)
                .padding(horizontal = 11.dp)
                .focusRequester(firstNameFocusRequester),
            keyboardActions = KeyboardActions(
                onNext = { lastNameFocusRequester.requestFocus() }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Next,
                capitalization = KeyboardCapitalization.Words
            ),
            placeholderText = stringResource(Res.string.placeholder_first_name),
            trailingIcon = painterResource(Res.drawable.ic_close),
            trailingIconAction = {
                onIntent(NameSetupAction.FirstNameChanged(""))
            }
        )

        HomesharingInput(
            value = nameSetupState.lastName,
            onValueChange = { onIntent(NameSetupAction.LastNameChanged(it)) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 11.dp)
                .padding(horizontal = 11.dp)
                .focusRequester(lastNameFocusRequester),
            keyboardActions = KeyboardActions(
                onDone = { onIntent(NameSetupAction.Proceed) }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Words
            ),
            placeholderText = stringResource(Res.string.placeholder_last_name),
            trailingIcon = painterResource(Res.drawable.ic_close),
            trailingIconAction = {
                onIntent(NameSetupAction.LastNameChanged(""))
            }
        )

        HomesharingButton(
            title = stringResource(Res.string.save_and_proceed),
            onClick = { onIntent(NameSetupAction.Proceed) },
            enabled = nameSetupState.firstName.isNotBlank() && nameSetupState.lastName.isNotBlank(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 11.dp)
                .padding(horizontal = 11.dp)
        )
    }
}

@Preview
@Composable
private fun PreviewProfileSetupScreen() {
    HomesharingTheme {
        NameSetupScreen(
            nameSetupState = NameSetupState(
                firstName = "Kolya",
                lastName = "Andreev",
            ),
            onIntent = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}
