package org.homesharing.lockbox.presentation.sessions

import android.content.Context
import android.icu.text.SimpleDateFormat
import android.net.ConnectivityManager
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.material3.pulltorefresh.rememberPullToRefreshState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.core.os.ConfigurationCompat
import androidx.core.os.LocaleListCompat
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import org.homesharing.lockbox.R
import org.homesharing.lockbox.domain.model.sessions.Session
import org.homesharing.lockbox.presentation.uikit.composables.TitledTopBar
import org.homesharing.lockbox.presentation.uikit.composables.core.LockBoxPullToRefresh
import org.homesharing.lockbox.presentation.uikit.composables.core.LockboxHorizontalDivider
import org.homesharing.lockbox.presentation.uikit.composables.core.buttons.LockboxButton
import org.homesharing.lockbox.presentation.uikit.composables.core.buttons.LockboxButtonStyle
import org.homesharing.lockbox.presentation.uikit.composables.core.groups.BasicDialogContent
import org.homesharing.lockbox.presentation.uikit.composables.core.groups.LockboxDialog
import org.homesharing.lockbox.presentation.uikit.composables.core.groups.LockboxGroup
import org.homesharing.lockbox.presentation.uikit.theme.LockBoxTheme
import org.koin.androidx.compose.koinViewModel
import java.util.Date
import java.util.concurrent.TimeUnit

private val minuteMillis = TimeUnit.MINUTES.toMillis(1)
private val hourMillis = TimeUnit.HOURS.toMillis(1)
private val dayMillis = TimeUnit.DAYS.toMillis(1)
private val fourteenDaysMillis = TimeUnit.DAYS.toMillis(14)

