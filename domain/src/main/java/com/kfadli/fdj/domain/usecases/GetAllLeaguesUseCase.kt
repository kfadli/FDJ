package com.kfadli.fdj.domain.usecases

import com.kfadli.fdj.data.model.League
import com.kfadli.fdj.data.repository.LeaguesRepository
import com.kfadli.fdj.domain.models.LeagueUI

class GetAllLeaguesUseCase(
    private val repository: LeaguesRepository,
) {
    suspend operator fun invoke(): Result<List<LeagueUI>> = repository.getAllLeagues().map { league -> league.toLeagueUI() }
}

private fun List<League>.toLeagueUI(): List<LeagueUI> = map { LeagueUI(id = it.id, name = it.name) }
