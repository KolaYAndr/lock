package org.homesharing.lockbox.presentation.add_flat_flow.flat_data

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
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
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import lockbox.sharedui.generated.resources.Res
import lockbox.sharedui.generated.resources.address
import lockbox.sharedui.generated.resources.bathroom
import lockbox.sharedui.generated.resources.bedroom
import lockbox.sharedui.generated.resources.commercial
import lockbox.sharedui.generated.resources.create_custom
import lockbox.sharedui.generated.resources.create_flat
import lockbox.sharedui.generated.resources.flat_type
import lockbox.sharedui.generated.resources.ic_armchair
import lockbox.sharedui.generated.resources.ic_bath
import lockbox.sharedui.generated.resources.ic_bed
import lockbox.sharedui.generated.resources.ic_chef_hat
import lockbox.sharedui.generated.resources.ic_chevron_left
import lockbox.sharedui.generated.resources.ic_house
import lockbox.sharedui.generated.resources.ic_map_pin
import lockbox.sharedui.generated.resources.ic_plus
import lockbox.sharedui.generated.resources.ic_shower_head
import lockbox.sharedui.generated.resources.ic_toilet
import lockbox.sharedui.generated.resources.kitchen
import lockbox.sharedui.generated.resources.living
import lockbox.sharedui.generated.resources.living_room
import lockbox.sharedui.generated.resources.new_flat
import lockbox.sharedui.generated.resources.rooms
import lockbox.sharedui.generated.resources.shower
import lockbox.sharedui.generated.resources.toilet
import org.homesharing.lockbox.domain.model.room.RoomType
import org.homesharing.lockbox.presentation.uikit.composable.button.HomesharingButton
import org.homesharing.lockbox.presentation.uikit.composable.changeable.HomesharingChangeablePropertyContainer
import org.homesharing.lockbox.presentation.uikit.composable.chip.HomesharingChip
import org.homesharing.lockbox.presentation.uikit.composable.property.HomesharingProperty
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun FlatDataScreen(
    viewModel: FlatDataViewModel = koinViewModel(),
    onProceed: () -> Unit,
    onNavBack: () -> Unit,
    onChangeAddress: () -> Unit,
    onChangeFlatType: () -> Unit,
    onChangeRooms: () -> Unit,
) {
    val uiState by viewModel.flatData.collectAsStateWithLifecycle()
    FlatDataScreen(
        uiState = uiState,
        onProceed = onProceed,
        onNavBack = onNavBack,
        onChangeAddress = onChangeAddress,
        onChangeFlatType = onChangeFlatType,
        onChangeRooms = onChangeRooms,
        modifier = Modifier.fillMaxSize(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FlatDataScreen(
    uiState: FlatDataUiState,
    onProceed: () -> Unit,
    onNavBack: () -> Unit,
    onChangeAddress: () -> Unit,
    onChangeFlatType: () -> Unit,
    onChangeRooms: () -> Unit,
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

        HomesharingChangeablePropertyContainer(
            title = stringResource(Res.string.address),
            onChangeClick = onChangeAddress,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .padding(horizontal = 16.dp)
        ) {
            HomesharingProperty(
                painter = painterResource(Res.drawable.ic_map_pin),
                name = uiState.flatAddress,
                modifier = Modifier.fillMaxWidth()
            )
        }

        HomesharingChangeablePropertyContainer(
            title = stringResource(Res.string.flat_type),
            onChangeClick = onChangeFlatType,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .padding(horizontal = 16.dp)
        ) {
            HomesharingProperty(
                painter = painterResource(Res.drawable.ic_house),
                name = if (!uiState.commercial) {
                    stringResource(Res.string.living)
                } else {
                    stringResource(Res.string.commercial)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        HomesharingChangeablePropertyContainer(
            title = stringResource(Res.string.rooms),
            onChangeClick = onChangeRooms,
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .padding(horizontal = 16.dp)
        ) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(8.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                for (room in uiState.chosenRooms)
                    HomesharingChip(
                        title = stringResource(
                            when (room) {
                                RoomType.Bathroom -> Res.string.bathroom
                                RoomType.Bedroom -> Res.string.bedroom
                                RoomType.Kitchen -> Res.string.kitchen
                                RoomType.LivingRoom -> Res.string.living_room
                                RoomType.ShowerRoom -> Res.string.shower
                                RoomType.Toilet -> Res.string.toilet
                                RoomType.Custom -> Res.string.create_custom
                            }
                        ),
                        painter = painterResource(
                            when (room) {
                                RoomType.Bathroom -> Res.drawable.ic_bath
                                RoomType.Bedroom -> Res.drawable.ic_bed
                                RoomType.Kitchen -> Res.drawable.ic_chef_hat
                                RoomType.LivingRoom -> Res.drawable.ic_armchair
                                RoomType.ShowerRoom -> Res.drawable.ic_shower_head
                                RoomType.Toilet -> Res.drawable.ic_toilet
                                RoomType.Custom -> Res.drawable.ic_plus
                            }
                        )
                    )
            }
        }

        HomesharingButton(
            title = stringResource(Res.string.create_flat),
            onClick = onProceed,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 11.dp)
                .padding(top = 24.dp, bottom = 16.dp)
                .navigationBarsPadding()
        )
    }
}

@Preview
@Composable
private fun PreviewFlatDataScreen() {
    HomesharingTheme {
        FlatDataScreen(
            uiState = FlatDataUiState.Initial,
            onProceed = {},
            onNavBack = {},
            onChangeAddress = {},
            onChangeFlatType = {},
            onChangeRooms = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}