package org.homesharing.lockbox.presentation.lockboxes

import android.content.Intent
import android.provider.Settings
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.Flow
import org.homesharing.lockbox.R
import org.homesharing.lockbox.domain.model.box.Box
import presentation.composables.LockBoxCircularProgress
import presentation.composables.LockBoxPullToRefresh
import presentation.composables.NetworkImage
import presentation.composables.TitledTopWithCloseAndBackButtons
import presentation.composables.general.BasicButton
import presentation.composables.general.BasicHorizontalDivider
import presentation.composables.general.BasicModal
import presentation.theme.LockBoxTheme

@Composable
fun LockBoxesScreen(
    state: LockBoxesUiState,
    boxes: Flow<PagingData<Box>>,
    onNavigateToBox: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    var showModal by remember { mutableStateOf(false) }
    val settingsLauncher =
        rememberLauncherForActivityResult(ActivityResultContracts.StartActivityForResult()) {}

    Column(modifier = modifier.background(LockBoxTheme.colors.grey950)) {
        TopRow(model = state.avatarLink, onAddDevice = { showModal = true })
        val lazyBoxes = boxes.collectAsLazyPagingItems()

        when (val loadState = lazyBoxes.loadState.refresh) {
            is LoadState.NotLoading -> {
                LockBoxScreen(
                    boxes = lazyBoxes,
                    onBoxClick = onNavigateToBox,
                    onAddDevice = { showModal = true }
                )
            }

            is LoadState.Loading -> {
                LoadingScreen()
            }

            is LoadState.Error -> {
                var isRefreshing by remember { mutableStateOf(false) }
                ErrorScreen(
                    message = loadState.error.localizedMessage,
                    isRefreshing = isRefreshing,
                    onRefresh = {
                        isRefreshing = true
                        lazyBoxes.refresh()
                        isRefreshing = false
                    }
                )
            }
        }
    }

    if (showModal) {
        ConnectModalSheet(
            onDismiss = { showModal = false },
            toWifiConnection = { settingsLauncher.launch(Intent(Settings.ACTION_WIFI_SETTINGS)) }
        )
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        LockBoxCircularProgress()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ErrorScreen(
    isRefreshing: Boolean,
    onRefresh: () -> Unit,
    message: String?
) {
    LockBoxPullToRefresh(
        modifier = Modifier.fillMaxSize(),
        isRefreshing = isRefreshing,
        onRefresh = onRefresh
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            item {
                Text(
                    text = message ?: stringResource(R.string.unknown_error),
                    color = LockBoxTheme.colors.grey100,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LockBoxScreen(
    boxes: LazyPagingItems<Box>,
    onBoxClick: (String) -> Unit,
    onAddDevice: () -> Unit,
) {
    LockBoxPullToRefresh(
        modifier = Modifier.fillMaxSize(),
        isRefreshing = boxes.loadState.refresh is LoadState.Loading,
        onRefresh = boxes::refresh,
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(top = 12.dp, start = 16.dp, end = 16.dp, bottom = 48.dp),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (boxes.itemCount == 0) {
                item {
                    EmptyScreen(onAddDevice = onAddDevice, modifier = Modifier.fillParentMaxSize())
                }
            } else {
                items(
                    count = boxes.itemCount,
                    key = { index -> boxes[index]?.macAddress ?: index }
                ) { index ->
                    boxes[index]?.let { box ->
                        LockBox(
                            box = box,
                            onClick = { onBoxClick(box.macAddress) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                item {
                    AddBoxRow(
                        onAddDevice = onAddDevice,
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun LockBox(
    box: Box,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.clickable { onClick(box.macAddress) }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .size(64.dp),
                tint = LockBoxTheme.colors.grey500
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 10.dp, horizontal = 12.dp)
            ) {
                Text(
                    text = box.alias,
                    style = LockBoxTheme.typography.headlineLarge,
                    color = LockBoxTheme.colors.white
                )

                Text(
                    text = box.address,
                    style = LockBoxTheme.typography.footnote,
                    color = LockBoxTheme.colors.grey500,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            if (box.batteryLevel <= Box.BATTERY_PERCENT_LOW) {
                Icon(
                    painter = painterResource(R.drawable.circle_alert),
                    contentDescription = null,
                    tint = LockBoxTheme.colors.red500,
                    modifier = Modifier
                        .padding(horizontal = 12.dp)
                        .size(24.dp)
                )
            }

            Icon(
                painter = painterResource(R.drawable.arrow_right),
                contentDescription = null,
                tint = LockBoxTheme.colors.grey500,
                modifier = Modifier.size(24.dp)
            )
        }

        BasicHorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}

@Composable
private fun TopRow(model: String, onAddDevice: () -> Unit) {
    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.SpaceBetween
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            NetworkImage(modifier = Modifier.size(40.dp), url = model)

            Text(
                text = stringResource(R.string.main),
                style = LockBoxTheme.typography.titleMedium,
                color = LockBoxTheme.colors.grey50,
                modifier = Modifier.padding(horizontal = 12.dp)
            )
        }

        IconButton(
            onClick = { onAddDevice() },
            modifier = Modifier.size(40.dp),
            colors = IconButtonDefaults.iconButtonColors(
                contentColor = LockBoxTheme.colors.grey100,
                containerColor = LockBoxTheme.colors.grey950
            )
        ) {
            Icon(
                painter = painterResource(R.drawable.plus),
                contentDescription = null
            )
        }
    }
}

@Composable
private fun ConnectModalSheet(
    onDismiss: () -> Unit,
    toWifiConnection: () -> Unit
) {
    BasicModal(
        onDismiss = onDismiss,
        modifier = Modifier.fillMaxSize()
    ) {
        TitledTopWithCloseAndBackButtons(
            title = stringResource(R.string.device_connection),
            onDismiss = onDismiss,
            modifier = Modifier.fillMaxWidth()
        )

        BasicHorizontalDivider(modifier = Modifier.fillMaxWidth())

        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(horizontal = 32.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Icon(
                painter = painterResource(R.drawable.router),
                contentDescription = null,
                tint = LockBoxTheme.colors.grey500,
                modifier = Modifier.size(48.dp),
            )

            Text(
                text = stringResource(R.string.connect_to_lockbox_network),
                style = LockBoxTheme.typography.titleMedium,
                color = LockBoxTheme.colors.grey100,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 12.dp)
            )

            Text(
                text = stringResource(R.string.go_to_wifi_settings_to_connect),
                style = LockBoxTheme.typography.bodyMedium,
                color = LockBoxTheme.colors.grey500,
                textAlign = TextAlign.Center,
                modifier = Modifier.padding(top = 8.dp)
            )

            BasicButton(
                onClick = { toWifiConnection() },
                text = stringResource(R.string.open_wifi_settings),
                paddingValues = PaddingValues(10.dp),
                modifier = Modifier.padding(top = 16.dp)
            )

            BasicButton(
                onClick = { onDismiss() },
                text = stringResource(R.string.cancel),
                modifier = Modifier.padding(top = 12.dp),
                paddingValues = PaddingValues(0.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = LockBoxTheme.colors.grey900,
                    contentColor = LockBoxTheme.colors.grey100
                )
            )
        }
    }
}

@Composable
private fun AddBoxRow(onAddDevice: () -> Unit, modifier: Modifier = Modifier) {
    Row(
        modifier = modifier.clickable { onAddDevice() },
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.Center
    ) {
        Icon(
            painter = painterResource(R.drawable.plus),
            contentDescription = null,
            modifier = Modifier.size(24.dp),
            tint = LockBoxTheme.colors.green500
        )

        Text(
            text = stringResource(R.string.add_device),
            style = LockBoxTheme.typography.headlineLarge,
            color = LockBoxTheme.colors.green500,
            modifier = Modifier.padding(horizontal = 8.dp),
            maxLines = 1
        )
    }
}

@Composable
private fun EmptyScreen(
    onAddDevice: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier,
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Icon(
            painter = painterResource(R.drawable.circle_fading_plus),
            contentDescription = null,
            tint = LockBoxTheme.colors.grey500,
            modifier = Modifier.size(48.dp)
        )

        Text(
            text = stringResource(R.string.manage_devices),
            modifier = Modifier.padding(top = 12.dp),
            style = LockBoxTheme.typography.titleMedium,
            color = LockBoxTheme.colors.grey100,
            textAlign = TextAlign.Center
        )

        Text(
            text = stringResource(R.string.here_will_be_devices_you_add),
            modifier = Modifier.padding(top = 8.dp),
            style = LockBoxTheme.typography.bodyMedium,
            color = LockBoxTheme.colors.grey500,
            textAlign = TextAlign.Center
        )

        BasicButton(
            onClick = { onAddDevice() },
            text = stringResource(R.string.add_device),
            paddingValues = PaddingValues(10.dp),
            modifier = Modifier.padding(top = 16.dp)
        )
    }
}

@Preview
@Composable
fun PreviewLockBox() {
    LockBoxTheme {
        LockBox(
            box = Box(
                address = "Doma",
                macAddress = "1D:2C",
                alias = "Demo",
                opened = false,
                signalStrength = 0.0,
                batteryLevel = 0.0
            ),
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Preview
@Composable
fun PreviewEmptyScreen() {
    LockBoxTheme {
        Column(modifier = Modifier.fillMaxSize()) {
            EmptyScreen({})
        }
    }
}
