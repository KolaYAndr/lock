//package org.homesharing.lockbox.presentation.uikit.composables.core.inputs
//
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.tooling.preview.Preview
//import org.homesharing.lockbox.R
//import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme
//
//@Composable
//internal fun LockboxTimeInput(time: String, onFieldClick: () -> Unit, modifier: Modifier = Modifier) {
//    LockboxInput(
//        value = time,
//        onValueChange = {},
//        enabled = false,
//        onFieldClick = onFieldClick,
//        placeholderText = stringResource(R.string.choose_time),
//        singleLine = true,
//        modifier = modifier
//    )
//}
//
//@Preview
//@Composable
//private fun PreviewLockboxTimeInput() {
//    LockBoxTheme {
//        LockboxTimeInput(time = "12:00", onFieldClick = {})
//    }
//}
