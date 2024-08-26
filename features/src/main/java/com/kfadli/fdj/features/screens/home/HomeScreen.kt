package com.kfadli.fdj.features.screens.home

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.lazy.grid.items
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import com.kfadli.fdj.domain.model.LeagueUI
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

    Box(modifier = modifier) {
        HomeLayout(state.value)
    }
}

@Composable
fun HomeLayout(state: UIState) {
    when (state) {
        is UIState.Failure ->
            FDJPopupDialog(
                content = state.cause.message ?: "Unknown error",
                textSubmit = null,
                onCancel = {},
            )

        UIState.Loading -> FDJLoadingDialog()
        is UIState.Success -> LeagueList(state.items)
    }
}

@Composable
fun LeagueList(items: List<LeagueUI>) {
    LazyVerticalGrid(
        columns = GridCells.Adaptive(minSize = 128.dp),
    ) {
        items(items) {
            LeagueItem(it)
        }
    }
}

@Composable
fun LeagueItem(league: LeagueUI) {
    Column(
        modifier =
            Modifier
                .padding(8.dp)
                .fillMaxWidth(),
    ) {
        Text(text = league.name)
    }
}
