package org.homesharing.lockbox.presentation.general.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.minimumInteractiveComponentSize
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.LinkAnnotation
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.TextLinkStyles
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme

private const val POLICY_TAG = "policy"
private const val CONDITION_TAG = "condition"

@Composable
internal fun PolicyAndConditions(
    onConditionsClick: () -> Unit,
    onPolicyClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    val annotatedString = buildAnnotatedString {
        append(stringResource(R.string.by_pressing_you_agree))

        pushLink(
            link = LinkAnnotation.Clickable(
                tag = CONDITION_TAG,
                styles = TextLinkStyles(
                    style = SpanStyle(
                        textDecoration = TextDecoration.Underline
                    ),
                    pressedStyle = SpanStyle(
                        color = LockBoxTheme.colors.grey400,
                    ),
                    focusedStyle = SpanStyle(
                        color = LockBoxTheme.colors.grey500,
                    ),
                    hoveredStyle = SpanStyle(
                        color = LockBoxTheme.colors.grey500,
                    ),
                ),
                linkInteractionListener = { onConditionsClick() }
            )
        )
        append(stringResource(R.string.with_service_conditions))
        pop()

        append(stringResource(R.string.and))

        pushLink(
            link = LinkAnnotation.Clickable(
                tag = POLICY_TAG,
                styles = TextLinkStyles(
                    style = SpanStyle(
                        textDecoration = TextDecoration.Underline
                    ),
                    pressedStyle = SpanStyle(
                        color = LockBoxTheme.colors.grey400,
                    ),
                    focusedStyle = SpanStyle(
                        color = LockBoxTheme.colors.grey500,
                    ),
                    hoveredStyle = SpanStyle(
                        color = LockBoxTheme.colors.grey500,
                    ),
                ),
                linkInteractionListener = { onPolicyClick() }
            )
        )
        append(stringResource(R.string.with_privacy_policy))
        pop()
    }

    Text(
        text = annotatedString,
        style = LockBoxTheme.typography.footnote,
        color = LockBoxTheme.colors.grey700,
        textAlign = TextAlign.Center,
        modifier = modifier.minimumInteractiveComponentSize()
    )
}

@Preview
@Composable
private fun PreviewPolicy() {
    LockBoxTheme {
        Box(
            modifier = Modifier.background(LockBoxTheme.colors.grey900)
        ) {
            PolicyAndConditions(
                {},
                {},
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp),
            )
        }
    }
}
