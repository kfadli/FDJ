package com.kfadli.fdj.data.datasource

import com.kfadli.fdj.data.network.response.LeaguesResponse
import com.kfadli.fdj.data.network.response.TeamsResponse

interface LeaguesApiDataSource {
    suspend fun getTeamsByLeague(leagueName: String): Result<TeamsResponse>

    suspend fun getAllLeagues(): Result<LeaguesResponse>
}
