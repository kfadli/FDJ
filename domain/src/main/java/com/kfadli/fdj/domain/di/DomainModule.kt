package com.kfadli.fdj.domain.di

import com.kfadli.fdj.domain.GetAllLeaguesUseCase
import com.kfadli.fdj.domain.GetLeagueByIdUseCase
import org.koin.dsl.module

val usecaseModule =
    module {
        single { GetLeagueByIdUseCase(get()) }
        single { GetAllLeaguesUseCase(get()) }
    }
