package com.kfadli.fdj.features.screens.home

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.kfadli.fdj.domain.GetAllLeaguesUseCase
import com.kfadli.fdj.domain.model.LeagueUI
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import timber.log.Timber

class HomeViewModel(
    private val getLeaguesUseCase: GetAllLeaguesUseCase,
) : ViewModel() {
    val state = MutableStateFlow<UIState>(UIState.Loading)

    fun getLeagues() {
        Timber.v("[getLeagues]")
        viewModelScope.launch {
            getLeaguesUseCase()
                .onSuccess {
                    Timber.d("[getLeagues] Success")
                    state.value = UIState.Success(it)
                }.onFailure {
                    Timber.w("[getLeagues] Failure", it)
                    state.value = UIState.Failure(it)
                }
        }
    }
}

sealed class UIState {
    data object Loading : UIState()

    data class Success(
        val items: List<LeagueUI>,
    ) : UIState()

    data class Failure(
        val cause: Throwable,
    ) : UIState()
}
