package org.homesharing.lockbox.presentation.lockboxes

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
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
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.LocalDateTime
import org.homesharing.lockbox.R
import org.homesharing.lockbox.domain.model.box.Box
import org.homesharing.lockbox.domain.model.box.BoxWithStatus
import org.homesharing.lockbox.domain.model.box.Status
import org.homesharing.lockbox.presentation.general.composables.AddRow
import org.homesharing.lockbox.presentation.general.composables.LockBoxPullToRefresh
import org.homesharing.lockbox.presentation.general.composables.TopRowWithAddButton
import org.homesharing.lockbox.presentation.general.composables.basic.BasicHorizontalDivider
import org.homesharing.lockbox.presentation.general.composables.login_top_rows.LockBoxesTopRow
import org.homesharing.lockbox.presentation.general.composables.screens.EmptyScreenContent
import org.homesharing.lockbox.presentation.general.composables.screens.ErrorScreenContent
import org.homesharing.lockbox.presentation.general.composables.screens.LoadingScreenContent
import org.homesharing.lockbox.presentation.general.theme.LockBoxTheme
import org.homesharing.lockbox.presentation.lockboxes.add_virtual_device_modal.AddVirtualDeviceModalViewModel
import org.homesharing.lockbox.presentation.lockboxes.connect_device_modal.ConnectDeviceModal
import org.homesharing.lockbox.presentation.lockboxes.connect_device_modal.ConnectDeviceModalViewModel
import org.homesharing.lockbox.presentation.lockboxes.virtual_device_modal.AddVirtualDeviceModal
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun LockBoxesScreen(
    avatarLink: String,
    boxes: Flow<PagingData<BoxWithStatus>>,
    onNavigateToBox: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    var showConnectModal by remember { mutableStateOf(false) }
//    var showAddVirtualDeviceModal by remember { mutableStateOf(false) }
    val lazyBoxes = boxes.collectAsLazyPagingItems()

    Column(modifier = modifier.background(LockBoxTheme.colors.grey950)) {
        TopRow(avatar = avatarLink, onAddDevice = { showConnectModal = true })

        when (lazyBoxes.loadState.refresh) {
            is LoadState.NotLoading -> {
                LockBoxScreen(
                    boxes = lazyBoxes,
                    onBoxClick = onNavigateToBox,
                    onAddDevice = { showConnectModal = true }
                )
            }

            is LoadState.Loading -> { LoadingScreenContent(modifier = Modifier.fillMaxSize()) }
            is LoadState.Error -> { ErrorScreen(onRefresh = lazyBoxes::refresh) }
        }
    }

    if (showConnectModal) {
        ConnectModalSheet(
            modifier = Modifier.fillMaxSize(),
            onDismiss = { showConnectModal = false },
        )
    }

//    if (showAddVirtualDeviceModal) {
//        VirtualDeviceModal(
//            modifier = Modifier.fillMaxSize(),
//            onDismiss = { showAddVirtualDeviceModal = false },
//            onFinish = lazyBoxes::refresh
//
//        )
//    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ErrorScreen(
    onRefresh: () -> Unit,
) {
    var isRefreshing by remember { mutableStateOf(false) }
    ErrorScreenContent(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            onRefresh()
            isRefreshing = false
        },
        message = stringResource(R.string.something_went_wrong_try_refreshing),
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LockBoxScreen(
    boxes: LazyPagingItems<BoxWithStatus>,
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
                    EmptyBoxesScreen(
                        onAddDevice = onAddDevice,
                        modifier = Modifier.fillParentMaxSize()
                    )
                }
            } else {
                items(
                    count = boxes.itemCount,
                    key = { index -> boxes[index]?.box?.macAddress ?: index }
                ) { index ->
                    boxes[index]?.let { statusBox ->
                        LockBox(
                            statusBox = statusBox,
                            onClick = { onBoxClick(statusBox.box.macAddress) },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                item {
                    AddRow(
                        onAdd = onAddDevice,
                        text = stringResource(R.string.add_device),
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun LockBox(
    statusBox: BoxWithStatus,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.clickable { onClick(statusBox.box.macAddress) }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .size(30.dp),
                tint = LockBoxTheme.colors.grey500
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 10.dp, horizontal = 12.dp),
                verticalArrangement = Arrangement.spacedBy(2.dp)
            ) {
                Text(
                    text = statusBox.box.alias,
                    style = LockBoxTheme.typography.headlineLarge,
                    color = LockBoxTheme.colors.constantWhite
                )

                Text(
                    text = statusBox.box.address,
                    style = LockBoxTheme.typography.footnote,
                    color = LockBoxTheme.colors.grey500,
                    modifier = Modifier.padding(top = 2.dp)
                )
            }

            statusBox.status.batteryLevel?.let { level ->
                if (level <= BoxWithStatus.BATTERY_PERCENT_LOW) {
                    Icon(
                        painter = painterResource(R.drawable.circle_alert),
                        contentDescription = null,
                        tint = LockBoxTheme.colors.red500,
                        modifier = Modifier
                            .padding(horizontal = 12.dp)
                            .size(24.dp)
                    )
                }
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
private fun TopRow(avatar: String, onAddDevice: () -> Unit) {
    TopRowWithAddButton(
        rowContent = { LockBoxesTopRow(avatarLink = avatar) },
        onAdd = onAddDevice,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 8.dp, horizontal = 16.dp)
    )
}

@Composable
private fun VirtualDeviceModal(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
    onFinish: () -> Unit,
) {
    val addVirtualDeviceViewModel = koinViewModel<AddVirtualDeviceModalViewModel>()
    val addVirtualDeviceUiState by addVirtualDeviceViewModel.addVirtualDeviceUiState.collectAsStateWithLifecycle()

    AddVirtualDeviceModal(
        modifier = modifier,
        onDismiss = onDismiss,
        deviceName = addVirtualDeviceUiState.deviceName,
        onChangeDeviceName = addVirtualDeviceViewModel::onChangeDeviceName,
        deviceAddress = addVirtualDeviceUiState.macAddress,
        onChangeMacAddress = addVirtualDeviceViewModel::onChangeMacAddress,
        onChangeDeviceAddress = addVirtualDeviceViewModel::onChangeDeviceAddress,
        onCreateVirtualDevice = {
            addVirtualDeviceViewModel.onCreateVirtualDevice()
            onFinish()
            onDismiss()
        }
    )
}

@Composable
private fun ConnectModalSheet(
    modifier: Modifier = Modifier,
    onDismiss: () -> Unit,
) {
    val connectDeviceViewModel = koinViewModel<ConnectDeviceModalViewModel>()

    ConnectDeviceModal(
        onDismiss = onDismiss,
        onCreateTestDevice = {
            connectDeviceViewModel.createTestDevice()
            onDismiss()
        },
        modifier = modifier
    )
}

@Composable
private fun EmptyBoxesScreen(
    onAddDevice: () -> Unit,
    modifier: Modifier = Modifier,
) {
    EmptyScreenContent(
        modifier = modifier,
        title = stringResource(R.string.manage_devices),
        subtitle = stringResource(R.string.here_will_be_devices_you_add),
        action = stringResource(R.string.add_device),
        onAction = onAddDevice
    )
}

@Preview
@Composable
private fun PreviewLockBox() {
    LockBoxTheme {
        LockBox(
            statusBox = BoxWithStatus(
                box = Box(
                    address = "Dom",
                    alias = "Domashniy",
                    ownerSub = "1234",
                    macAddress = "11:AA"
                ),
                status = Status(
                    macAddress = "11:aa",
                    opened = false,
                    batteryLevel = 90.0,
                    signalStrength = 0.0,
                    timeStamp = LocalDateTime(2025, 4, 3, 12, 23, 14, 10)
                )
            ),
            onClick = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}


@Preview
@Composable
private fun PreviewLockBoxesScreen() {
    LockBoxTheme {
        val pagingData = flowOf(
            PagingData.from(
                listOf(
                    BoxWithStatus(
                        box = Box(
                            address = "Dom",
                            alias = "Domashniy",
                            ownerSub = "1234",
                            macAddress = "11:AA"
                        ),
                        status = Status(
                            macAddress = "11:aa",
                            opened = false,
                            batteryLevel = 90.0,
                            signalStrength = 0.0,
                            timeStamp = LocalDateTime(2025, 4, 3, 12, 23, 14, 10)
                        )
                    ),
                    BoxWithStatus(
                        box = Box(
                            address = "Dom",
                            alias = "Domashniy",
                            ownerSub = "1234",
                            macAddress = "11:AA"
                        ),
                        status = Status(
                            macAddress = "11:aa",
                            opened = false,
                            batteryLevel = 90.0,
                            signalStrength = 0.0,
                            timeStamp = LocalDateTime(2025, 4, 3, 12, 23, 14, 10)
                        )
                    ),
                    BoxWithStatus(
                        box = Box(
                            address = "Dom",
                            alias = "Domashniy",
                            ownerSub = "1234",
                            macAddress = "11:AA"
                        ),
                        status = Status(
                            macAddress = "11:aa",
                            opened = false,
                            batteryLevel = 90.0,
                            signalStrength = 0.0,
                            timeStamp = LocalDateTime(2025, 4, 3, 12, 23, 14, 10)
                        )
                    ),
                ),
                LoadStates(
                    LoadState.NotLoading(false),
                    LoadState.NotLoading(false),
                    LoadState.NotLoading(false)
                )
            )
        )
        LockBoxesScreen("", pagingData, {}, Modifier.fillMaxSize())
    }
}

@Preview
@Composable
private fun PreviewLoading() {
    LockBoxTheme {
        val pagingData = flowOf(PagingData.empty<BoxWithStatus>())
        LockBoxesScreen("", pagingData, {}, Modifier.fillMaxSize())
    }
}

@Preview
@Composable
private fun PreviewEmptyScreen() {
    LockBoxTheme {
        val pagingData = flowOf(
            PagingData.empty<BoxWithStatus>(
                LoadStates(
                    LoadState.NotLoading(false),
                    LoadState.NotLoading(false),
                    LoadState.NotLoading(false)
                )
            )
        )
        LockBoxesScreen("", pagingData, {}, Modifier.fillMaxSize())
    }
}
