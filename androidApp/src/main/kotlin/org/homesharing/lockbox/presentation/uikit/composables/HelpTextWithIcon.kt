//package org.homesharing.lockbox.presentation.uikit.composables
//
//import androidx.compose.foundation.clickable
//import androidx.compose.foundation.layout.Arrangement
//import androidx.compose.foundation.layout.Row
//import androidx.compose.foundation.layout.fillMaxWidth
//import androidx.compose.foundation.layout.size
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.res.stringResource
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import org.homesharing.lockbox.R
//import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme
//
//@Composable
//internal fun HelpTextWithIcon(onClick: () -> Unit, modifier: Modifier = Modifier) {
//    Row(
//        modifier = modifier.clickable { onClick() },
//        horizontalArrangement = Arrangement.spacedBy(6.dp, Alignment.CenterHorizontally),
//        verticalAlignment = Alignment.CenterVertically
//    ) {
//        Icon(
//            painter = painterResource(R.drawable.headset),
//            contentDescription = null,
//            tint = LockBoxTheme.colors.grey100,
//            modifier = Modifier.size(16.dp)
//        )
//
//        Text(
//            text = stringResource(R.string.help),
//            style = LockBoxTheme.typography.subHeadlineLarge,
//            color = LockBoxTheme.colors.grey100
//        )
//    }
//}
//
//@Preview
//@Composable
//private fun PreviewHelp() {
//    LockBoxTheme {
//        HelpTextWithIcon({}, Modifier.fillMaxWidth())
//    }
//}
