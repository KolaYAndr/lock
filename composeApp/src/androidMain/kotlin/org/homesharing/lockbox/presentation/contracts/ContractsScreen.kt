package org.homesharing.lockbox.presentation.contracts

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.asPaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.statusBars
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.net.toUri
import androidx.paging.LoadState
import androidx.paging.LoadStates
import androidx.paging.PagingData
import androidx.paging.compose.LazyPagingItems
import androidx.paging.compose.collectAsLazyPagingItems
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOf
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.toJavaLocalDateTime
import org.homesharing.lockbox.R
import org.homesharing.lockbox.domain.model.contract.Contract
import org.homesharing.lockbox.presentation.contracts.viewmodel.ContractsViewModel
import org.homesharing.lockbox.presentation.uikit.composables.AddRow
import org.homesharing.lockbox.presentation.uikit.composables.core.LockBoxPullToRefresh
import org.homesharing.lockbox.presentation.uikit.composables.core.LockboxHorizontalDivider
import org.homesharing.lockbox.presentation.uikit.composables.templates.EmptyScreenContent
import org.homesharing.lockbox.presentation.uikit.composables.templates.ErrorScreenContent
import org.homesharing.lockbox.presentation.uikit.composables.templates.LoadingScreenContent
import org.homesharing.lockbox.presentation.uikit.downloadDocument
import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme
import org.koin.androidx.compose.koinViewModel
import java.time.format.DateTimeFormatter

@Composable
internal fun ContractsScreen(
    contractsViewModel: ContractsViewModel = koinViewModel(),
) {
    ContractsScreen(
        contracts = contractsViewModel.contracts,
        modifier = Modifier.fillMaxSize(),
    )
}

