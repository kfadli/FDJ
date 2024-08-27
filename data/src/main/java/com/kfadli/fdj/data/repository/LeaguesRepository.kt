package com.kfadli.fdj.data.repository

import com.kfadli.fdj.data.model.League
import com.kfadli.fdj.data.model.Team

interface LeaguesRepository {
    suspend fun getAllLeagues(): Result<List<League>>

    suspend fun getTeamsByLeague(leagueName: String): Result<List<Team>>
}
