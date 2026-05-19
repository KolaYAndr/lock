package org.homesharing.lockbox.presentation.profile_flow.profile_setup

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
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import lockbox.sharedui.generated.resources.Res
import lockbox.sharedui.generated.resources.ic_close
import lockbox.sharedui.generated.resources.full_name
import lockbox.sharedui.generated.resources.how_to_address_you
import lockbox.sharedui.generated.resources.proceed
import lockbox.sharedui.generated.resources.profile_setup
import org.homesharing.lockbox.presentation.uikit.composable.button.HomesharingButton
import org.homesharing.lockbox.presentation.uikit.composable.input.HomesharingInput
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun ProfileSetupScreen(
    viewModel: ProfileSetupViewModel = koinViewModel(),
    onProceed: () -> Unit,
) {
    val fullName by viewModel.fullName.collectAsStateWithLifecycle()
    ProfileSetupScreen(
        fullName = fullName,
        onChangeFullName = viewModel::onChangeFullName,
        onFinish = onProceed,
        modifier = Modifier.fillMaxSize(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ProfileSetupScreen(
    fullName: String,
    onChangeFullName: (String) -> Unit,
    onFinish: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.background(HomesharingTheme.colors.white),
        verticalArrangement = Arrangement.Top,
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(Res.string.profile_setup),
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

        val focusRequester = remember { FocusRequester() }
        LaunchedEffect(Unit) {
            focusRequester.requestFocus()
        }

        HomesharingInput(
            value = fullName,
            onValueChange = onChangeFullName,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .padding(horizontal = 11.dp)
                .focusRequester(focusRequester),
            keyboardActions = KeyboardActions(
                onDone = { onFinish() }
            ),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                capitalization = KeyboardCapitalization.Words
            ),
            placeholderText = stringResource(Res.string.full_name),
            trailingIcon = painterResource(Res.drawable.ic_close),
            trailingIconAction = {
                onChangeFullName("")
            }
        )

        HomesharingButton(
            title = stringResource(Res.string.proceed),
            onClick = onFinish,
            enabled = fullName.isNotEmpty(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .padding(horizontal = 11.dp)
        )
    }
}

@Preview
@Composable
private fun PreviewProfileSetupScreen() {
    HomesharingTheme {
        ProfileSetupScreen(
            fullName = "Kolya",
            onChangeFullName = {},
            onFinish = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}