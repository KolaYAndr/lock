package presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextDecoration
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import presentation.theme.LockBoxTheme

const val POLICY_TAG = "policy"
const val CONDITION_TAG = "condition"

@Composable
fun PolicyAndConditions(modifier: Modifier = Modifier) {
    val annotatedString = buildAnnotatedString {
        append(stringResource(R.string.by_pressing_you_agree))

        pushStringAnnotation(tag = CONDITION_TAG, annotation = "") //Todo
        withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
            append(stringResource(R.string.with_service_conditions))
        }
        pop()

        append(stringResource(R.string.and))

        pushStringAnnotation(tag = POLICY_TAG, annotation = "") //Todo
        withStyle(SpanStyle(textDecoration = TextDecoration.Underline)) {
            append(stringResource(R.string.with_privacy_policy))
        }
        pop()
    }

    Text(
        text = annotatedString,
        style = LockBoxTheme.typography.footnote,
        color = LockBoxTheme.colors.grey700,
        textAlign = TextAlign.Center,
        modifier = modifier
    )
}

@Preview
@Composable
fun PreviewPolicy() {
    LockBoxTheme {
        Box(
            modifier = Modifier.background(LockBoxTheme.colors.grey900)
        ) {
            PolicyAndConditions(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(4.dp)
            )
        }
    }
}
