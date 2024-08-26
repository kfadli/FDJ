package com.kfadli.fdj.domain.di

import com.kfadli.fdj.domain.usecases.GetAllLeaguesUseCase
import com.kfadli.fdj.domain.usecases.GetTeamsByLeagueUseCase
import org.koin.dsl.module

val usecaseModule =
    module {
        single { GetTeamsByLeagueUseCase(get()) }
        single { GetAllLeaguesUseCase(get()) }
    }