@Composable
private fun ContractsScreen(
    contracts: Flow<PagingData<Contract>>,
    modifier: Modifier = Modifier,
) {
    val lazyContracts = contracts.collectAsLazyPagingItems()
    when (lazyContracts.loadState.refresh) {
        is LoadState.NotLoading -> {
            LockBoxContractScreen(
                contracts = lazyContracts,
                onAddDocument = { },
                modifier = modifier,
            )
        }

        is LoadState.Loading -> {
            LoadingScreenContent(modifier = modifier)
        }

        is LoadState.Error -> {
            ErrorScreen(
                onRefresh = lazyContracts::refresh,
                modifier = modifier
            )
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun LockBoxContractScreen(
    contracts: LazyPagingItems<Contract>,
    onAddDocument: () -> Unit,
    modifier: Modifier,
) {
    val formatter = remember { DateTimeFormatter.ofPattern("d MMM yyyy") }
    val context = LocalContext.current
    LockBoxPullToRefresh(
        modifier = modifier,
        isRefreshing = contracts.loadState.refresh is LoadState.Loading,
        state = rememberPullToRefreshState(),
        onRefresh = contracts::refresh,
    ) {
        val topPadding = WindowInsets.statusBars.only(WindowInsetsSides.Top).asPaddingValues()
            .calculateTopPadding()
        LazyColumn(
            modifier = Modifier.fillMaxSize(),
            contentPadding = PaddingValues(
                top = 12.dp + topPadding,
                start = 16.dp,
                end = 16.dp,
                bottom = 48.dp
            ),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            if (contracts.itemCount == 0) {
                item {
                    EmptyScreenContent(
                        title = stringResource(R.string.manage_contracts),
                        subtitle = stringResource(R.string.here_will_be_contracts_you_create),
                        action = stringResource(R.string.add_contract),
                        onAction = onAddDocument,
                        modifier = Modifier.fillMaxSize()
                    )
                }
            } else {
                items(contracts.itemCount) { index ->
                    val contract = contracts[index]
                    contract?.let {
                        Contract(
                            formatter = formatter,
                            contract = contract,
                            onClick = {},
                            onDelete = {},
                            onDownload = {
                                downloadDocument(
                                    context,
                                    contract.downloadLink.toUri(),
                                    contract.name
                                )
                            },
                            modifier = Modifier.fillMaxWidth()
                        )
                    }
                }

                item {
                    AddRow(
                        onAdd = onAddDocument,
                        text = stringResource(R.string.add_contract),
                        modifier = Modifier.padding(top = 10.dp)
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ErrorScreen(
    onRefresh: () -> Unit,
    modifier: Modifier,
) {
    var isRefreshing by remember { mutableStateOf(false) }
    ErrorScreenContent(
        isRefreshing = isRefreshing,
        onRefresh = {
            isRefreshing = true
            onRefresh()
            isRefreshing = false
        },
        message = stringResource(R.string.something_went_wrong_try_refreshing),
        modifier = modifier
    )
}

@Composable
private fun Contract(
    formatter: DateTimeFormatter,
    contract: Contract,
    onClick: (Contract) -> Unit,
    onDelete: (Contract) -> Unit,
    onDownload: (String) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(modifier = modifier.clickable { onClick(contract) }) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                painterResource(R.drawable.document),
                contentDescription = null,
                modifier = Modifier
                    .padding(vertical = 6.dp)
                    .size(30.dp),
                tint = LockBoxTheme.colors.grey500
            )

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(vertical = 10.dp, horizontal = 12.dp),
                horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically)
            ) {
                Text(
                    text = contract.name,
                    color = LockBoxTheme.colors.constantWhite,
                    style = LockBoxTheme.typography.headlineLarge
                )

                Text(
                    text = contract.creationDate.toJavaLocalDateTime().format(formatter),
                    color = LockBoxTheme.colors.grey500,
                    style = LockBoxTheme.typography.footnote
                )
            }

            Row(
                horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.End),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(
                    onClick = { onDelete(contract) },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.trash),
                        contentDescription = null,
                        tint = LockBoxTheme.colors.red600
                    )
                }

                IconButton(
                    onClick = { onDownload(contract.downloadLink) },
                    modifier = Modifier.size(24.dp)
                ) {
                    Icon(
                        painter = painterResource(R.drawable.download),
                        contentDescription = null,
                        tint = LockBoxTheme.colors.grey500
                    )
                }

                Icon(
                    painter = painterResource(R.drawable.arrow_right),
                    contentDescription = null,
                    tint = LockBoxTheme.colors.grey500
                )
            }
        }

        LockboxHorizontalDivider(modifier = Modifier.fillMaxWidth())
    }
}


@Preview
@Composable
private fun PreviewLockBox() {
    LockBoxTheme {
        val formatter = remember { DateTimeFormatter.ofPattern("d MMM yyyy") }
        Contract(
            formatter = formatter,
            contract = Contract(
                "0",
                "New",
                LocalDateTime(2025, 5, 5, 5, 5, 5, 5),
                ""
            ),
            onClick = {},
            onDelete = {},
            onDownload = {},
            modifier = Modifier.fillMaxWidth()
        )
    }
}

@Preview
@Composable
private fun PreviewContractsScreen() {
    LockBoxTheme {
        val pagingData = flowOf(
            PagingData.from(
                listOf(
                    Contract(
                        "0",
                        "New",
                        LocalDateTime(2025, 5, 5, 5, 5, 5, 5),
                        ""
                    )
                ),
                LoadStates(
                    LoadState.NotLoading(true),
                    LoadState.NotLoading(true),
                    LoadState.NotLoading(true)
                )
            )
        )
        ContractsScreen(pagingData, Modifier.fillMaxSize())
    }
}

@Preview
@Composable
private fun PreviewLoading() {
    LockBoxTheme {
        val pagingData = flowOf(PagingData.empty<Contract>())
        ContractsScreen(pagingData, Modifier.fillMaxSize())
    }
}

@Preview
@Composable
private fun PreviewEmptyScreen() {
    LockBoxTheme {
        val pagingData = flowOf(
            PagingData.empty<Contract>(
                LoadStates(
                    LoadState.NotLoading(false),
                    LoadState.NotLoading(false),
                    LoadState.NotLoading(false)
                )
            )
        )
        ContractsScreen(pagingData, Modifier.fillMaxSize())
    }
}
