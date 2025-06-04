package org.homesharing.lockbox.presentation.detailed_lockbox.access_modal_sheet

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import kotlinx.datetime.toJavaLocalDateTime
import org.homesharing.lockbox.R
import org.homesharing.lockbox.domain.model.access.Access
import org.homesharing.lockbox.presentation.general.composables.AddRow
import org.homesharing.lockbox.presentation.general.composables.LockBoxCircularProgress
import org.homesharing.lockbox.presentation.general.composables.RoundText
import org.homesharing.lockbox.presentation.general.composables.TitledTopWithCloseAndBackButtons
import org.homesharing.lockbox.presentation.general.composables.basic.BasicHorizontalDivider
import org.homesharing.lockbox.presentation.general.composables.basic.BasicModal
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme
import java.time.format.DateTimeFormatter

@Composable
internal fun AccessModalSheet(
    onDismiss: () -> Unit,
    accessList: List<Access>,
    step: AccessModalSheetStep,
    changeStep: (AccessModalSheetStep) -> Unit,
    isLoading: Boolean,
    modifier: Modifier = Modifier,
) {
    val formatter = remember { DateTimeFormatter.ofPattern("d MMM yyyy") }
    BasicModal(
        onDismiss = onDismiss,
        modifier = modifier
    ) {
        TitledTopWithCloseAndBackButtons(
            title = stringResource(R.string.access_device),
            subtitle = stringResource(R.string.add_user),
            onDismiss = {
                if (step is AccessModalSheetStep.GuestList) onDismiss()
                else changeStep(AccessModalSheetStep.GuestList)
            },
            modifier = Modifier.fillMaxWidth()
        )
        AnimatedContent(
            targetState = step
        ) { step ->
            when (step) {
                AccessModalSheetStep.AddGuest -> {

                }

                AccessModalSheetStep.GuestList -> {
                    AnimatedContent(
                        targetState = isLoading
                    ) { loading ->
                        when (loading) {
                            true -> {
                                Box(
                                    contentAlignment = Alignment.Center,
                                    modifier = Modifier.fillMaxSize()
                                ) {
                                    LockBoxCircularProgress()
                                }
                            }
                            false -> {
                                LazyColumn(
                                    contentPadding = PaddingValues(top = 12.dp, start = 16.dp, end = 16.dp),
                                    modifier = Modifier.fillMaxSize(),
                                ) {
                                    items(
                                        count = accessList.size,
                                        key = { index -> accessList[index].macAddress }
                                    ) { index ->
                                        Access(
                                            access = accessList[index],
                                            formatter = formatter,
                                            modifier = Modifier.fillMaxWidth()
                                        )

                                        BasicHorizontalDivider()
                                    }

                                    item {
                                        AddRow(
                                            onAdd = { changeStep(AccessModalSheetStep.AddGuest) },
                                            text = stringResource(R.string.add_user),
                                            modifier = Modifier
                                                .fillMaxWidth()
                                                .padding(vertical = 10.dp)
                                        )
                                    }
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
private fun Access(access: Access, formatter: DateTimeFormatter, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.padding(vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically
    ) {
        RoundText(
            text = access.name ?: stringResource(R.string.guest),
            modifier = Modifier.size(40.dp)
        )

        Column(
            modifier = Modifier
                .weight(1f)
                .padding(horizontal = 12.dp),
            verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically)
        ) {
            Text(
                text = access.name ?: stringResource(R.string.guest),
                color = LockBoxTheme.colors.white,
                style = LockBoxTheme.typography.headlineLarge
            )

            val endDateText = formatter.format(access.endTime.toJavaLocalDateTime().toLocalDate())
            Text(
                text = endDateText,
                color = LockBoxTheme.colors.grey500,
                style = LockBoxTheme.typography.footnote
            )

        }

        Icon(
            painter = painterResource(R.drawable.arrow_right),
            tint = LockBoxTheme.colors.grey500,
            contentDescription = null,
            modifier = Modifier.size(24.dp)
        )
    }
}
