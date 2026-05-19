package org.homesharing.lockbox.presentation.flats_flow.add_or_import_flat

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
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import lockbox.sharedui.generated.resources.Res
import lockbox.sharedui.generated.resources.add_a_flat
import lockbox.sharedui.generated.resources.by_hand
import lockbox.sharedui.generated.resources.imported
import lockbox.sharedui.generated.resources.ic_link
import lockbox.sharedui.generated.resources.not_now
import lockbox.sharedui.generated.resources.proceed
import lockbox.sharedui.generated.resources.soon
import lockbox.sharedui.generated.resources.ic_square_pen
import org.homesharing.lockbox.domain.model.add_flat.AddFlatType
import org.homesharing.lockbox.presentation.uikit.composable.button.HomesharingButton
import org.homesharing.lockbox.presentation.uikit.composable.button.HomesharingButtonDefaults
import org.homesharing.lockbox.presentation.uikit.composable.pickable.HomesharingPickableItem
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun AddFlatScreen(
    viewModel: AddFlatViewModel = koinViewModel(),
    onProceed: () -> Unit,
) {
    val addType by viewModel.addType.collectAsStateWithLifecycle()
    AddFlatScreen(
        addType = addType,
        onSelectRole = viewModel::changeFlatType,
        onProceed = onProceed,
        modifier = Modifier.fillMaxSize(),
    )
}

@Composable
private fun AddFlatScreen(
    addType: AddFlatType?,
    onSelectRole: (AddFlatType) -> Unit,
    onProceed: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier.background(HomesharingTheme.colors.white),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = stringResource(Res.string.add_a_flat),
            style = HomesharingTheme.typography.titleLarge,
            fontWeight = FontWeight.Bold,
            color = HomesharingTheme.colors.black,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 24.dp)
                .padding(horizontal = 16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.spacedBy(16.dp, Alignment.CenterHorizontally)
        ) {
            Box(
                modifier = Modifier.weight(1f)
            ) {
                HomesharingPickableItem(
                    icon = painterResource(Res.drawable.ic_link),
                    title = stringResource(Res.string.imported),
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

            HomesharingPickableItem(
                icon = painterResource(Res.drawable.ic_square_pen),
                title = stringResource(Res.string.by_hand),
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
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(horizontal = 11.dp)
        ) { addType ->
            when (addType) {
                null -> HomesharingButton(
                    title = stringResource(Res.string.not_now),
                    onClick = onProceed,
                    style = HomesharingButtonDefaults.secondary(),
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 24.dp)
                )

                else -> HomesharingButton(
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
private fun PreviewAddOrImportFlatScreen() {
    HomesharingTheme {
        AddFlatScreen(
            addType = null,
            onSelectRole = {},
            onProceed = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}