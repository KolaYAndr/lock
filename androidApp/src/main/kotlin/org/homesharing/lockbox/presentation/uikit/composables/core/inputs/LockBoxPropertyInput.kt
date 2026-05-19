//package org.homesharing.lockbox.presentation.uikit.composables.core.inputs
//
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.text.input.ImeAction
//import androidx.compose.ui.text.input.KeyboardType
//import androidx.compose.ui.tooling.preview.Preview
//import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme
//
//@Composable
//internal fun LockBoxPropertyInput(
//    value: String,
//    onValueChange: (String) -> Unit,
//    placeholderText: String,
//    modifier: Modifier = Modifier,
//    keyboardOptions: KeyboardOptions = KeyboardOptions(
//        keyboardType = KeyboardType.Text,
//        showKeyboardOnFocus = true,
//        imeAction = ImeAction.Done,
//    ),
//    keyboardActions: KeyboardActions = KeyboardActions()
//) {
//    LockboxInput(
//        value = value,
//        onValueChange = onValueChange,
//        enabled = true,
//        placeholderText = placeholderText,
//        keyboardOptions = keyboardOptions,
//        keyboardActions = keyboardActions,
//        modifier = modifier
//    )
//}
//
//@Composable
//internal fun LockBoxPropertyInput(
//    value: String,
//    modifier: Modifier
//) {
//    LockboxInput(
//        value = value,
//        onValueChange = {},
//        enabled = false,
//        placeholderText = "",
//        modifier = modifier
//    )
//}
//
//@Preview
//@Composable
//private fun PreviewPropertyInput() {
//    LockBoxTheme {
//        LockBoxPropertyInput(
//            value = "Dom",
//            onValueChange = {},
//            placeholderText = "",
//            modifier = Modifier.fillMaxWidth()
//        )
//    }
//}
