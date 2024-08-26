package com.kfadli.fdj.data

class LeaguesRepository(
    private val dataSource: LeaguesApiDataSource,
) {
    suspend fun getLeague(leagueId: Long): Result<League> = dataSource.getLeague(leagueId)

    suspend fun getAllLeagues(): Result<List<League>> = dataSource.getAllLeagues()
}
