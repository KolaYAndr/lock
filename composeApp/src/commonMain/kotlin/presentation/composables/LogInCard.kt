package presentation.composables

import android.widget.Toast
import androidx.compose.foundation.clickable
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import presentation.composables.general.BasicButton
import presentation.composables.general.BasicCard
import presentation.theme.LockBoxTheme

private const val POLICY_TAG = "policy"
private const val CONDITION_TAG = "condition"

@Composable
fun LogInCard(onDismiss: () -> Unit, modifier: Modifier = Modifier) {
    BasicCard(
        modifier = modifier
            .padding(vertical = 4.dp, horizontal = 8.dp)
    ) {
        var email by remember { mutableStateOf("") }
        var password by remember { mutableStateOf("") }
        var isPasswordSeen by remember { mutableStateOf(false) }
        val emailInteractionSource = remember { MutableInteractionSource() }
        val passwordInteractionSource = remember { MutableInteractionSource() }
        val focusRequester = remember { FocusRequester() }
        val focusManager = LocalFocusManager.current
        val keyboardController = LocalSoftwareKeyboardController.current
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                .fillMaxWidth()
        ) {
            Box(modifier = Modifier.fillMaxWidth()) {
                CloseRoundButton(
                    onClick = onDismiss::invoke,
                    modifier = Modifier
                        .align(Alignment.TopEnd)
                        .padding(top = 12.dp, end = 16.dp)
                )

                Icon(
                    painter = painterResource(R.drawable.logo_horizontal),
                    contentDescription = null,
                    tint = LockBoxTheme.colors.white,
                    modifier = Modifier
                        .align(Alignment.TopCenter)
                        .padding(top = 12.dp)
                )
            }


            Text(
                text = stringResource(R.string.log_in_your_profile),
                style = LockBoxTheme.typography.titleMedium,
                color = LockBoxTheme.colors.white,
                modifier = Modifier.padding(top = 12.dp)
            )

            Text(
                text = stringResource(R.string.email),
                style = LockBoxTheme.typography.subHeadlineLarge,
                color = LockBoxTheme.colors.grey100,
                modifier = Modifier
                    .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                    .align(Alignment.Start)
            )
            EmailInput(
                email = email,
                onValueChange = { email = it },
                onClearText = { email = "" },
                interactionSource = emailInteractionSource,
                keyboardActions = KeyboardActions(onNext = { focusRequester.requestFocus() }),
                modifier = Modifier.padding(top = 6.dp, start = 16.dp, end = 16.dp)
            )

            Text(
                text = stringResource(R.string.password),
                style = LockBoxTheme.typography.subHeadlineLarge,
                color = LockBoxTheme.colors.grey100,
                modifier = Modifier
                    .padding(top = 24.dp, start = 16.dp, end = 16.dp)
                    .align(Alignment.Start)
            )
            PasswordInput(
                password = password,
                onValueChange = { password = it },
                onClearText = { password = "" },
                interactionSource = passwordInteractionSource,
                isSeen = isPasswordSeen,
                onSeenChange = { isPasswordSeen = !isPasswordSeen },
                keyboardActions = KeyboardActions(
                    onDone = {
                        keyboardController?.hide()
                        focusManager.clearFocus()
                    }
                ),
                modifier = Modifier
                    .focusRequester(focusRequester)
                    .padding(top = 6.dp, start = 16.dp, end = 16.dp)
            )

            Text(
                text = stringResource(R.string.forgot_password),
                style = LockBoxTheme.typography.subHeadlineMedium,
                color = LockBoxTheme.colors.grey500,
                modifier = Modifier
                    .padding(top = 12.dp, start = 16.dp, end = 16.dp)
                    .align(Alignment.Start)
                    .clickable { /*Todo*/ },
                textDecoration = TextDecoration.Underline
            )

            BasicButton(
                onClick = {},
                text = stringResource(R.string.sing_in),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 20.dp, start = 16.dp, end = 16.dp)
            )

            val annotatedString = buildAnnotatedString {
                append(stringResource(R.string.by_pressing_you_agree))

                pushStringAnnotation(tag = CONDITION_TAG, annotation = "") //Todo
                withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                    append(stringResource(R.string.service_contidions))
                }
                pop()

                append(stringResource(R.string.and))

                pushStringAnnotation(tag = POLICY_TAG, annotation = "") //Todo
                withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
                    append(stringResource(R.string.privacy_policy))
                }
                pop()
            }

            Text(
                text = annotatedString,
                style = LockBoxTheme.typography.footnote,
                color = LockBoxTheme.colors.grey700,
                textAlign = TextAlign.Center,
                modifier = Modifier
                    .padding(top = 2.dp, start = 16.dp, end = 16.dp)
                    .align(Alignment.Start)
                    .clickable {

                    }
            )

            Row(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 28.dp, start = 16.dp, end = 16.dp, bottom = 16.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally)
            ) {
                Text(
                    text = stringResource(R.string.no_account),
                    color = LockBoxTheme.colors.grey500,
                    style = LockBoxTheme.typography.subHeadlineMedium
                )

                Text(
                    text = stringResource(R.string.register),
                    color = LockBoxTheme.colors.grey100,
                    style = LockBoxTheme.typography.subHeadlineLarge,
                    textDecoration = TextDecoration.Underline,
                    modifier = Modifier.clickable { /*Todo*/ }
                )
            }
        }
    }
}

@Preview
@Composable
fun PreviewDialog() {
    LockBoxTheme {
        LogInCard({}, modifier = Modifier.padding(8.dp))
    }
}