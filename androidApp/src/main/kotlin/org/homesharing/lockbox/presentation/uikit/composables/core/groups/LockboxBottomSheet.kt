//package org.homesharing.lockbox.presentation.uikit.composables.core.groups
//
//import androidx.compose.foundation.layout.ColumnScope
//import androidx.compose.foundation.layout.WindowInsets
//import androidx.compose.foundation.layout.navigationBarsPadding
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.ExperimentalMaterial3Api
//import androidx.compose.material3.ModalBottomSheet
//import androidx.compose.material3.ModalBottomSheetProperties
//import androidx.compose.material3.rememberModalBottomSheetState
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.graphics.Shape
//import androidx.compose.ui.unit.dp
//import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme
//
//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//internal fun LockboxBottomSheet(
//    onDismissRequest: () -> Unit,
//    modifier: Modifier = Modifier,
//    shape: Shape = LockBoxTheme.shape.roundedCornerShape24,
//    content: @Composable ColumnScope.() -> Unit,
//) {
//    val sheetState = rememberModalBottomSheetState(
//        skipPartiallyExpanded = true
//    )
//    ModalBottomSheet(
//        onDismissRequest = onDismissRequest,
//        shape = shape,
//        properties = ModalBottomSheetProperties(shouldDismissOnBackPress = true),
//        containerColor = LockBoxTheme.colors.grey900,
//        sheetState = sheetState,
//        contentWindowInsets = { WindowInsets(0.dp) },
//        dragHandle = {},
//        modifier = modifier.padding(start = 8.dp, end = 8.dp, bottom = 8.dp).navigationBarsPadding()
//    ) {
//        content()
//    }
//}
