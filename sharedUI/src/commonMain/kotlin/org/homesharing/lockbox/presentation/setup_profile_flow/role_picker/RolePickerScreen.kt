package org.homesharing.lockbox.presentation.setup_profile_flow.role_picker

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import lockbox.sharedui.generated.resources.Res
import lockbox.sharedui.generated.resources.guest
import lockbox.sharedui.generated.resources.how_do_you_plan_to_use_app
import lockbox.sharedui.generated.resources.ic_door_open
import lockbox.sharedui.generated.resources.ic_house
import lockbox.sharedui.generated.resources.not_now
import lockbox.sharedui.generated.resources.owner
import lockbox.sharedui.generated.resources.pick_a_role
import lockbox.sharedui.generated.resources.proceed
import org.homesharing.lockbox.domain.model.role.UserRole
import org.homesharing.lockbox.presentation.uikit.composable.button.HomesharingButton
import org.homesharing.lockbox.presentation.uikit.composable.button.HomesharingButtonDefaults
import org.homesharing.lockbox.presentation.uikit.composable.radio_item.HomesharingRadioItem
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun RolePickerScreen(
    viewModel: RolePickerViewModel = koinViewModel(),
    onProceed: () -> Unit,
) {
    val pickedRole by viewModel.pickedRole.collectAsStateWithLifecycle()
    RolePickerScreen(
        pickedRole = pickedRole,
        onSelectRole = viewModel::onPickRole,
        onProceed = onProceed,
        modifier = Modifier.fillMaxSize(),
    )
}

@Composable
private fun RolePickerScreen(
    pickedRole: UserRole?,
    onSelectRole: (UserRole) -> Unit,
    onProceed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.background(HomesharingTheme.colors.white),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(
            text = stringResource(Res.string.how_do_you_plan_to_use_app),
            style = HomesharingTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = HomesharingTheme.colors.black,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 16.dp)
                .statusBarsPadding()
        )

        Text(
            text = stringResource(Res.string.pick_a_role),
            style = HomesharingTheme.typography.bodyMedium,
            color = HomesharingTheme.colors.black,
            modifier = Modifier
                .padding(horizontal = 16.dp)
                .padding(top = 8.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 40.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
        ) {
            HomesharingRadioItem(
                icon = painterResource(Res.drawable.ic_door_open),
                title = stringResource(Res.string.guest),
                isSelected = pickedRole == UserRole.Guest,
                onClick = {
                    onSelectRole(UserRole.Guest)
                },
                modifier = Modifier.weight(1f),
            )

            HomesharingRadioItem(
                icon = painterResource(Res.drawable.ic_house),
                title = stringResource(Res.string.owner),
                isSelected = pickedRole == UserRole.Landlord,
                onClick = {
                    onSelectRole(UserRole.Landlord)
                },
                modifier = Modifier.weight(1f),
            )
        }

        AnimatedContent(
            targetState = pickedRole != null,
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        ) { hasPickedRole ->
            if (!hasPickedRole) {
                HomesharingButton(
                    title = stringResource(Res.string.not_now),
                    onClick = onProceed,
                    style = HomesharingButtonDefaults.secondary(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                )
            } else {
                HomesharingButton(
                    title = stringResource(Res.string.proceed),
                    onClick = onProceed,
                    style = HomesharingButtonDefaults.primary(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                )
            }
        }
    }
}

@Preview
@Composable
private fun PreviewRoleChooserScreen() {
    HomesharingTheme {
        RolePickerScreen(
            pickedRole = null,
            onSelectRole = {},
            onProceed = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}