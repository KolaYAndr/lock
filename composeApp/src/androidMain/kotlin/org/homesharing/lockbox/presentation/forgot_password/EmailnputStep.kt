package org.homesharing.lockbox.presentation.forgot_password

import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import presentation.composables.EmailInput
import presentation.composables.PolicyAndConditions
import presentation.composables.general.BasicButton
import presentation.theme.LockBoxTheme

@Composable
fun EmailInputStep(
    email: String,
    onEmailChange: (String) -> Unit,
    onConfirmEmail: () -> Unit,
) {
    val emailInteractionSource = remember { MutableInteractionSource() }

    Text(
        text = stringResource(R.string.type_in_email),
        style = LockBoxTheme.typography.titleMedium,
        color = LockBoxTheme.colors.white,
        modifier = Modifier
            .padding(top = 16.dp, start = 16.dp, end = 16.dp)
    )
    Text(
        text = stringResource(R.string.we_will_send_recovery_link),
        style = LockBoxTheme.typography.bodyMedium,
        color = LockBoxTheme.colors.grey500,
        modifier = Modifier
            .padding(top = 8.dp, start = 16.dp, end = 16.dp, bottom = 12.dp)
    )

    Text(
        text = stringResource(R.string.email),
        style = LockBoxTheme.typography.subHeadlineLarge,
        color = LockBoxTheme.colors.grey100,
        modifier = Modifier
            .padding(top = 12.dp, start = 16.dp, end = 16.dp)
    )
    EmailInput(
        email = email,
        onValueChange = { onEmailChange(it) },
        onClearText = { onEmailChange("") },
        interactionSource = emailInteractionSource,
        keyboardOptions = KeyboardOptions(
            keyboardType = KeyboardType.Email,
            imeAction = ImeAction.Done
        ),
        keyboardActions = KeyboardActions(onDone = {}),
        isError = false,
        errorText = "",
        modifier = Modifier.padding(
            top = 6.dp,
            start = 16.dp,
            end = 16.dp,
            bottom = 12.dp
        )
    )

    BasicButton(
        onClick = { onConfirmEmail() },
        text = stringResource(R.string.confirm_email),
        paddingValues = PaddingValues(14.dp),
        modifier = Modifier
            .fillMaxWidth()
            .padding(top = 12.dp, start = 16.dp, end = 16.dp)
    )

    PolicyAndConditions(
        modifier = Modifier
            .padding(top = 2.dp, start = 16.dp, end = 16.dp)
            .clickable {

            }
    )
}
