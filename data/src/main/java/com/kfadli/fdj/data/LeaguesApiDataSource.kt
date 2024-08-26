package com.kfadli.fdj.data

import io.ktor.client.HttpClient

class LeaguesApiDataSource(
    private val clientApi: HttpClient,
) {
    fun getLeague(leagueId: Long): Result<League> = Result.success(League(1L, "Champions League"))

    fun getAllLeagues(): Result<List<League>> =
        Result.success(
            listOf(
                League(id = 1L, name = "Champions League"),
                League(2L, "Europa League"),
            ),
        )
}
