package com.kfadli.fdj.features.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kfadli.fdj.domain.models.LeagueUI
import com.kfadli.fdj.domain.models.TeamUI
import com.kfadli.fdj.domain.usecases.GetAllLeaguesUseCase
import com.kfadli.fdj.domain.usecases.GetTeamsByLeagueUseCase
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(
    private val ioDispatcher: CoroutineDispatcher = Dispatchers.IO,
    private val getLeaguesUseCase: GetAllLeaguesUseCase,
    private val getTeamsUseCase: GetTeamsByLeagueUseCase,
) : ViewModel() {
    private val _state = MutableStateFlow<UIState>(UIState.Loading)
    val state = _state.asStateFlow()

    fun getLeagues() {
        Timber.v("[getLeagues]")
        viewModelScope.launch(ioDispatcher) {
            getLeaguesUseCase()
                .onSuccess {
                    Timber.d("[getLeagues] Success")
                    _state.value = UIState.LeagueUISuccess(items = it)
                }.onFailure {
                    Timber.w(message = "[getLeagues] Failure", t = it)
                    _state.value = UIState.Failure(cause = it)
                    delay(200)
                    _state.value = UIState.Idle
                }
        }
    }

    fun getTeams(leagueName: String) {
        Timber.v("[getTeams] leagueName: $leagueName")
        viewModelScope.launch(ioDispatcher) {
            getTeamsUseCase(leagueName)
                .onSuccess {
                    Timber.d(message = "[getTeams] Success")
                    _state.value = UIState.TeamsUISuccess(items = it)
                }.onFailure {
                    Timber.w(message = "[getTeams] Failure", t = it)
                    _state.value = UIState.Failure(cause = it)
                    delay(200)
                    _state.value = UIState.Idle
                }
        }
    }
}

sealed class UIState {
    data object Idle : UIState()

    data object Loading : UIState()

    data class LeagueUISuccess(
        val items: List<LeagueUI>,
    ) : UIState()

    data class TeamsUISuccess(
        val items: List<TeamUI>,
    ) : UIState()

    data class Failure(
        val cause: Throwable,
    ) : UIState()
}
