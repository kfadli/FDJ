package com.kfadli.fdj.data.mapper

import com.kfadli.fdj.data.model.League
import com.kfadli.fdj.data.model.Team
import com.kfadli.fdj.data.network.response.LeagueResponse
import com.kfadli.fdj.data.network.response.TeamResponse

fun List<TeamResponse>.toTeams(): List<Team> =
    map { response ->
        Team(
            id = response.id.toLong(),
            name = response.name,
            badgeUrl = response.badge,
        )
    }

fun List<LeagueResponse>.toLeagues(): List<League> = map { response -> League(response.id.toLong(), response.name) }
