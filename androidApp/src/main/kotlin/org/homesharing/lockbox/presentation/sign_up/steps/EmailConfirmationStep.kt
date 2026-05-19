//package org.homesharing.lockbox.presentation.sign_up.steps
//
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.height
//import androidx.compose.foundation.layout.padding
//import androidx.compose.material3.Text
//import androidx.compose.material3.minimumInteractiveComponentSize
//import androidx.compose.runtime.Composable
//import androidx.compose.runtime.LaunchedEffect
//import androidx.compose.runtime.getValue
//import androidx.compose.runtime.mutableStateOf
//import androidx.compose.runtime.remember
//import androidx.compose.runtime.setValue
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.unit.dp
//import kotlinx.coroutines.delay
//import kotlinx.coroutines.isActive
//import org.homesharing.lockbox.R
//import org.homesharing.lockbox.presentation.uikit.composables.HelpModalSheet
//import org.homesharing.lockbox.presentation.uikit.composables.HelpTextWithIcon
//import org.homesharing.lockbox.presentation.uikit.composables.core.buttons.LockboxCountDownButton
//import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme
//
//private const val CHECK_CONFIRMATION_DELAY = 15000L
//
//@Composable
//internal fun EmailConfirmationStep(
//    email: String,
//    onConfirmEmail: () -> Unit,
//    onChangeEmailClick: () -> Unit,
//    onCheckConfirmation: suspend () -> Unit,
//) {
//    LaunchedEffect(Unit) {
//        onConfirmEmail()
//        while (isActive) {
//            delay(CHECK_CONFIRMATION_DELAY)
//            onCheckConfirmation()
//        }
//    }
//
//    var showHelpModal by remember { mutableStateOf(false) }
//    Text(
//        text = stringResource(R.string.check_your_email),
//        style = LockBoxTheme.typography.titleMedium,
//        color = LockBoxTheme.colors.constantWhite,
//        modifier = Modifier.padding(top = 16.dp, start = 16.dp, end = 16.dp)
//    )
//    Text(
//        text = stringResource(R.string.link_sent_to_email),
//        style = LockBoxTheme.typography.bodyMedium,
//        color = LockBoxTheme.colors.grey500,
//        modifier = Modifier.padding(top = 8.dp, start = 16.dp, end = 16.dp)
//    )
//
//    Row(
//        verticalAlignment = Alignment.CenterVertically,
//        horizontalArrangement = Arrangement.Start,
//        modifier = Modifier
//            .fillMaxWidth()
//            .padding(top = 8.dp, start = 16.dp, end = 16.dp)
//    ) {
//        Text(
//            style = LockBoxTheme.typography.headlineMedium,
//            color = LockBoxTheme.colors.grey100,
//            text = email
//        )
//        Text(
//            style = LockBoxTheme.typography.subHeadlineLarge,
//            color = LockBoxTheme.colors.constantWhite,
//            text = stringResource(R.string.change_short),
//            modifier = Modifier
//                .minimumInteractiveComponentSize()
//                .clickable { onChangeEmailClick() }
//        )
//    }
//
//    LockboxCountDownButton(
//        onClick = onConfirmEmail,
//        modifier = Modifier
//            .padding(top = 24.dp, start = 16.dp, end = 16.dp)
//            .fillMaxWidth()
//    )
//
//    HelpTextWithIcon(
//        onClick = { showHelpModal = true },
//        modifier = Modifier
//            .padding(horizontal = 16.dp, vertical = 8.dp)
//            .fillMaxWidth()
//            .height(32.dp)
//    )
//
//    if (showHelpModal) {
//        HelpModalSheet(
//            onDismiss = { showHelpModal = false },
//            modifier = Modifier.fillMaxSize()
//        )
//    }
//}
