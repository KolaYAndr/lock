package org.homesharing.lockbox.presentation_v2.flats_flow.flat_rooms

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.navigationBarsPadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.homesharing.lockbox.R
import org.homesharing.lockbox.domain.model.flat_rooms.FlatRoom
import org.homesharing.lockbox.presentation_v2.uikit.composable.button.HomesharingButton
import org.homesharing.lockbox.presentation_v2.uikit.composable.button.HomesharingButtonDefaults
import org.homesharing.lockbox.presentation_v2.uikit.composable.pickable.HomesharingPickableItem
import org.homesharing.lockbox.presentation_v2.uikit.theme.HomesharingTheme
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun FlatRoomsScreen(
    viewModel: FlatRoomsViewModel = koinViewModel(),
    onProceed: () -> Unit,
    onNavBack: () -> Unit,
) {
    val uiState by viewModel.uiState.collectAsStateWithLifecycle()
    FlatRoomsScreen(
        uiState = uiState,
        onToggleRoom = viewModel::toggleRoom,
        onProceed = onProceed,
        onNavBack = onNavBack,
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FlatRoomsScreen(
    uiState: FlatRoomsUiState,
    onToggleRoom: (FlatRoom) -> Unit,
    onProceed: () -> Unit,
    onNavBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(HomesharingTheme.colors.white)
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Top,
    ) {
        CenterAlignedTopAppBar(
            title = {
                Text(
                    text = stringResource(R.string.new_flat),
                    style = HomesharingTheme.typography.bodyMedium,
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = HomesharingTheme.colors.white,
                titleContentColor = HomesharingTheme.colors.black,
            ),
            navigationIcon = {
                IconButton(
                    onClick = onNavBack,
                    colors = IconButtonDefaults.iconButtonColors(
                        contentColor = HomesharingTheme.colors.black.copy(alpha = .5f)
                    )
                ) {
                    Icon(
                        painter = painterResource(R.drawable.chevron_left),
                        contentDescription = null,
                    )
                }
            },
            modifier = Modifier.statusBarsPadding()
        )

        Text(
            text = stringResource(R.string.rooms),
            style = HomesharingTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = HomesharingTheme.colors.black,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        )

        Text(
            text = stringResource(R.string.will_help_with_report_when_checking_out),
            style = HomesharingTheme.typography.bodyMedium,
            color = HomesharingTheme.colors.black,
            modifier = Modifier
                .padding(top = 8.dp)
                .padding(horizontal = 16.dp)
        )

        Text(
            text = stringResource(R.string.popular),
            style = HomesharingTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = HomesharingTheme.colors.black,
            modifier = Modifier
                .padding(top = 24.dp)
                .padding(horizontal = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
        ) {
            HomesharingPickableItem(
                icon = painterResource(R.drawable.chef_hat),
                title = stringResource(R.string.kitchen),
                isSelected = FlatRoom.Kitchen in uiState.chosenRooms,
                onClick = {
                    onToggleRoom(FlatRoom.Kitchen)
                },
                modifier = Modifier.weight(1f),
                role = Role.Switch
            )

            HomesharingPickableItem(
                icon = painterResource(R.drawable.bed),
                title = stringResource(R.string.bedroom),
                isSelected = FlatRoom.Bedroom in uiState.chosenRooms,
                onClick = {
                    onToggleRoom(FlatRoom.Bedroom)
                },
                modifier = Modifier.weight(1f),
                role = Role.Switch
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
        ) {
            HomesharingPickableItem(
                icon = painterResource(R.drawable.armchair),
                title = stringResource(R.string.living_room),
                isSelected = FlatRoom.LivingRoom in uiState.chosenRooms,
                onClick = {
                    onToggleRoom(FlatRoom.LivingRoom)
                },
                modifier = Modifier.weight(1f),
                role = Role.Switch
            )

            HomesharingPickableItem(
                icon = painterResource(R.drawable.shower_head),
                title = stringResource(R.string.shower),
                isSelected = FlatRoom.Shower in uiState.chosenRooms,
                onClick = {
                    onToggleRoom(FlatRoom.Shower)
                },
                modifier = Modifier.weight(1f),
                role = Role.Switch
            )
        }

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
        ) {
            HomesharingPickableItem(
                icon = painterResource(R.drawable.bath),
                title = stringResource(R.string.bathroom),
                isSelected = FlatRoom.Bathroom in uiState.chosenRooms,
                onClick = {
                    onToggleRoom(FlatRoom.Bathroom)
                },
                modifier = Modifier.weight(1f),
                role = Role.Switch
            )

            HomesharingPickableItem(
                icon = painterResource(R.drawable.toilet),
                title = stringResource(R.string.toilet),
                isSelected = FlatRoom.Toilet in uiState.chosenRooms,
                onClick = {
                    onToggleRoom(FlatRoom.Toilet)
                },
                modifier = Modifier.weight(1f),
                role = Role.Switch
            )
        }

        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .padding(horizontal = 16.dp),
            contentAlignment = Alignment.Center
        ) {
            HomesharingPickableItem(
                icon = painterResource(R.drawable.plus),
                title = stringResource(R.string.create_custom),
                isSelected = false,
                enabled = false,
                onClick = {},
                role = Role.Button,
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = stringResource(R.string.soon),
                style = HomesharingTheme.typography.bodyMedium,
                color = HomesharingTheme.colors.white,
                modifier = Modifier
                    .graphicsLayer(rotationZ = -15f)
                    .background(
                        HomesharingTheme.colors.black,
                        HomesharingTheme.shape.roundedCornerShape16
                    )
                    .padding(horizontal = 4.5.dp, vertical = 2.5.dp)
                    .align(Alignment.TopStart)
            )
        }

        HomesharingButton(
            title = stringResource(R.string.proceed),
            onClick = onProceed,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .padding(horizontal = 11.dp)
        )

        HomesharingButton(
            title = stringResource(R.string.skip),
            onClick = onProceed,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp, bottom = 16.dp)
                .padding(horizontal = 11.dp)
                .navigationBarsPadding(),
            style = HomesharingButtonDefaults.secondary()
        )
    }
}

@Preview
@Composable
private fun PreviewFlatRoomsScreen() {
    HomesharingTheme {
        FlatRoomsScreen(
            uiState = FlatRoomsUiState.Initial,
            onToggleRoom = {},
            onProceed = {},
            onNavBack = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}