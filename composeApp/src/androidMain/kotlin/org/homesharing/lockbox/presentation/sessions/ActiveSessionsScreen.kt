package org.homesharing.lockbox.presentation.sessions

import android.icu.text.SimpleDateFormat
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.pluralStringResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.core.os.ConfigurationCompat
import androidx.core.os.LocaleListCompat
import org.homesharing.lockbox.R
import presentation.composables.TitledTopBar
import presentation.composables.general.BasicGroup
import presentation.composables.general.BasicHorizontalDivider
import presentation.theme.LockBoxTheme
import java.util.Date

private const val minuteMillis = 60 * 1000L
private const val hourMillis = 60 * minuteMillis
private const val dayMillis = 24 * hourMillis
private const val fourteenDaysMillis = 14 * dayMillis

@Composable
fun ActiveSessionScreen(
    activeSessionsUiState: ActiveSessionsUiState,
    onNavBack: () -> Unit,
    onFinishSessions: () -> Unit,
    modifier: Modifier = Modifier
) {
    Column(
        modifier = modifier.background(LockBoxTheme.colors.grey950),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        TitledTopBar(
            title = stringResource(R.string.active_sessions),
            onNavigate = onNavBack,
            modifier = Modifier.fillMaxWidth()
        )

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 12.dp, horizontal = 16.dp),
            verticalArrangement = Arrangement.Top,
            horizontalAlignment = Alignment.Start
        ) {
            Text(
                text = stringResource(R.string.this_device),
                style = LockBoxTheme.typography.subHeadlineLarge,
                color = LockBoxTheme.colors.grey500,
                modifier = Modifier.padding(top = 12.dp)
            )

            BasicGroup(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 6.dp, bottom = 12.dp),
            ) {
                Device(
                    painter = painterResource(R.drawable.smartphone),
                    name = "${Build.MANUFACTURER} ${Build.DEVICE}".uppercase(),
                    location = "Saint P.",
                    accessTime = stringResource(R.string.right_now),
                    modifier = Modifier.fillMaxWidth()
                )

                BasicHorizontalDivider(modifier = Modifier.fillMaxWidth())

                Text(
                    text = stringResource(R.string.finish_all_other_sessions),
                    style = LockBoxTheme.typography.headlineLarge,
                    color = LockBoxTheme.colors.red600,
                    modifier = Modifier
                        .clickable { onFinishSessions() }
                        .fillMaxWidth()
                        .padding(vertical = 14.dp, horizontal = 16.dp)
                )
            }

            Text(
                text = stringResource(R.string.active_sessions),
                style = LockBoxTheme.typography.subHeadlineLarge,
                color = LockBoxTheme.colors.grey500,
                modifier = Modifier.padding(top = 12.dp)
            )
            BasicGroup(
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
                        BasicHorizontalDivider(modifier = Modifier.fillMaxWidth())
                    }
                }
            }
        }
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
            // Если прошло менее часа, считаем минуты
            val minutes = (diff / minuteMillis).toInt().coerceAtLeast(1)
            pluralStringResource(R.plurals.minutes_ago, minutes, minutes)
        }

        diff < dayMillis -> {
            // Если прошло менее суток, считаем часы
            val hours = (diff / hourMillis).toInt().coerceAtLeast(1)
            pluralStringResource(R.plurals.hours_ago, hours, hours)
        }

        else -> {
            // Иначе – считаем дни
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
    modifier: Modifier = Modifier
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
                color = LockBoxTheme.colors.white
            )

            Text(
                text = stringResource(R.string.location_dot_access_time, location, accessTime),
                style = LockBoxTheme.typography.footnote,
                color = LockBoxTheme.colors.grey500
            )
        }
    }
}