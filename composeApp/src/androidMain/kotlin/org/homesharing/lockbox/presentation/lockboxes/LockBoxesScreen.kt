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
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.layout.wrapContentSize
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Lock
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Text
import androidx.compose.material3.rememberModalBottomSheetState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
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
import org.homesharing.lockbox.data.network.model.BoxDto
import presentation.composables.NetworkImage
import presentation.composables.SearchBar
import presentation.composables.general.BasicButton
import presentation.composables.general.BasicHorizontalDivider
import presentation.theme.LockBoxTheme
import presentation.theme.White

private const val BATTERY_LEVEL_LOW = 20

@Composable
fun LockBoxesScreen(
    state: LockBoxesUiState,
    boxes: Flow<PagingData<BoxDto>>,
    onSearchChange: (String) -> Unit,
    onNavigateToBox: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    val keyboardController = LocalSoftwareKeyboardController.current
    var showModal by remember { mutableStateOf(false) }
    Column(
        modifier = modifier.background(LockBoxTheme.colors.grey950),
    ) {
        TopRow(model = state.avatarLink, onAddDevice = { showModal = true })
        val lazyBoxes = boxes.collectAsLazyPagingItems()
        when (val loadState = lazyBoxes.loadState.refresh) {
            is LoadState.NotLoading -> {
                if (lazyBoxes.itemCount == 0) {
                    EmptyScreen(
                        search = state.searchString,
                        onSearchChange = onSearchChange,
                        onDoneAction = { keyboardController?.hide() },
                        onAddDevice = { showModal = true }
                    )
                } else {
                    LockBoxScreen(
                        search = state.searchString,
                        onSearchChange = onSearchChange,
                        onDoneAction = { keyboardController?.hide() },
                        boxes = lazyBoxes,
                        onBoxClick = onNavigateToBox,
                        onAddDevice = { showModal = true }
                    )
                }
            }

            is LoadState.Loading -> {
                CircularProgressIndicator(modifier = Modifier
                    .fillMaxSize()
                    .wrapContentSize())
            }

            else -> {
                Text(text = loadState.toString(), color = White)
            }
        }
    }

    if (showModal) {
        ConnectModalSheet(
            onDismiss = { showModal = false },
            toWifiConnection = {}
        )
    }
}


@Composable
private fun LockBoxScreen(
    search: String,
    onSearchChange: (String) -> Unit,
    onDoneAction: () -> Unit,
    boxes: LazyPagingItems<BoxDto>,
    onBoxClick: (String) -> Unit,
    onAddDevice: () -> Unit,
) {
    SearchBar(
        search = search,
        onSearchChange = onSearchChange::invoke,
        placeholderText = stringResource(R.string.device_search),
        onDoneAction = { onDoneAction() },
        enabled = true,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp)
    )

    LazyColumn(
        modifier = Modifier
            .fillMaxSize(),
        contentPadding = PaddingValues(top = 12.dp, start = 16.dp, end = 16.dp, bottom = 48.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
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

@Composable
private fun LockBox(
    box: BoxDto,
    onClick: (String) -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .clickable { onClick(box.macAddress) }) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Lock,
                contentDescription = null,
                modifier = Modifier.size(64.dp),
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

            if (box.batteryLevel < BATTERY_LEVEL_LOW) {
                Icon(
                    painter = painterResource(R.drawable.circle_alert),
                    contentDescription = null,
                    tint = LockBoxTheme.colors.red500,
                    modifier = Modifier
                        .size(24.dp)
                        .padding(horizontal = 12.dp)
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
            NetworkImage(modifier = Modifier.size(40.dp), model = model)

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ConnectModalSheet(
    onDismiss: () -> Unit,
    toWifiConnection: () -> Unit
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
        modifier = Modifier
            .statusBarsPadding()
            .padding(top = 12.dp)
            .fillMaxSize()
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 5.dp, horizontal = 4.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            IconButton(
                onClick = onDismiss,
                modifier = Modifier.size(48.dp),
                colors = IconButtonDefaults.iconButtonColors(contentColor = LockBoxTheme.colors.grey500)
            ) {
                Icon(
                    painter = painterResource(R.drawable.arrow_back),
                    contentDescription = null,
                )
            }

            Text(
                text = stringResource(R.string.device_connection),
                style = LockBoxTheme.typography.titleSmall,
                color = LockBoxTheme.colors.grey100,
                modifier = Modifier
                    .padding(12.dp)
            )

            IconButton(
                onClick = onDismiss,
                modifier = Modifier.size(48.dp),
                colors = IconButtonDefaults.iconButtonColors(contentColor = LockBoxTheme.colors.grey500)
            ) {
                Icon(
                    painter = painterResource(R.drawable.close),
                    contentDescription = null,
                )
            }
        }

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
    search: String,
    onSearchChange: (String) -> Unit,
    onDoneAction: () -> Unit,
    onAddDevice: () -> Unit
) {
    SearchBar(
        search = search,
        onSearchChange = onSearchChange::invoke,
        placeholderText = stringResource(R.string.device_search),
        onDoneAction = { onDoneAction() },
        enabled = false,
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 12.dp, horizontal = 16.dp)
    )

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = 32.dp),
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
            box = BoxDto("Doma", "1D:2C", "Demo", false, 0.0, 0.0),
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
            EmptyScreen("govno", {}, {}, {})
        }
    }
}
