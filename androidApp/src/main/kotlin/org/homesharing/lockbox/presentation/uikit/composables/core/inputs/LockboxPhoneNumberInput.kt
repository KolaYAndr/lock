//package org.homesharing.lockbox.presentation.uikit.composables.core.inputs
//
//import androidx.compose.animation.AnimatedVisibility
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.input.VisualTransformation
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import org.homesharing.lockbox.R
//import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme
//
//@Composable
//internal fun LockboxPhoneNumberInput(
//    number: String,
//    onValueChange: (String) -> Unit,
//    onClearText: () -> Unit,
//    errorText: String?,
//    modifier: Modifier = Modifier,
//    keyboardOptions: KeyboardOptions = KeyboardOptions(),
//    keyboardActions: KeyboardActions = KeyboardActions(),
//) {
//    LockboxInput(
//        value = number,
//        onValueChange = onValueChange,
//        placeholderText = stringResource(R.string.phone_number_placeholder),
//        visualTransformation = VisualTransformation.None,
//        keyboardOptions = keyboardOptions,
//        errorText = errorText,
//        keyboardActions = keyboardActions,
//        trailingContent = {
//            AnimatedVisibility(number.isNotEmpty()) {
//                IconButton(onClick = onClearText) {
//                    Icon(
//                        painter = painterResource(R.drawable.close),
//                        null,
//                        tint = LockBoxTheme.colors.grey100
//                    )
//                }
//            }
//        },
//        modifier = modifier
//    )
//}
//
//@Composable
//@Preview
//private fun PreviewNumberLockbox() {
//    LockBoxTheme {
//        LockboxPhoneNumberInput(
//            number = "7904",
//            onValueChange = { },
//            onClearText = { },
//            keyboardActions = KeyboardActions(),
//            keyboardOptions = KeyboardOptions(),
//            errorText = null,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(16.dp)
//        )
//
//    }
//}
