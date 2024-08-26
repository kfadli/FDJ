package com.kfadli.fdj.data

import com.kfadli.fdj.data.model.League
import com.kfadli.fdj.data.model.Team
import com.kfadli.fdj.data.network.response.LeagueResponse
import com.kfadli.fdj.data.network.response.TeamResponse
import timber.log.Timber

class LeaguesRepository(
    private val dataSource: LeaguesApiDataSource,
) {
    suspend fun getAllLeagues(): Result<List<League>> {
        Timber.v("[getAllLeagues")
        return dataSource.getAllLeagues().map { it.leagues.toLeagues() }
    }

    suspend fun getTeamsByLeague(leagueName: String): Result<List<Team>> {
        Timber.v("[getTeamsByLeague] leagueName: $leagueName")
        return dataSource.getTeamsByLeague(leagueName).map { it.teams?.toTeams() ?: emptyList() }
    }
}

private fun List<TeamResponse>.toTeams(): List<Team> =
    map { response ->
        Team(
            id = response.id.toLong(),
            name = response.name,
            badgeUrl = response.badge,
        )
    }

private fun List<LeagueResponse>.toLeagues(): List<League> = map { response -> League(response.id.toLong(), response.name) }
