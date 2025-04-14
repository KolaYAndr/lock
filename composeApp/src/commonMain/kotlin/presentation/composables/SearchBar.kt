package presentation.composables

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import presentation.theme.LockBoxTheme

//@Composable
//fun SearchBar(
//    search: String,
//    onSearchChange: (String) -> Unit,
//    placeholderText: String,
//    onDoneAction: () -> Unit,
//    modifier: Modifier = Modifier
//) {
//    TextField(
//        value = search,
//        onValueChange = { onSearchChange(it) },
//        modifier = modifier,
//        shape = LockBoxTheme.shape.roundedCornerShape999,
//        colors = TextFieldDefaults.colors(
//            focusedTextColor = LockBoxTheme.colors.grey100,
//            unfocusedTextColor = LockBoxTheme.colors.grey100,
//            focusedContainerColor = LockBoxTheme.colors.grey800,
//            unfocusedContainerColor = LockBoxTheme.colors.grey800,
//            unfocusedPlaceholderColor = LockBoxTheme.colors.grey500,
//            focusedPlaceholderColor = LockBoxTheme.colors.grey500,
//            cursorColor = LockBoxTheme.colors.grey100,
//            focusedIndicatorColor = Color.Transparent,
//            unfocusedIndicatorColor = Color.Transparent,
//        ),
//        textStyle = LockBoxTheme.typography.bodyMedium,
//        keyboardOptions = KeyboardOptions(
//            imeAction = ImeAction.Done,
//            keyboardType = KeyboardType.Text,
//            showKeyboardOnFocus = true
//        ),
//        keyboardActions = KeyboardActions(onDone = { onDoneAction() }),
//        placeholder = {
//            Text(
//                text = placeholderText,
//                style = LockBoxTheme.typography.bodyMedium,
//                color = LockBoxTheme.colors.grey500
//            )
//        },
//        leadingIcon = {
//            Icon(
//                painter = painterResource(R.drawable.search),
//                contentDescription = null,
//                tint = LockBoxTheme.colors.grey500
//            )
//        }
//    )
//}

@Composable
fun SearchBar(
    search: String,
    onSearchChange: (String) -> Unit,
    placeholderText: String,
    onDoneAction: () -> Unit,
    enabled: Boolean,
    modifier: Modifier = Modifier
) {
    Box(
        modifier = modifier
            .background(LockBoxTheme.colors.grey800, LockBoxTheme.shape.roundedCornerShape999)
            .padding(horizontal = 12.dp, vertical = 8.dp)
    ) {
        BasicTextField(
            value = search,
            onValueChange = onSearchChange,
            textStyle = LockBoxTheme.typography.bodyMedium.copy(color = LockBoxTheme.colors.grey100),
            cursorBrush = SolidColor(LockBoxTheme.colors.grey100),
            keyboardOptions = KeyboardOptions(
                imeAction = ImeAction.Done,
                keyboardType = KeyboardType.Text,
                showKeyboardOnFocus = true
            ),
            enabled = enabled,
            keyboardActions = KeyboardActions(onDone = { onDoneAction() }),
            decorationBox = { innerTextField ->
                Row(
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth()
                ) {
                    Icon(
                        painter = painterResource(R.drawable.search),
                        contentDescription = null,
                        tint = LockBoxTheme.colors.grey500,
                    )
                    Box(
                        modifier = Modifier
                        .weight(1f)
                        .padding(horizontal = 8.dp)
                    ) {
                        if (search.isEmpty()) {
                            Text(
                                text = placeholderText,
                                style = LockBoxTheme.typography.bodyMedium,
                                color = LockBoxTheme.colors.grey500,
                            )
                        }
                        innerTextField()
                    }
                }
            }
        )
    }
}

