package org.homesharing.lockbox.presentation.flat_detailed

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBarsPadding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import lockbox.sharedui.generated.resources.Res
import lockbox.sharedui.generated.resources.commercial
import lockbox.sharedui.generated.resources.failed_to_load_flat_info
import lockbox.sharedui.generated.resources.flat_info
import lockbox.sharedui.generated.resources.ic_chevron_left
import lockbox.sharedui.generated.resources.ic_flat
import lockbox.sharedui.generated.resources.living
import org.homesharing.lockbox.domain.model.premise_type.PremiseType
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun FlatDetailedScreen(
    viewModel: FlatDetailedViewModel = koinViewModel(),
    onNavBack: () -> Unit,
) {
    val flatDetailedState by viewModel.flat.collectAsStateWithLifecycle()

    FlatDetailedScreen(
        state = flatDetailedState,
        onNavBack = onNavBack,
        modifier = Modifier.fillMaxSize()
    )
}

@Composable
private fun FlatDetailedScreen(
    state: FlatDetailedUiState,
    onNavBack: () -> Unit,
    modifier: Modifier = Modifier,
) {
    when {
        state.loading -> {
            LoadingScreen(modifier = modifier)
        }
        state.error -> {
            ErrorScreen(modifier = modifier)
        }

        else -> {
            Column(
                modifier = modifier.background(HomesharingTheme.colors.white),
                verticalArrangement = Arrangement.Top,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                CenterAlignedTopAppBar(
                    title = {
                        Text(
                            text = stringResource(Res.string.flat_info),
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

                Image(
                    painter = painterResource(Res.drawable.ic_flat),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(top = 16.dp),
                )

                Text(
                    text = when (state.premiseType) {
                        PremiseType.RESIDENTIAL -> stringResource(Res.string.living)
                        PremiseType.NON_RESIDENTIAL -> stringResource(Res.string.commercial)
                    },
                    style = HomesharingTheme.typography.bodySmall,
                    modifier = Modifier
                        .padding(top = 24.dp)
                        .padding(horizontal = 16.dp),
                    color = HomesharingTheme.colors.black.copy(alpha = .3f)
                )

                Text(
                    text = state.name,
                    style = HomesharingTheme.typography.titleLarge,
                    modifier = Modifier.padding(horizontal = 16.dp)
                )

                Text(
                    text = state.address,
                    style = HomesharingTheme.typography.bodyMedium,
                    modifier = Modifier
                        .padding(top = 8.dp)
                        .padding(horizontal = 16.dp)
                )
            }
        }
    }
}

@Composable
private fun ErrorScreen(
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(HomesharingTheme.colors.white)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        Text(
            text = stringResource(Res.string.failed_to_load_flat_info),
            style = HomesharingTheme.typography.bodyMedium,
            color = HomesharingTheme.colors.black,
        )
    }
}

@Composable
private fun LoadingScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .background(HomesharingTheme.colors.white),
        contentAlignment = Alignment.Center,
    ) {
        CircularProgressIndicator(color = HomesharingTheme.colors.black)
    }
}

@Composable
@Preview
private fun PreviewFlatDetailedScreen() {
    HomesharingTheme {
        FlatDetailedScreen(
            state = FlatDetailedUiState.Initial,
            onNavBack = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}