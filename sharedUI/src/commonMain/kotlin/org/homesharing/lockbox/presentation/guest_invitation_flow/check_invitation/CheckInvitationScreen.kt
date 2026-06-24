package org.homesharing.lockbox.presentation.guest_invitation_flow.check_invitation

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
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import lockbox.sharedui.generated.resources.Res
import lockbox.sharedui.generated.resources.ic_calendar_days
import lockbox.sharedui.generated.resources.ic_camera
import lockbox.sharedui.generated.resources.check_in_report
import lockbox.sharedui.generated.resources.check_in_setup
import lockbox.sharedui.generated.resources.check_invitation
import lockbox.sharedui.generated.resources.check_out_report
import lockbox.sharedui.generated.resources.ic_chevron_left
import lockbox.sharedui.generated.resources.dates
import lockbox.sharedui.generated.resources.dates_and_conditions
import lockbox.sharedui.generated.resources.deposit
import lockbox.sharedui.generated.resources.flat
import lockbox.sharedui.generated.resources.guest
import lockbox.sharedui.generated.resources.guest_count
import lockbox.sharedui.generated.resources.guest_invitation
import lockbox.sharedui.generated.resources.ic_hand_coins
import lockbox.sharedui.generated.resources.ic_house
import lockbox.sharedui.generated.resources.ic_id_card
import lockbox.sharedui.generated.resources.ic_mail
import lockbox.sharedui.generated.resources.request_passport
import lockbox.sharedui.generated.resources.send_invitation
import lockbox.sharedui.generated.resources.sign_deal_online
import lockbox.sharedui.generated.resources.ic_signature
import lockbox.sharedui.generated.resources.ic_user
import lockbox.sharedui.generated.resources.ic_users
import org.homesharing.lockbox.domain.model.check_in_setup.CheckInSetup
import org.homesharing.lockbox.presentation.uikit.composable.button.HomesharingButton
import org.homesharing.lockbox.presentation.uikit.composable.changeable.HomesharingChangeablePropertyContainer
import org.homesharing.lockbox.presentation.uikit.composable.chip.HomesharingChip
import org.homesharing.lockbox.presentation.uikit.composable.input.HomesharingInput
import org.homesharing.lockbox.presentation.uikit.composable.property.HomesharingProperty
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme
import org.jetbrains.compose.resources.pluralStringResource
import org.koin.compose.viewmodel.koinViewModel

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
                    text = stringResource(Res.string.guest_invitation),
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
            text = stringResource(Res.string.check_invitation),
            style = HomesharingTheme.typography.titleSmall,
            fontWeight = FontWeight.Bold,
            color = HomesharingTheme.colors.black,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
        )

        HomesharingChangeablePropertyContainer(
            title = stringResource(Res.string.guest),
            onChangeClick = onChangeGuestInfo,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp),
        ) {
            HomesharingProperty(
                painter = painterResource(Res.drawable.ic_user),
                name = uiState.guestName,
                modifier = Modifier
                    .fillMaxWidth()
            )

            HomesharingProperty(
                painter = painterResource(Res.drawable.ic_mail),
                name = uiState.guestEmail,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        HomesharingChangeablePropertyContainer(
            title = stringResource(Res.string.flat),
            onChangeClick = onChangeFlatInfo,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp),
        ) {
            HomesharingProperty(
                painter = painterResource(Res.drawable.ic_house),
                name = uiState.buildingName,
                description = uiState.buildingAddress,
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        HomesharingChangeablePropertyContainer(
            title = stringResource(Res.string.dates_and_conditions),
            onChangeClick = onChangeDates,
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 16.dp)
                .padding(top = 24.dp),
        ) {
            HomesharingProperty(
                painter = painterResource(Res.drawable.ic_calendar_days),
                name = stringResource(
                    Res.string.dates,
                    uiState.checkInDate,
                    uiState.checkOutDate,
                    uiState.checkInTime,
                    uiState.checkOutTime
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )

            HomesharingProperty(
                painter = painterResource(Res.drawable.ic_users),
                name = pluralStringResource(
                    Res.plurals.guest_count,
                    uiState.guestCount,
                    uiState.guestCount
                ),
                modifier = Modifier
                    .fillMaxWidth()
            )

            HomesharingProperty(
                painter = painterResource(Res.drawable.ic_hand_coins),
                name = stringResource(Res.string.deposit, uiState.deposit),
                modifier = Modifier
                    .fillMaxWidth()
            )
        }

        HomesharingChangeablePropertyContainer(
            title = stringResource(Res.string.check_in_setup),
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
                            CheckInSetup.CheckInReport -> stringResource(Res.string.check_in_report)
                            CheckInSetup.CheckOutReport -> stringResource(Res.string.check_out_report)
                            CheckInSetup.RequestPassport -> stringResource(Res.string.request_passport)
                            CheckInSetup.SignDealOnline -> stringResource(Res.string.sign_deal_online)
                        },
                        painter = painterResource(
                            when (perk) {
                                CheckInSetup.CheckInReport -> Res.drawable.ic_camera
                                CheckInSetup.CheckOutReport -> Res.drawable.ic_camera
                                CheckInSetup.RequestPassport -> Res.drawable.ic_id_card
                                CheckInSetup.SignDealOnline -> Res.drawable.ic_signature
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
            title = stringResource(Res.string.send_invitation),
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