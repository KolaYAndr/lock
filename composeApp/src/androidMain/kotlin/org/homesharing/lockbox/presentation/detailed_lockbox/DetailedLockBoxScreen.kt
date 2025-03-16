package org.homesharing.lockbox.presentation.detailed_lockbox

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import org.homesharing.lockbox.R
import presentation.composables.HelpModalSheet
import presentation.composables.IconTextArrow
import presentation.composables.LockBoxPropertyInput
import presentation.composables.general.BasicBottomSheet
import presentation.composables.general.BasicButton
import presentation.composables.general.BasicDialog
import presentation.composables.general.BasicGroup
import presentation.composables.general.BasicHorizontalDivider
import presentation.composables.TitledTopWithCloseAndBackButtons
import presentation.composables.general.BasicModal
import presentation.theme.LockBoxTheme
import org.homesharing.lockbox.domain.model.box.Box

@Composable
fun DetailedLockBoxScreen(
    uiState: DetailedLockBoxUiState,
    onNavBack: () -> Unit,
    onOpenBox: () -> Unit,
    onUnbindDevice: () -> Unit,
    onSaveAddress: (String) -> Unit,
    onSaveName: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showOpenDeviceDialog by remember { mutableStateOf(false) }
    var showUnbindDeviceDialog by remember { mutableStateOf(false) }
//    var showAccessModal by remember { mutableStateOf(false) }
    var showAboutDeviceModal by remember { mutableStateOf(false) }
    var showChangeNameBottomSheet by remember { mutableStateOf(false) }
    var showChangeAddressBottomSheet by remember { mutableStateOf(false) }
    var showHelpModal by remember { mutableStateOf(false) }

    Column(
        modifier = modifier.background(LockBoxTheme.colors.grey950),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 12.dp),
        ) {
            IconButton(
                onClick = onNavBack,
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_back),
                    contentDescription = null,
                    tint = LockBoxTheme.colors.grey500,
                    modifier = Modifier
                        .size(24.dp)
                        .align(Alignment.CenterStart)
                )
            }

            Text(
                text = uiState.boxName,
                style = LockBoxTheme.typography.titleSmall,
                color = LockBoxTheme.colors.grey100,
                textAlign = TextAlign.Center,
                modifier = Modifier.align(Alignment.Center)
            )
        }

        BasicHorizontalDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp)
        )

        LazyColumn(
            modifier = Modifier.weight(1f),
            contentPadding = PaddingValues(bottom = 12.dp, start = 16.dp, end = 16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Top
        ) {
            item {
                Column(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 48.dp, horizontal = 32.dp),
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Icon(
                        imageVector = Icons.Default.Lock,
                        contentDescription = null,
                        modifier = Modifier.size(64.dp),
                        tint = LockBoxTheme.colors.grey500
                    )

                    Text(
                        text = uiState.boxName,
                        style = LockBoxTheme.typography.titleMedium,
                        color = LockBoxTheme.colors.grey100,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 12.dp)
                    )

                    Text(
                        text = uiState.address,
                        style = LockBoxTheme.typography.bodyMedium,
                        color = LockBoxTheme.colors.grey500,
                        textAlign = TextAlign.Center,
                        modifier = Modifier.padding(top = 8.dp)
                    )

                    BasicButton(
                        onClick = { showOpenDeviceDialog = true },
                        text = stringResource(R.string.open),
                        paddingValues = PaddingValues(10.dp),
                        modifier = Modifier.padding(top = 16.dp)
                    )
                }
            }

            item {
                BasicGroup(
                    shape = LockBoxTheme.shape.roundedCornerShape12,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                ) {
                    StatusInfo(
                        statusImage = painterResource(R.drawable.wifi_zero),
                        statusCategory = stringResource(R.string.wifi_network),
                        status = "Network Name", // todo переделать всё,
                        alertImage = {},
                        modifier = Modifier.fillMaxWidth()
                    )

                    StatusInfo(
                        statusImage = getSignalImage(uiState.signalStrength),
                        statusCategory = stringResource(R.string.signal_level),
                        status = getSignalStatus(uiState.signalStrength),
                        alertImage = {
                            Icon(
                                painter = painterResource(R.drawable.circle_alert),
                                contentDescription = null,
                                tint = getSignalAlertColor(uiState.signalStrength)
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )

                    StatusInfo(
                        statusImage = getBatteryImage(uiState.batteryLevel),
                        statusCategory = stringResource(R.string.battery_level),
                        status = stringResource(R.string.battery_percent, uiState.batteryLevel),
                        alertImage = {
                            Icon(
                                painter = painterResource(R.drawable.circle_alert),
                                contentDescription = null,
                                tint = getBatteryAlertColor(uiState.batteryLevel)
                            )
                        },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

//            item {
//                BasicGroup(
//                    shape = LockBoxTheme.shape.roundedCornerShape12,
//                    modifier = Modifier
//                        .fillMaxWidth()
//                        .padding(vertical = 12.dp)
//                ) {
//                    ActionableProperty(
//                        title = stringResource(R.string.access_device),
//                        onClick = { showAccessModal = true },
//                        modifier = Modifier
//                            .fillMaxWidth()
//                            .padding(vertical = 4.dp)
//                    )
//                }
//            }

            item {
                BasicGroup(
                    shape = LockBoxTheme.shape.roundedCornerShape12,
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                ) {
                    ActionableProperty(
                        title = stringResource(R.string.address),
                        text = uiState.address,
                        onClick = { showChangeAddressBottomSheet = true },
                        modifier = Modifier.fillMaxWidth()
                    )

                    ActionableProperty(
                        title = stringResource(R.string.device_name),
                        text = uiState.boxName,
                        onClick = { showChangeNameBottomSheet = true },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            item {
                BasicGroup(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(vertical = 12.dp)
                ) {
                    IconTextArrow(
                        painter = painterResource(R.drawable.circle_alert),
                        text = stringResource(R.string.about_device),
                        onClick = { showAboutDeviceModal = true },
                        modifier = Modifier.fillMaxWidth()
                    )

                    BasicHorizontalDivider(modifier = Modifier.fillMaxWidth())

                    IconTextArrow(
                        painter = painterResource(R.drawable.headset),
                        text = stringResource(R.string.help),
                        onClick = { showHelpModal = true },
                        modifier = Modifier.fillMaxWidth()
                    )
                }
            }

            item {
                Row(
                    modifier = Modifier
                        .padding(vertical = 12.dp, horizontal = 16.dp)
                        .fillMaxWidth()
                        .clickable { showUnbindDeviceDialog = true },
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.spacedBy(
                        12.dp, Alignment.CenterHorizontally
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.trash),
                        contentDescription = null,
                        modifier = Modifier.size(24.dp),
                        tint = LockBoxTheme.colors.red600
                    )

                    Text(
                        text = stringResource(R.string.unbind_device),
                        style = LockBoxTheme.typography.headlineLarge,
                        color = LockBoxTheme.colors.red600
                    )
                }
            }
        }
    }

    if (showOpenDeviceDialog) {
        OpenDeviceDialog(
            onDismiss = { showOpenDeviceDialog = false },
            onOpenBox = onOpenBox,
        )
    }

    if (showUnbindDeviceDialog) {
        UnbindDeviceDialog(
            onDismiss = { showUnbindDeviceDialog = false },
            onUnbind = onUnbindDevice,
        )
    }

    if (showChangeAddressBottomSheet) {
        ChangePropertyBottomSheet(
            initialValue = uiState.address,
            titleText = stringResource(R.string.address),
            onDismiss = { showChangeAddressBottomSheet = false },
            onSaveChanges = onSaveAddress,
        )
    }

    if (showChangeNameBottomSheet) {
        ChangePropertyBottomSheet(
            initialValue = uiState.boxName,
            titleText = stringResource(R.string.device_name),
            onDismiss = { showChangeNameBottomSheet = false },
            onSaveChanges = onSaveName,
        )
    }

    if (showAboutDeviceModal) {
        AboutDeviceModal(
            onDismiss = { showAboutDeviceModal = false },
            boxName = uiState.boxName,
            modifier = Modifier.fillMaxSize()
        )
    }

    if (showHelpModal) {
        HelpModalSheet(
            onDismiss = { showHelpModal = false },
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
private fun getBatteryAlertColor(batteryLevel: Int): Color {
    return when (batteryLevel) {
        in Box.BATTERY_PERCENT_LOW..< Box.BATTERY_PERCENT_MEDIUM -> LockBoxTheme.colors.yellow500
        in 0..< Box.BATTERY_PERCENT_LOW -> LockBoxTheme.colors.red600
        else -> Color.Transparent
    }
}

@Composable
private fun getBatteryImage(batteryLevel: Int): Painter {
    return when (batteryLevel) {
        in Box.BATTERY_PERCENT_LOW..< Box.BATTERY_PERCENT_MEDIUM -> painterResource(R.drawable.battery_low)
        in Box.BATTERY_PERCENT_MEDIUM ..< Box.BATTERY_PERCENT_HIGH -> painterResource(R.drawable.battery_medium)
        in Box.BATTERY_PERCENT_HIGH..100 -> painterResource(R.drawable.battery_full)
        else -> painterResource(R.drawable.battery_empty)
    }
}

@Composable
private fun getSignalImage(signalStrength: Int): Painter {
    return when(signalStrength) {
        in Box.SIGNAL_STRENGTH_LEVEL_LOW..< Box.SIGNAL_STRENGTH_LEVEL_MEDIUM -> painterResource(R.drawable.signal_low)
        in Box.SIGNAL_STRENGTH_LEVEL_MEDIUM..< Box.SIGNAL_STRENGTH_LEVEL_HIGH -> painterResource(R.drawable.signal_medium)
        in Box.SIGNAL_STRENGTH_LEVEL_HIGH..< Box.SIGNAL_STRENGTH_LEVEL_FULL -> painterResource(R.drawable.signal_high)
        in Box.SIGNAL_STRENGTH_LEVEL_FULL..< 0 -> painterResource(R.drawable.signal_full)
        else -> painterResource(R.drawable.signal_zero)
    }
}

@Composable
@ReadOnlyComposable
private fun getSignalStatus(signalStrength: Int): String {
    return when(signalStrength) {
        in Box.SIGNAL_STRENGTH_LEVEL_LOW..< Box.SIGNAL_STRENGTH_LEVEL_MEDIUM -> stringResource(R.string.signa_low)
        in Box.SIGNAL_STRENGTH_LEVEL_MEDIUM..< Box.SIGNAL_STRENGTH_LEVEL_HIGH -> stringResource(R.string.signal_medium)
        in Box.SIGNAL_STRENGTH_LEVEL_HIGH..< Box.SIGNAL_STRENGTH_LEVEL_FULL -> stringResource(R.string.signal_high)
        in Box.SIGNAL_STRENGTH_LEVEL_FULL..< 0 -> stringResource(R.string.signal_full)
        else -> stringResource(R.string.signal_zero)
    }
}

@Composable
private fun getSignalAlertColor(signalStrength: Int): Color {
    return when (signalStrength) {
        in -89..-60 -> LockBoxTheme.colors.yellow500
        in -100..-90 -> LockBoxTheme.colors.red600
        else -> Color.Transparent
    }
}


@Composable
private fun UnbindDeviceDialog(
    onDismiss: () -> Unit,
    onUnbind: () -> Unit,
    modifier: Modifier = Modifier
) {
    BasicDialog(
        onDismiss = onDismiss,
        modifier = modifier
    ) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(start = 8.dp, end = 8.dp, top = 8.dp),
            verticalArrangement = Arrangement.spacedBy(8.dp),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(R.string.unbind_device_question),
                style = LockBoxTheme.typography.titleMedium,
                color = LockBoxTheme.colors.grey100
            )

            Text(
                text = stringResource(R.string.not_cancellable_unbind_device),
                style = LockBoxTheme.typography.bodyMedium,
                color = LockBoxTheme.colors.grey500
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
        ) {
            BasicButton(
                onClick = onDismiss,
                text = stringResource(R.string.cancel),
                paddingValues = PaddingValues(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LockBoxTheme.colors.grey900,
                    contentColor = LockBoxTheme.colors.grey100
                )
            )

            BasicButton(
                onClick = {
                    onUnbind()
                    onDismiss()
                },
                text = stringResource(R.string.unbind),
                paddingValues = PaddingValues(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LockBoxTheme.colors.red600,
                    contentColor = LockBoxTheme.colors.grey100
                )
            )
        }
    }
}

@Composable
private fun AboutDeviceModal(
    onDismiss: () -> Unit,
    boxName: String,
    modifier: Modifier = Modifier
) {
    BasicModal(
        onDismiss = onDismiss,
        modifier = modifier
    ) {
        TitledTopWithCloseAndBackButtons(
            onDismiss = onDismiss,
            title = stringResource(R.string.about_device),
            subtitle = boxName
        )

        BasicHorizontalDivider(modifier = Modifier.fillMaxWidth())

        Column(
            modifier = Modifier
                .padding(vertical = 12.dp, horizontal = 16.dp)
                .weight(1f),
            horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = stringResource(R.string.manufacturer),
                style = LockBoxTheme.typography.subHeadlineMedium,
                color = LockBoxTheme.colors.grey500,
                modifier = Modifier.padding(top = 10.dp)
            )
            Text(
                text = stringResource(R.string.lockbox),
                style = LockBoxTheme.typography.headlineLarge,
                color = LockBoxTheme.colors.white,
                modifier = Modifier.padding(top = 2.dp)
            )

            BasicHorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )

            Text(
                text = stringResource(R.string.device_type),
                style = LockBoxTheme.typography.subHeadlineMedium,
                color = LockBoxTheme.colors.grey500,
                modifier = Modifier.padding(top = 10.dp)
            )
            Text(
                text = stringResource(R.string.lockbox_type),
                style = LockBoxTheme.typography.headlineLarge,
                color = LockBoxTheme.colors.white,
                modifier = Modifier.padding(top = 2.dp)
            )

            BasicHorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )

            Text(
                text = stringResource(R.string.original_naming),
                style = LockBoxTheme.typography.subHeadlineMedium,
                color = LockBoxTheme.colors.grey500,
                modifier = Modifier.padding(top = 10.dp)
            )
            Text(
                text = stringResource(R.string.original_naming_lockbox),
                style = LockBoxTheme.typography.headlineLarge,
                color = LockBoxTheme.colors.white,
                modifier = Modifier.padding(top = 2.dp)
            )

            BasicHorizontalDivider(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 10.dp)
            )
        }

        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterHorizontally),
            modifier = Modifier
                .fillMaxWidth()
                .navigationBarsPadding()
        ) {
            Icon(
                painter = painterResource(R.drawable.headset),
                contentDescription = null,
                tint = LockBoxTheme.colors.grey100
            )

            Text(
                text = stringResource(R.string.help),
                style = LockBoxTheme.typography.headlineMedium,
                color = LockBoxTheme.colors.grey100
            )
        }
    }
}