@Composable
internal fun ActiveSessionScreen(
    activeSessionsViewModel: ActiveSessionsViewModel = koinViewModel(),
) {
    val uiState by activeSessionsViewModel.uiState.collectAsStateWithLifecycle()
    ActiveSessionScreen(
        activeSessionsUiState = uiState,
        onFinishSessions = activeSessionsViewModel::finishOtherSessions,
        onRefresh = activeSessionsViewModel::refreshSessions,
        modifier = Modifier.fillMaxSize()
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
private fun ActiveSessionScreen(
    activeSessionsUiState: ActiveSessionsUiState,
    onFinishSessions: () -> Unit,
    onRefresh: () -> Unit,
    modifier: Modifier = Modifier,
) {
    var showCloseSessionsDialog by remember { mutableStateOf(false) }
    LockBoxPullToRefresh(
        onRefresh = onRefresh,
        isRefreshing = activeSessionsUiState.isRefreshing,
        state = rememberPullToRefreshState(),
        modifier = modifier,
    ) {
        LazyColumn(
            modifier = Modifier
                .background(LockBoxTheme.colors.grey950)
                .fillMaxSize()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            item {
                Text(
                    text = stringResource(R.string.this_device),
                    style = LockBoxTheme.typography.subHeadlineLarge,
                    color = LockBoxTheme.colors.grey500,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }

            item {
                val context = LocalContext.current
                val connectivityManager =
                    remember { context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager }
                val ip = remember {
                    connectivityManager.getLinkProperties(connectivityManager.activeNetwork)?.linkAddresses
                        ?.map { it.address.hostAddress }
                        ?.firstOrNull { it != null && !it.contains(":") }
                }
                LockboxGroup(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp, bottom = 12.dp),
                ) {
                    Device(
                        painter = painterResource(R.drawable.smartphone),
                        name = "${Build.MANUFACTURER} ${Build.DEVICE}".uppercase(),
                        location = ip.orEmpty(),
                        accessTime = stringResource(R.string.right_now),
                        modifier = Modifier.fillMaxWidth()
                    )

                    LockboxHorizontalDivider(modifier = Modifier.fillMaxWidth())

                    LockboxButton(
                        onClick = { showCloseSessionsDialog = true },
                        text = stringResource(R.string.finish_all_other_sessions),
                        style = LockboxButtonStyle.red(),
                        paddingValues = PaddingValues(0.dp),
                        modifier = Modifier
                            .padding(horizontal = 16.dp)
                            .fillMaxWidth()
                    )
                }
            }

            item {
                Text(
                    text = stringResource(R.string.active_sessions),
                    style = LockBoxTheme.typography.subHeadlineLarge,
                    color = LockBoxTheme.colors.grey500,
                    modifier = Modifier.padding(top = 12.dp)
                )
            }

            item {
                LockboxGroup(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 6.dp),
                ) {
                    activeSessionsUiState.sessions.forEachIndexed { index, session ->
                        Device(
                            painter = painterResource(R.drawable.monitor_smartphone),
                            name = stringResource(R.string.unknown_device),
                            location = session.ipAddress,
                            accessTime = lastAccessedString(session.lastAccessed),
                            modifier = Modifier.fillMaxWidth()
                        )

                        if (index != activeSessionsUiState.sessions.lastIndex) {
                            LockboxHorizontalDivider(modifier = Modifier.fillMaxWidth())
                        }
                    }
                }
            }
        }

        if (showCloseSessionsDialog) {
            CloseSessionsDialog(
                onDismiss = { showCloseSessionsDialog = false },
                onFinishSessions = onFinishSessions
            )
        }
    }
}

@Composable
internal fun ActiveSessionsTopBar(onNavBack: () -> Unit) {
    TitledTopBar(
        title = stringResource(R.string.active_sessions),
        onNavigate = onNavBack,
        modifier = Modifier.fillMaxWidth()
    )
}

@Composable
private fun CloseSessionsDialog(
    onDismiss: () -> Unit,
    onFinishSessions: () -> Unit,
    modifier: Modifier = Modifier,
) {
    LockboxDialog(
        onDismiss = onDismiss,
        modifier = modifier
    ) {
        BasicDialogContent(
            title = stringResource(R.string.finish_all_other_sessions_question),
            onDismiss = onDismiss,
            onDangerButtonAction = {
                onFinishSessions()
                onDismiss()
            },
            dangerButtonText = stringResource(R.string.finish)
        )
    }
}

@Composable
@ReadOnlyComposable
private fun lastAccessedString(lastAccessed: Long): String {
    val now = System.currentTimeMillis()
    val diff = now - lastAccessed

    return when {
        diff > fourteenDaysMillis -> {
            val configuration = LocalConfiguration.current
            val sdf = SimpleDateFormat(
                "dd.MM.yyyy",
                ConfigurationCompat.getLocales(configuration).get(0)
                    ?: (LocaleListCompat.getDefault()[0]!!)
            )
            sdf.format(Date(lastAccessed))
        }

        diff < hourMillis -> {
            val minutes = (diff / minuteMillis).toInt().coerceAtLeast(1)
            pluralStringResource(R.plurals.minutes_ago, minutes, minutes)
        }

        diff < dayMillis -> {
            val hours = (diff / hourMillis).toInt().coerceAtLeast(1)
            pluralStringResource(R.plurals.hours_ago, hours, hours)
        }

        else -> {
            val days = (diff / dayMillis).toInt().coerceAtLeast(1)
            pluralStringResource(R.plurals.days_ago, days, days)
        }
    }
}

@Composable
private fun Device(
    painter: Painter,
    name: String,
    location: String,
    accessTime: String,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier.padding(horizontal = 16.dp, vertical = 10.dp),
        verticalAlignment = Alignment.CenterVertically,
        horizontalArrangement = Arrangement.spacedBy(12.dp, Alignment.Start)
    ) {
        Icon(
            painter = painter,
            contentDescription = null,
            tint = LockBoxTheme.colors.grey100,
            modifier = Modifier.size(28.dp)
        )

        Column(
            modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.spacedBy(2.dp, Alignment.CenterVertically),
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = name,
                style = LockBoxTheme.typography.headlineLarge,
                color = LockBoxTheme.colors.constantWhite
            )

            Text(
                text = stringResource(R.string.location_dot_access_time, location, accessTime),
                style = LockBoxTheme.typography.footnote,
                color = LockBoxTheme.colors.grey500
            )
        }
    }
}

@Preview
@Composable
private fun PreviewActiveSessions() {
    LockBoxTheme {
        val state = ActiveSessionsUiState(listOf(Session("", System.currentTimeMillis(), "")))
        ActiveSessionScreen(state, {}, {}, Modifier.fillMaxSize())
    }
}
