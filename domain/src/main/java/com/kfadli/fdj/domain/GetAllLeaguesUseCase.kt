package com.kfadli.fdj.domain

import com.kfadli.fdj.data.League
import com.kfadli.fdj.data.LeaguesRepository
import com.kfadli.fdj.domain.model.LeagueUI

class GetAllLeaguesUseCase(
    private val repository: LeaguesRepository,
) {
    suspend operator fun invoke(): Result<List<LeagueUI>> = repository.getAllLeagues().map { it.toLeagueUI() }
}

private fun List<League>.toLeagueUI(): List<LeagueUI> = map { LeagueUI(it.id, it.name, "") }