@Composable
private fun ChangePropertyBottomSheet(
    titleText: String,
    initialValue: String,
    onDismiss: () -> Unit,
    onSaveChanges: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var currentValue by remember { mutableStateOf(initialValue) }
    BasicBottomSheet(
        onDismissRequest = onDismiss,
        shape = LockBoxTheme.shape.roundedCornerShape16,
        modifier = modifier
    ) {
        BottomSheetTitle(
            onDismiss = onDismiss,
            title = titleText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 4.dp, start = 8.dp, end = 8.dp)
        )

        val canSaveChanges by remember { derivedStateOf { initialValue != currentValue } }
        Column(
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 12.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            LockBoxPropertyInput(
                value = currentValue,
                onValueChange = { newValue -> currentValue = newValue },
                onDone = {
                    if (canSaveChanges) {
                        onSaveChanges(currentValue)
                        onDismiss()
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
            )

            BasicButton(
                onClick = {
                    onSaveChanges(currentValue)
                    onDismiss()
                },
                text = stringResource(R.string.save_changes),
                paddingValues = PaddingValues(14.dp),
                enabled = canSaveChanges,
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 12.dp)
            )
        }
    }
}

@Composable
private fun BottomSheetTitle(
    onDismiss: () -> Unit,
    title: String,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(4.dp)
    ) {
        IconButton(
            onClick = onDismiss,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = LockBoxTheme.colors.grey500,
                containerColor = Color.Transparent
            ),
        ) {
            Icon(
                painter = painterResource(R.drawable.arrow_back),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }

        Text(
            text = title,
            style = LockBoxTheme.typography.titleSmall,
            color = LockBoxTheme.colors.grey100,
            modifier = Modifier.weight(1f)
        )

        IconButton(
            onClick = onDismiss,
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = LockBoxTheme.colors.grey500,
                containerColor = Color.Transparent
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.close),
                contentDescription = null,
                modifier = Modifier.size(24.dp)
            )
        }
    }
}

