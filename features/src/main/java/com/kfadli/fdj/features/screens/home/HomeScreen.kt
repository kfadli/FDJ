package com.kfadli.fdj.features.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.kfadli.fdj.domain.models.LeagueUI
import com.kfadli.fdj.domain.models.TeamUI
import com.kfadli.fdj.features.extensions.toStringError
import com.kfadli.fdj.features.screens.home.UIState.*
import com.kfadli.fdj.features.ui.AutoCompleteSelectBar
import com.kfadli.fdj.features.ui.BadgeFromUrl
import com.kfadli.fdj.features.ui.FDJLoadingDialog
import com.kfadli.fdj.features.ui.FDJPopupDialog
import org.koin.compose.viewmodel.koinViewModel

@Composable
fun HomeScreen(modifier: Modifier) {
    val viewModel = koinViewModel<HomeViewModel>()
    val state = viewModel.state.collectAsState()

    LaunchedEffect(Unit) {
        viewModel.getLeagues()
    }

    HomeLayout(
        modifier = modifier,
        state = state.value,
        onSelected = { viewModel.getTeams(it) },
        onRetry = { viewModel.getLeagues() },
    )
}

@Composable
fun HomeLayout(
    modifier: Modifier = Modifier,
    state: UIState,
    onSelected: (String) -> Unit,
    onRetry: () -> Unit,
) {
    val teams = remember { mutableStateOf<List<TeamUI>?>(null) }
    val leagues = remember { mutableStateOf(emptyList<LeagueUI>()) }

    var showErrorDialog by remember { mutableStateOf(false) }
    var messageErrorDialog by remember { mutableStateOf<String?>(null) }

    when (state) {
        is Failure -> {
            messageErrorDialog = state.cause.toStringError()
            showErrorDialog = true
        }
        Loading -> FDJLoadingDialog()
        is LeagueUISuccess -> leagues.value = state.items
        is TeamsUISuccess -> teams.value = state.items
        else -> {}
    }

    Column(modifier = modifier) {
        AutoCompleteSelectBar(
            entries = leagues.value.map { it.name },
            hint = "Search by league",
            label = "Leagues",
            onSelected = {
                onSelected(it)
            },
        )

        teams.value?.let {
            TeamList(items = it)
        }

        if (showErrorDialog) {
            FDJPopupDialog(
                content = messageErrorDialog ?: "Unknown error",
                textSubmit = "Retry",
                onCancel = {
                    showErrorDialog = false
                    messageErrorDialog = null
                },
                onConfirm =
                    if (leagues.value.isEmpty()) {
                        {
                            showErrorDialog = false
                            messageErrorDialog = null
                            onRetry()
                        }
                    } else {
                        null
                    },
            )
        }
    }
}

@Composable
fun TeamList(items: List<TeamUI>) {
    LazyVerticalGrid(
        modifier = Modifier.padding(16.dp),
        columns = GridCells.Fixed(count = 2),
    ) {
        items(items) {
            TeamItem(it)
        }
    }

    if (items.isEmpty()) {
        Text(
            modifier = Modifier.fillMaxWidth(),
            textAlign = TextAlign.Center,
            text = "Teams not found",
            style = MaterialTheme.typography.bodyMedium,
            fontSize = 18.sp,
        )
    }
}

@Composable
fun TeamItem(team: TeamUI) {
    Column(
        modifier =
            Modifier
                .testTag("team_${team.id}")
                .padding(8.dp)
                .fillMaxWidth(),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {
        BadgeFromUrl(imageUrl = team.iconUrl, contentDescription = team.name)
    }
}
