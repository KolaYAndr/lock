package org.homesharing.lockbox.presentation_v2.profile_flow.role_chooser

import androidx.activity.compose.BackHandler
import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.homesharing.lockbox.R
import org.homesharing.lockbox.domain.model.role.UserRole
import org.homesharing.lockbox.presentation_v2.uikit.composable.button.HomesharingButton
import org.homesharing.lockbox.presentation_v2.uikit.composable.button.HomesharingButtonDefaults
import org.homesharing.lockbox.presentation_v2.uikit.composable.pickable.HomesharingPickableItem
import org.homesharing.lockbox.presentation_v2.uikit.theme.HomesharingTheme
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun RoleChooserScreen(
    viewModel: RoleChooserViewModel = koinViewModel(),
    onProceed: () -> Unit,
) {
    BackHandler(enabled = false) { }
    val pickedRole by viewModel.pickedRole.collectAsStateWithLifecycle()
    RoleChooserScreen(
        pickedRole = pickedRole,
        onSelectRole = viewModel::onPickRole,
        onProceed = onProceed,
        modifier = Modifier.fillMaxSize(),
    )
}

@Composable
private fun RoleChooserScreen(
    pickedRole: UserRole?,
    onSelectRole: (UserRole) -> Unit,
    onProceed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.background(HomesharingTheme.colors.white),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.pick_a_role),
            style = HomesharingTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = HomesharingTheme.colors.black,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .padding(horizontal = 11.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
        ) {
            HomesharingPickableItem(
                icon = painterResource(R.drawable.door_open),
                title = stringResource(R.string.guest),
                isSelected = pickedRole == UserRole.Guest,
                onClick = {
                    onSelectRole(UserRole.Guest)
                },
                modifier = Modifier.weight(1f),
                role = Role.RadioButton
            )

            HomesharingPickableItem(
                icon = painterResource(R.drawable.house),
                title = stringResource(R.string.owner),
                isSelected = pickedRole == UserRole.Owner,
                onClick = {
                    onSelectRole(UserRole.Owner)
                },
                modifier = Modifier.weight(1f),
                role = Role.RadioButton
            )
        }

        AnimatedContent(
            targetState = pickedRole,
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 11.dp)
        ) { role ->
            when (role) {
                null -> HomesharingButton(
                    title = stringResource(R.string.not_now),
                    onClick = onProceed,
                    style = HomesharingButtonDefaults.secondary(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                )

                else -> HomesharingButton(
                    title = stringResource(R.string.proceed),
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
        RoleChooserScreen(
            pickedRole = null,
            onSelectRole = {},
            onProceed = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}