package org.homesharing.lockbox.presentation.uikit.composable.input

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import lockbox.sharedui.generated.resources.Res
import lockbox.sharedui.generated.resources.ic_close
import lockbox.sharedui.generated.resources.ic_search
import lockbox.sharedui.generated.resources.search_placeholder
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource

@Composable
internal fun HomesharingSearch(
    value: String,
    onValueChange: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    HomesharingInput(
        value = value,
        onValueChange = onValueChange,
        placeholderText = stringResource(Res.string.search_placeholder),
        trailingIcon = painterResource(Res.drawable.ic_close),
        trailingIconAction = if (value.isNotBlank()) {
            { onValueChange("") }
        } else null,
        leadingIcon = painterResource(Res.drawable.ic_search),
        modifier = modifier,
    )
}

@Preview
@Composable
private fun HomesharingSearchPreview() {
    HomesharingTheme {
        HomesharingSearch(
            value = "",
            onValueChange = { },
            modifier = Modifier.fillMaxWidth()
        )
    }
}
