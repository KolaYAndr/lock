//package org.homesharing.lockbox.presentation.uikit.composables.core.inputs
//
//import androidx.compose.foundation.text.KeyboardActions
//import androidx.compose.foundation.text.KeyboardOptions
//import androidx.compose.material3.Icon
//import androidx.compose.material3.IconButton
//import androidx.compose.material3.IconButtonDefaults
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.input.VisualTransformation
//import androidx.compose.ui.tooling.preview.Preview
//import org.homesharing.lockbox.R
//import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme
//
//@Composable
//internal fun LockboxCredentialsInput(
//    text: String,
//    onTextChange: (String) -> Unit,
//    placeholderText: String,
//    canSave: Boolean,
//    onCancel: () -> Unit,
//    onFinish: () -> Unit,
//    modifier: Modifier = Modifier,
//    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
//    keyboardActions: KeyboardActions = KeyboardActions.Default,
//) {
//    LockboxInput(
//        value = text,
//        onValueChange = onTextChange,
//        placeholderText = placeholderText,
//        trailingContent = {
//            if (canSave) {
//                IconButton(
//                    onClick = onCancel,
//                    colors = IconButtonDefaults.iconButtonColors(
//                        containerColor = LockBoxTheme.colors.grey800,
//                        contentColor = LockBoxTheme.colors.grey100
//                    )
//                ) {
//                    Icon(
//                        painter = painterResource(R.drawable.close),
//                        contentDescription = null
//                    )
//                }
//
//                IconButton(
//                    onClick = onFinish,
//                    colors = IconButtonDefaults.iconButtonColors(
//                        containerColor = LockBoxTheme.colors.grey800,
//                        contentColor = LockBoxTheme.colors.grey100
//                    )
//                ) {
//                    Icon(
//                        painter = painterResource(R.drawable.check),
//                        contentDescription = null
//                    )
//                }
//            }
//        },
//        visualTransformation = VisualTransformation.None,
//        keyboardOptions = keyboardOptions,
//        keyboardActions = keyboardActions,
//        modifier = modifier
//    )
//}
//
//@Preview
//@Composable
//private fun PreviewLockboxCredentialsInput() {
//    LockBoxTheme {
//        LockboxCredentialsInput(
//            text = "Aga",
//            onTextChange = {},
//            canSave = true,
//            onCancel = {},
//            onFinish = {},
//            placeholderText = "Vashe imya"
//        )
//    }
//}
