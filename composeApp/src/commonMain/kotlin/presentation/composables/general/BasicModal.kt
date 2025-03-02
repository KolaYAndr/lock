package presentation.composables.general

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import presentation.theme.LockBoxTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun BasicModal(
    onDismiss: () -> Unit,
    modifier: Modifier = Modifier,
    content: @Composable ColumnScope.() -> Unit
) {
    val sheetState = rememberModalBottomSheetState(
        skipPartiallyExpanded = true
    )
    ModalBottomSheet(
        onDismissRequest = onDismiss,
        sheetState = sheetState,
        containerColor = LockBoxTheme.colors.grey900,
        properties = ModalBottomSheetProperties(shouldDismissOnBackPress = true),
        shape = RoundedCornerShape(topStart = 24.dp, topEnd = 24.dp),
        dragHandle = {},
        modifier = modifier
            .statusBarsPadding()
            .padding(top = 12.dp)
    ) {
        content()
    }
}