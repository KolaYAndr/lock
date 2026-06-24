package org.homesharing.lockbox.presentation.flat_list

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.paging.LoadState
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import androidx.paging.compose.itemContentType
import androidx.paging.compose.itemKey
import kotlinx.coroutines.flow.flowOf
import lockbox.sharedui.generated.resources.Res
import lockbox.sharedui.generated.resources.failed_to_load_flats
import lockbox.sharedui.generated.resources.failed_to_load_more_flats
import lockbox.sharedui.generated.resources.ic_flat
import lockbox.sharedui.generated.resources.retry
import org.homesharing.lockbox.domain.model.flat.Flat
import org.homesharing.lockbox.domain.model.premise_type.PremiseType
import org.homesharing.lockbox.presentation.uikit.composable.button.HomesharingButton
import org.homesharing.lockbox.presentation.uikit.composable.refresh.HomesharingPullToRefreshBox
import org.homesharing.lockbox.presentation.uikit.theme.HomesharingTheme
import org.jetbrains.compose.resources.painterResource
import org.jetbrains.compose.resources.stringResource
import org.koin.compose.viewmodel.koinViewModel

@Composable
internal fun FlatListScreen(
    viewModel: FlatListViewModel = koinViewModel(),
    onNavigateToDetails: (String) -> Unit,
) {
    val lazyFlats = viewModel.flats.collectAsLazyPagingItems()

    FlatDetailedScreen(
        lazyFlats = lazyFlats,
        onNavigateToDetails = onNavigateToDetails,
        modifier = Modifier.fillMaxSize(),
    )
}

@Composable
private fun FlatDetailedScreen(
    lazyFlats: LazyPagingItems<Flat>,
    onNavigateToDetails: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (lazyFlats.loadState.refresh) {
        is LoadState.Loading -> LoadingScreen(modifier = modifier)

        is LoadState.Error -> ErrorScreen(
            onRetry = { lazyFlats.retry() },
            modifier = modifier,
        )

        is LoadState.NotLoading -> HomesharingPullToRefreshBox(
            isRefreshing = false,
            onRefresh = lazyFlats::refresh,
            modifier = modifier.background(HomesharingTheme.colors.white),
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxSize(),
                contentPadding = PaddingValues(vertical = 12.dp),
            ) {
                items(
                    count = lazyFlats.itemCount,
                    key = lazyFlats.itemKey { it.id },
                    contentType = lazyFlats.itemContentType { "flat" }
                ) { index ->
                    lazyFlats[index]?.let { flat ->
                        Flat(
                            flat = flat,
                            onNavigateToDetails = onNavigateToDetails,
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                when (lazyFlats.loadState.append) {
                    is LoadState.Loading -> item { LoadingItem() }
                    is LoadState.Error -> item { ErrorItem(onRetry = { lazyFlats.retry() }) }
                    is LoadState.NotLoading -> Unit
                }
            }
        }
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
private fun ErrorScreen(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier
            .background(HomesharingTheme.colors.white)
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp, Alignment.CenterVertically),
    ) {
        Text(
            text = stringResource(Res.string.failed_to_load_flats),
            style = HomesharingTheme.typography.bodyMedium,
            color = HomesharingTheme.colors.black,
        )
        HomesharingButton(
            title = stringResource(Res.string.retry),
            onClick = onRetry,
        )
    }
}

@Composable
private fun LoadingItem(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator(
            modifier = Modifier.size(24.dp),
            color = HomesharingTheme.colors.black
        )
    }
}

@Composable
private fun ErrorItem(
    onRetry: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier
            .fillMaxWidth()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.spacedBy(8.dp)
    ) {
        Text(
            text = stringResource(Res.string.failed_to_load_more_flats),
            style = HomesharingTheme.typography.bodySmall,
            color = HomesharingTheme.colors.black
        )

        HomesharingButton(
            title = stringResource(Res.string.retry),
            onClick = onRetry,

        )
    }
}

@Composable
private fun Flat(
    flat: Flat,
    onNavigateToDetails: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier
            .clickable(
                onClick = { onNavigateToDetails(flat.id) },
                role = Role.Button,
            )
            .padding(horizontal = 16.dp, vertical = 12.dp),
        horizontalArrangement = Arrangement.spacedBy(16.dp),
        verticalAlignment = Alignment.CenterVertically,
    ) {
        Image(
            painter = painterResource(Res.drawable.ic_flat),
            contentDescription = null,
            modifier = Modifier.size(96.dp),
        )

        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.spacedBy(4.dp),
            horizontalAlignment = Alignment.Start,
        ) {
            Text(
                text = flat.name,
                style = HomesharingTheme.typography.bodyMedium,
                color = HomesharingTheme.colors.black
            )

            Text(
                text = flat.address,
                style = HomesharingTheme.typography.bodySmall,
                color = HomesharingTheme.colors.black.copy(alpha = .5f)
            )
        }
    }
}


@Composable
@Preview
private fun FlatListScreenPreview() {
    val mockFlats = listOf(
        Flat(
            id = "1",
            address = "ул. Пушкина, д. Колотушкина",
            name = "Уютная квартира",
            premiseType = PremiseType.RESIDENTIAL,
            livingArea = 50.0
        ),
        Flat(
            id = "2",
            address = "ул. Лермонтова, д. 15",
            name = "Просторные апартаменты",
            premiseType = PremiseType.RESIDENTIAL,
            livingArea = 75.0
        ),
        Flat(
            id = "3",
            address = "ул. Гоголя, д. 10",
            name = "Стильная студия",
            premiseType = PremiseType.RESIDENTIAL,
            livingArea = 35.0
        )
    )
    val lazyFlats = flowOf(PagingData.from(mockFlats)).collectAsLazyPagingItems()

    HomesharingTheme {
        FlatDetailedScreen(
            lazyFlats = lazyFlats,
            onNavigateToDetails = {},
            modifier = Modifier.fillMaxSize()
        )
    }
}

@Composable
@Preview
private fun LoadingItemPreview() {
    HomesharingTheme {
        LoadingItem()
    }
}

@Composable
@Preview
private fun ErrorItemPreview() {
    HomesharingTheme {
        ErrorItem(onRetry = {})
    }
}

@Composable
@Preview
private fun LoadingScreenPreview() {
    HomesharingTheme {
        LoadingScreen()
    }
}

@Composable
@Preview
private fun ErrorScreenPreview() {
    HomesharingTheme {
        ErrorScreen(onRetry = {})
    }
}

@Composable
@Preview
private fun FlatItemPreview() {
    HomesharingTheme {
        Flat(
            flat = Flat(
                id = "1",
                address = "ул. Пушкина, д. Колотушкина",
                name = "Уютная квартира",
                premiseType = PremiseType.RESIDENTIAL,
                livingArea = 50.0
            ),
            onNavigateToDetails = {}
        )
    }
}