@Composable
private fun OpenDeviceDialog(
    onDismiss: () -> Unit,
    onOpenBox: () -> Unit,
    modifier: Modifier = Modifier
) {
    BasicDialog(
        onDismiss = onDismiss,
        modifier = modifier
    ) {
        Text(
            text = stringResource(R.string.open_device_question),
            style = LockBoxTheme.typography.titleMedium,
            color = LockBoxTheme.colors.grey100,
            modifier = Modifier.padding(start = 8.dp, end = 8.dp, top = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(8.dp, Alignment.End)
        ) {
            BasicButton(
                onClick = onDismiss,
                text = stringResource(R.string.cancel),
                paddingValues = PaddingValues(10.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LockBoxTheme.colors.grey900,
                    contentColor = LockBoxTheme.colors.grey100
                )
            )

            BasicButton(
                onClick = {
                    onOpenBox()
                    onDismiss()
                },
                text = stringResource(R.string.open),
                paddingValues = PaddingValues(10.dp)
            )
        }
    }
}

@Composable
private fun ActionableProperty(
    title: String,
    text: String = "",
    onClick: () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = Modifier
            .clickable { onClick() }
            .padding(vertical = 10.dp, horizontal = 16.dp)
            .then(modifier),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp)
    ) {
        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.Top),
            horizontalAlignment = Alignment.Start,
            modifier = Modifier.weight(1f)
        ) {
            Text(
                text = title,
                style = LockBoxTheme.typography.headlineLarge,
                color = LockBoxTheme.colors.white
            )

            if (text.isNotEmpty()) {
                Text(
                    text = text,
                    style = LockBoxTheme.typography.footnote,
                    color = LockBoxTheme.colors.grey500
                )
            }
        }

        Icon(
            painter = painterResource(R.drawable.arrow_right),
            contentDescription = null,
            tint = LockBoxTheme.colors.grey500,
        )
    }
}

@Composable
private fun StatusInfo(
    statusImage: Painter,
    statusCategory: String,
    status: String,
    alertImage: @Composable () -> Unit,
    modifier: Modifier = Modifier
) {
    Row(
        modifier = modifier.padding(vertical = 10.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start)
    ) {
        Icon(
            painter = statusImage,
            contentDescription = null,
            tint = Color.Unspecified,
            modifier = Modifier.size(24.dp)
        )

        Column(
            verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = statusCategory,
                style = LockBoxTheme.typography.subHeadlineMedium,
                color = LockBoxTheme.colors.grey500,
            )

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Text(
                    text = status,
                    style = LockBoxTheme.typography.headlineLarge,
                    color = LockBoxTheme.colors.white
                )

                alertImage()
            }
        }
    }
}

@Preview
@Composable
fun PreviewDetailScreen() {
    LockBoxTheme {
        val state = DetailedLockBoxUiState()
        DetailedLockBoxScreen(
            state,
            {}, {}, {}, {}, {},
            Modifier.fillMaxSize()
        )
    }
}
