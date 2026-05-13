package org.homesharing.lockbox.presentation_v2.profile_flow.profile_setup

import androidx.activity.compose.BackHandler
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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation_v2.uikit.composable.button.HomesharingButton
import org.homesharing.lockbox.presentation_v2.uikit.composable.input.HomesharingInput
import org.homesharing.lockbox.presentation_v2.uikit.theme.HomesharingTheme
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun ProfileSetupScreen(
    viewModel: ProfileSetupViewModel = koinViewModel(),
    onProceed: () -> Unit,
) {
    BackHandler(enabled = false) { }
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
                    text = stringResource(R.string.profile_setup),
                    style = HomesharingTheme.typography.bodyLarge,
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = HomesharingTheme.colors.white,
                titleContentColor = HomesharingTheme.colors.black,
            ),
            modifier = Modifier.statusBarsPadding()
        )

        Text(
            text = stringResource(R.string.how_to_address_you),
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
                imeAction = ImeAction.Done
            ),
            placeholderText = stringResource(R.string.full_name),
            trailingIcon = painterResource(R.drawable.close),
            trailingIconAction = {
                onChangeFullName("")
            }
        )

        HomesharingButton(
            title = stringResource(R.string.proceed),
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