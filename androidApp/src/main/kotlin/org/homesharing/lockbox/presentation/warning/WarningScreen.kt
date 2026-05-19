//package org.homesharing.lockbox.presentation.warning
//
//import androidx.activity.compose.BackHandler
//import androidx.compose.foundation.background
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Column
//import androidx.compose.foundation.layout.PaddingValues
//import androidx.compose.foundation.layout.fillMaxSize
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.material3.ButtonDefaults
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import org.homesharing.lockbox.R
//import org.homesharing.lockbox.presentation.uikit.composables.core.buttons.LockboxButton
//import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme
//import org.koin.androidx.compose.koinViewModel
//
//@Composable
//internal fun WarningScreen(
//    warningViewModel: WarningViewModel = koinViewModel(),
//) {
//    BackHandler(enabled = false) { }
//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(LockBoxTheme.colors.grey950)
//            .padding(horizontal = 32.dp),
//        verticalArrangement = Arrangement.Center,
//        horizontalAlignment = Alignment.CenterHorizontally
//    ) {
//        Icon(
//            painter = painterResource(R.drawable.circle_alert),
//            contentDescription = null,
//            tint = LockBoxTheme.colors.grey500,
//            modifier = Modifier.size(40.dp)
//        )
//
//        Text(
//            text = stringResource(R.string.your_account_will_be_deleted),
//            style = LockBoxTheme.typography.titleMedium,
//            color = LockBoxTheme.colors.grey100,
//            textAlign = TextAlign.Center,
//            modifier = Modifier.padding(top = 12.dp)
//        )
//
//        Text(
//            text = stringResource(R.string.deletion_in_couple_of_days),
//            style = LockBoxTheme.typography.bodyMedium,
//            color = LockBoxTheme.colors.grey500,
//            textAlign = TextAlign.Center,
//            modifier = Modifier.padding(top = 8.dp)
//        )
//
//        LockboxButton(
//            onClick = warningViewModel::logOut,
//            text = stringResource(R.string.ready),
//            paddingValues = PaddingValues(10.dp),
//            style = ButtonDefaults.buttonColors(
//                containerColor = LockBoxTheme.colors.grey800,
//                contentColor = LockBoxTheme.colors.grey100
//            ),
//            modifier = Modifier.padding(top = 16.dp)
//        )
//    }
//}
//
//@Preview
//@Composable
//private fun PreviewWarning() {
//    LockBoxTheme {
//        WarningScreen()
//    }
//}
