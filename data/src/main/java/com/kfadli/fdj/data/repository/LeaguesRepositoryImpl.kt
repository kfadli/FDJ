package com.kfadli.fdj.data.repository

import com.kfadli.fdj.data.datasource.LeaguesApiDataSource
import com.kfadli.fdj.data.mapper.toLeagues
import com.kfadli.fdj.data.mapper.toTeams
import com.kfadli.fdj.data.model.League
import com.kfadli.fdj.data.model.Team
import timber.log.Timber

class LeaguesRepositoryImpl(
    private val dataSource: LeaguesApiDataSource,
) : LeaguesRepository {
    override suspend fun getAllLeagues(): Result<List<League>> {
        Timber.v("[getAllLeagues")
        return dataSource.getAllLeagues().map { it.leagues.toLeagues() }
    }

    override suspend fun getTeamsByLeague(leagueName: String): Result<List<Team>> {
        Timber.v("[getTeamsByLeague] leagueName: $leagueName")
        return dataSource.getTeamsByLeague(leagueName = leagueName).map { it.teams?.toTeams() ?: emptyList() }
    }
}
