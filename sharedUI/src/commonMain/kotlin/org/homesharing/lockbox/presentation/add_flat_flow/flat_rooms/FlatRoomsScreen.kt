package org.homesharing.lockbox.presentation.add_flat_flow.flat_rooms

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
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import lockbox.sharedui.generated.resources.Res
import lockbox.sharedui.generated.resources.bathroom
import lockbox.sharedui.generated.resources.bedroom
import lockbox.sharedui.generated.resources.create_custom
import lockbox.sharedui.generated.resources.ic_armchair
import lockbox.sharedui.generated.resources.ic_bath
import lockbox.sharedui.generated.resources.ic_bed
import lockbox.sharedui.generated.resources.ic_chef_hat
import lockbox.sharedui.generated.resources.ic_chevron_left
import lockbox.sharedui.generated.resources.ic_plus
import lockbox.sharedui.generated.resources.ic_shower_head
import lockbox.sharedui.generated.resources.ic_toilet
import lockbox.sharedui.generated.resources.kitchen
import lockbox.sharedui.generated.resources.living_room
import lockbox.sharedui.generated.resources.new_flat
import lockbox.sharedui.generated.resources.popular
import lockbox.sharedui.generated.resources.proceed
import lockbox.sharedui.generated.resources.rooms
import lockbox.sharedui.generated.resources.shower
import lockbox.sharedui.generated.resources.skip
import lockbox.sharedui.generated.resources.soon
import lockbox.sharedui.generated.resources.toilet
import lockbox.sharedui.generated.resources.will_help_with_report_when_checking_out
import org.homesharing.lockbox.domain.model.room.RoomType
import org.homesharing.lockbox.presentation.uikit.composable.button.HomesharingButton
import org.homesharing.lockbox.presentation.uikit.composable.button.HomesharingButtonDefaults
import org.homesharing.lockbox.presentation.uikit.composable.pickable.HomesharingPickableItem
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
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
    onToggleRoom: (RoomType) -> Unit,
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
                    text = stringResource(Res.string.new_flat),
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
                        painter = painterResource(Res.drawable.ic_chevron_left),
                        contentDescription = null,
                    )
                }
            },
            modifier = Modifier.statusBarsPadding()
        )

        Text(
            text = stringResource(Res.string.rooms),
            style = HomesharingTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = HomesharingTheme.colors.black,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        )

        Text(
            text = stringResource(Res.string.will_help_with_report_when_checking_out),
            style = HomesharingTheme.typography.bodyMedium,
            color = HomesharingTheme.colors.black,
            modifier = Modifier
                .padding(top = 8.dp)
                .padding(horizontal = 16.dp)
        )

        Text(
            text = stringResource(Res.string.popular),
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
                icon = painterResource(Res.drawable.ic_chef_hat),
                title = stringResource(Res.string.kitchen),
                isSelected = RoomType.Kitchen in uiState.chosenRooms,
                onClick = {
                    onToggleRoom(RoomType.Kitchen)
                },
                modifier = Modifier.weight(1f),
            )

            HomesharingPickableItem(
                icon = painterResource(Res.drawable.ic_bed),
                title = stringResource(Res.string.bedroom),
                isSelected = RoomType.Bedroom in uiState.chosenRooms,
                onClick = {
                    onToggleRoom(RoomType.Bedroom)
                },
                modifier = Modifier.weight(1f),
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
                icon = painterResource(Res.drawable.ic_armchair),
                title = stringResource(Res.string.living_room),
                isSelected = RoomType.LivingRoom in uiState.chosenRooms,
                onClick = {
                    onToggleRoom(RoomType.LivingRoom)
                },
                modifier = Modifier.weight(1f),
            )

            HomesharingPickableItem(
                icon = painterResource(Res.drawable.ic_shower_head),
                title = stringResource(Res.string.shower),
                isSelected = RoomType.ShowerRoom in uiState.chosenRooms,
                onClick = {
                    onToggleRoom(RoomType.ShowerRoom)
                },
                modifier = Modifier.weight(1f),
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
                icon = painterResource(Res.drawable.ic_bath),
                title = stringResource(Res.string.bathroom),
                isSelected = RoomType.Bathroom in uiState.chosenRooms,
                onClick = {
                    onToggleRoom(RoomType.Bathroom)
                },
                modifier = Modifier.weight(1f),
            )

            HomesharingPickableItem(
                icon = painterResource(Res.drawable.ic_toilet),
                title = stringResource(Res.string.toilet),
                isSelected = RoomType.Toilet in uiState.chosenRooms,
                onClick = {
                    onToggleRoom(RoomType.Toilet)
                },
                modifier = Modifier.weight(1f),
                
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
                icon = painterResource(Res.drawable.ic_plus),
                title = stringResource(Res.string.create_custom),
                isSelected = false,
                enabled = false,
                onClick = {},
                modifier = Modifier.fillMaxWidth()
            )

            Text(
                text = stringResource(Res.string.soon),
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
            title = stringResource(Res.string.proceed),
            onClick = onProceed,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .padding(horizontal = 11.dp)
        )

        HomesharingButton(
            title = stringResource(Res.string.skip),
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