//package org.homesharing.lockbox.presentation.uikit.composables
//
//import androidx.compose.foundation.background
//import androidx.compose.foundation.border
//import androidx.compose.foundation.layout.Box
//import androidx.compose.foundation.layout.padding
//import androidx.compose.foundation.layout.size
//import androidx.compose.material3.Icon
//import androidx.compose.material3.Text
//import androidx.compose.runtime.Composable
//import androidx.compose.ui.Alignment
//import androidx.compose.ui.Modifier
//import androidx.compose.ui.res.painterResource
//import androidx.compose.ui.text.style.TextAlign
//import androidx.compose.ui.tooling.preview.Preview
//import androidx.compose.ui.unit.dp
//import org.homesharing.lockbox.R
//import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme
//
//private const val SHORT_LENGTH_BOUND = 3
//
//@Composable
//internal fun RoundText(text: String, modifier: Modifier = Modifier) {
//    Box(
//        modifier = modifier
//            .border(
//                width = 0.5.dp,
//                color = LockBoxTheme.colors.grey700,
//                shape = LockBoxTheme.shape.circleShape
//            )
//            .background(
//                color = LockBoxTheme.colors.grey800,
//                shape = LockBoxTheme.shape.circleShape
//            )
//            .size(64.dp),
//        contentAlignment = Alignment.Center
//    ) {
//        if (text.isNotEmpty()) {
//            val shownName = if (text.length <= SHORT_LENGTH_BOUND) text
//            else {
//                val splits = text.split(" ")
//                val name = splits.getOrNull(0) ?: ""
//                val surname = splits.getOrNull(1) ?: ""
//                getShortenedName(name, surname)
//            }
//            Text(
//                text = shownName,
//                color = LockBoxTheme.colors.grey500,
//                textAlign = TextAlign.Center
//            )
//        } else {
//            Icon(
//                painter = painterResource(R.drawable.user),
//                contentDescription = null,
//                tint = LockBoxTheme.colors.grey500,
//                modifier = Modifier.size(48.dp)
//            )
//        }
//    }
//}
//
//private fun getShortenedName(name: String, surname: String): String {
//    val sb = StringBuilder()
//
//    if (name.isNotEmpty()) sb.append(name.first().uppercaseChar())
//    if (surname.isNotEmpty()) sb.append(surname.first().uppercaseChar())
//
//    return sb.toString()
//}
//
//@Preview
//@Composable
//private fun PreviewRoundText() {
//    LockBoxTheme {
//        RoundText(text = "YA3", modifier = Modifier.padding(16.dp))
//    }
//}
