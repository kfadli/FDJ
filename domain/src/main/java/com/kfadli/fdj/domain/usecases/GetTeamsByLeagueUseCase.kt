package com.kfadli.fdj.domain.usecases

import com.kfadli.fdj.data.model.Team
import com.kfadli.fdj.data.repository.LeaguesRepository
import com.kfadli.fdj.domain.models.TeamUI

class GetTeamsByLeagueUseCase(
    private val repository: LeaguesRepository,
) {
    suspend operator fun invoke(leagueName: String): Result<List<TeamUI>> =
        repository
            .getTeamsByLeague(leagueName = leagueName)
            .map { team -> team.toTeamUI().sortedByDescending { it.name } }
}

private fun List<Team>.toTeamUI(): List<TeamUI> =
    map {
        TeamUI(
            id = it.id,
            name = it.name,
            iconUrl = it.badgeUrl ?: "",
        )
    }
