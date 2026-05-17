package org.homesharing.lockbox.presentation_v2.guest_invitaion_flow.check_invitation

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
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.homesharing.lockbox.R
import org.homesharing.lockbox.domain.model.check_in_setup.CheckInSetup
import org.homesharing.lockbox.presentation_v2.uikit.composable.button.HomesharingButton
import org.homesharing.lockbox.presentation_v2.uikit.composable.changeable.HomesharingChangeablePropertyContainer
import org.homesharing.lockbox.presentation_v2.uikit.composable.chip.HomesharingChip
import org.homesharing.lockbox.presentation_v2.uikit.composable.input.HomesharingInput
import org.homesharing.lockbox.presentation_v2.uikit.composable.property.HomesharingProperty
import org.homesharing.lockbox.presentation_v2.uikit.theme.HomesharingTheme
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun CheckInvitationScreen(
    viewModel: CheckInvitationViewModel = koinViewModel(),
    onProceed: () -> Unit,
    onChangeGuestInfo: () -> Unit,
    onChangeFlatInfo: () -> Unit,
    onChangeDates: () -> Unit,
    onChangeSetup: () -> Unit,
    onNavBack: () -> Unit,
) {
    val checkInvitation by viewModel.checkInvitation.collectAsStateWithLifecycle()
    CheckInvitationScreen(
        uiState = checkInvitation,
        onProceed = onProceed,
        onChangeGuestInfo = onChangeGuestInfo,
        onChangeFlatInfo = onChangeFlatInfo,
        onChangeDates = onChangeDates,
        onChangeSetup = onChangeSetup,
        onNavBack = onNavBack,
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun CheckInvitationScreen(
    uiState: CheckInvitationUiState,
    onProceed: () -> Unit,
    onChangeGuestInfo: () -> Unit,
    onChangeFlatInfo: () -> Unit,
    onChangeDates: () -> Unit,
    onChangeSetup: () -> Unit,
    onNavBack: () -> Unit,
    modifier: Modifier = Modifier
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
                    text = stringResource(R.string.guest_invitation),
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
            text = stringResource(R.string.check_invitation),
            style = HomesharingTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = HomesharingTheme.colors.black,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        )

        HomesharingChangeablePropertyContainer(
            title = stringResource(R.string.guest),
            onChangeClick = onChangeGuestInfo,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp),
        ) {
            HomesharingProperty(
                painter = painterResource(R.drawable.user),
                name = uiState.guestName,
                modifier = Modifier
                    .fillMaxWidth()
            )

            HomesharingProperty(
                painter = painterResource(R.drawable.mail),
                name = uiState.guestEmail,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        HomesharingChangeablePropertyContainer(
            title = stringResource(R.string.flat),
            onChangeClick = onChangeFlatInfo,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp),
        ) {
            HomesharingProperty(
                painter = painterResource(R.drawable.house),
                name = uiState.buildingName,
                description = uiState.buildingAddress,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        HomesharingChangeablePropertyContainer(
            title = stringResource(R.string.dates_and_conditions),
            onChangeClick = onChangeDates,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp),
        ) {
            HomesharingProperty(
                painter = painterResource(R.drawable.calendar_days),
                name = stringResource(
                    R.string.dates,
                    uiState.checkInDate,
                    uiState.checkOutDate,
                    uiState.checkInTime,
                    uiState.checkOutTime
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )

            HomesharingProperty(
                painter = painterResource(R.drawable.users),
                name = pluralStringResource(
                    R.plurals.guest_count,
                    uiState.guestCount,
                    uiState.guestCount
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )

            HomesharingProperty(
                painter = painterResource(R.drawable.hand_coins),
                name = stringResource(R.string.deposit, uiState.deposit),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        HomesharingChangeablePropertyContainer(
            title = stringResource(R.string.check_in_setup),
            onChangeClick = onChangeSetup,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp),
        ) {
            FlowRow(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.Start),
                verticalArrangement = Arrangement.spacedBy(16.dp, Alignment.Top),
            ) {
                for (perk in uiState.selectedSettings) {
                    HomesharingChip(
                        title = when (perk) {
                            CheckInSetup.CheckInReport -> stringResource(R.string.check_in_report)
                            CheckInSetup.CheckOutReport -> stringResource(R.string.check_out_report)
                            CheckInSetup.RequestPassport -> stringResource(R.string.request_passport)
                            CheckInSetup.SignDealOnline -> stringResource(R.string.sign_deal_online)
                        },
                        painter = painterResource(
                            when (perk) {
                                CheckInSetup.CheckInReport -> R.drawable.camera
                                CheckInSetup.CheckOutReport -> R.drawable.camera
                                CheckInSetup.RequestPassport -> R.drawable.id_card
                                CheckInSetup.SignDealOnline -> R.drawable.signature
                            }
                        )
                    )
                }
            }
        }

        HomesharingInput(
            value = uiState.commentForGuest,
            readOnly = true,
            onValueChange = {},
            modifier = Modifier
                .fillMaxWidth()
                .padding(11.dp)
        )

        HomesharingButton(
            title = stringResource(R.string.send_invitation),
            onClick = onProceed,
            modifier = Modifier
                .fillMaxWidth()
                .padding(11.dp)
                .navigationBarsPadding(),
        )
    }
}

@Preview
@Composable
private fun PreviewCheckInvitationScreen() {
    HomesharingTheme {
        CheckInvitationScreen(
            uiState = CheckInvitationUiState.Initial,
            modifier = Modifier.fillMaxSize(),
            onProceed = { },
            onChangeGuestInfo = {},
            onChangeFlatInfo = {},
            onChangeDates = {},
            onChangeSetup = {},
            onNavBack = {},
        )
    }
}