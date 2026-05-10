package org.homesharing.lockbox.presentation_v2.flats_flow.add_or_import_flat

import androidx.compose.animation.AnimatedContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
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
import androidx.compose.ui.graphics.graphicsLayer
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.homesharing.lockbox.R
import org.homesharing.lockbox.domain.model.add_flat.AddFlatType
import org.homesharing.lockbox.presentation_v2.uikit.composable.button.HomesharingButton
import org.homesharing.lockbox.presentation_v2.uikit.composable.button.HomesharingButtonDefaults
import org.homesharing.lockbox.presentation_v2.uikit.composable.pickable.HomesharingPickableItem
import org.homesharing.lockbox.presentation_v2.uikit.theme.HomesharingTheme
import org.koin.androidx.compose.koinViewModel

@Composable
internal fun AddFlatScreen(
    viewModel: AddFlatViewModel = koinViewModel()
) {
    val addType by viewModel.addType.collectAsStateWithLifecycle()
    AddFlatScreen(
        addType = addType,
        onSelectRole = viewModel::changeFlatType,
        modifier = Modifier.fillMaxSize(),
    )
}

@Composable
private fun AddFlatScreen(
    addType: AddFlatType?,
    onSelectRole: (AddFlatType) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(HomesharingTheme.colors.white)
            .padding(horizontal = 16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(R.string.add_a_flat),
            style = HomesharingTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = HomesharingTheme.colors.black,
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                HomesharingPickableItem(
                    icon = painterResource(R.drawable.door_open),
                    title = stringResource(R.string.guest),
                    isSelected = addType == AddFlatType.Import,
                    enabled = false,
                    onClick = {
                        onSelectRole(AddFlatType.Import)
                    },
                    modifier = Modifier
                        .fillMaxWidth()
                        .align(Alignment.Center),
                    role = Role.RadioButton
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
            HomesharingPickableItem(
                icon = painterResource(R.drawable.house),
                title = stringResource(R.string.owner),
                isSelected = addType == AddFlatType.ByHand,
                onClick = {
                    onSelectRole(AddFlatType.ByHand)
                },
                modifier = Modifier.weight(1f),
                role = Role.RadioButton
            )
        }

        AnimatedContent(
            targetState = addType,
            contentAlignment = Alignment.Center
        ) { addType ->
            when (addType) {
                null -> HomesharingButton(
                    title = stringResource(R.string.not_now),
                    onClick = {},
                    style = HomesharingButtonDefaults.secondary(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                )

                else -> HomesharingButton(
                    title = stringResource(R.string.proceed),
                    onClick = {},
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
private fun PreviewAddOrImportFlatScreen() {
    HomesharingTheme {
        AddFlatScreen(
            addType = null,
            onSelectRole = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}