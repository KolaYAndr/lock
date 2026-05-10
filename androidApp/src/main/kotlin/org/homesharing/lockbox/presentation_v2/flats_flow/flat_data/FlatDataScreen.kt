package org.homesharing.lockbox.presentation_v2.flats_flow.flat_data

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
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.homesharing.lockbox.R
import org.homesharing.lockbox.domain.model.flat_rooms.FlatRoom
import org.homesharing.lockbox.presentation_v2.uikit.composable.button.HomesharingButton
import org.homesharing.lockbox.presentation_v2.uikit.composable.changeable.HomesharingChangeablePropertyContainer
import org.homesharing.lockbox.presentation_v2.uikit.composable.chip.HomesharingChip
import org.homesharing.lockbox.presentation_v2.uikit.composable.property.HomesharingProperty
import org.homesharing.lockbox.presentation_v2.uikit.theme.HomesharingTheme
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun FlatDataScreen(
    viewModel: FlatDataViewModel = koinViewModel()
) {
    val uiState by viewModel.flatData.collectAsStateWithLifecycle()
    FlatDataScreen(
        uiState = uiState,
        modifier = Modifier.fillMaxSize(),
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun FlatDataScreen(
    uiState: FlatDataUiState,
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
                    style = HomesharingTheme.typography.bodyLarge,
                )
            },
            colors = TopAppBarDefaults.topAppBarColors(
                containerColor = HomesharingTheme.colors.white,
                titleContentColor = HomesharingTheme.colors.black,
            ),
            navigationIcon = {
                IconButton(
                    onClick = {},
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

        HomesharingChangeablePropertyContainer(
            title = stringResource(R.string.address),
            onChangeClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .padding(horizontal = 16.dp)
        ) {
            HomesharingProperty(
                painter = painterResource(R.drawable.map_pin),
                name = uiState.flatAddress,
                modifier = Modifier.fillMaxWidth()
            )
        }

        HomesharingChangeablePropertyContainer(
            title = stringResource(R.string.flat_type),
            onChangeClick = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .padding(horizontal = 16.dp)
        ) {
            HomesharingProperty(
                painter = painterResource(R.drawable.house),
                name = if (uiState.commercial) {
                    stringResource(R.string.living)
                } else {
                    stringResource(R.string.commercial)
                },
                modifier = Modifier.fillMaxWidth()
            )
        }

        HomesharingChangeablePropertyContainer(
            title = stringResource(R.string.rooms),
            onChangeClick = {},
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
                                FlatRoom.Bathroom -> R.string.bathroom
                                FlatRoom.Bedroom -> R.string.bedroom
                                FlatRoom.Kitchen -> R.string.kitchen
                                FlatRoom.LivingRoom -> R.string.living_room
                                FlatRoom.Shower -> R.string.shower
                                FlatRoom.Toilet -> R.string.toilet
                            }
                        ),
                        painter = painterResource(
                            when (room) {
                                FlatRoom.Bathroom -> R.drawable.bath
                                FlatRoom.Bedroom -> R.drawable.bed
                                FlatRoom.Kitchen -> R.drawable.chef_hat
                                FlatRoom.LivingRoom -> R.drawable.armchair
                                FlatRoom.Shower -> R.drawable.shower_head
                                FlatRoom.Toilet -> R.drawable.toilet
                            }
                        )
                    )
            }
        }

        HomesharingButton(
            title = stringResource(R.string.create_flat),
            onClick = { },
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
            modifier = Modifier.fillMaxSize()
        )
    }
}