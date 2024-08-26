package com.kfadli.fdj.domain

import com.kfadli.fdj.data.League
import com.kfadli.fdj.data.LeaguesRepository

class GetLeagueByIdUseCase(
    private val repository: LeaguesRepository,
) {
    suspend operator fun invoke(id: Long): Result<League> = repository.getLeague(id)
}
