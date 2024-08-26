package com.kfadli.fdj.data.di

import com.kfadli.fdj.data.LeaguesApiDataSource
import com.kfadli.fdj.data.LeaguesRepository
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule =
    module {
        single<LeaguesRepository> { LeaguesRepository(get()) }
        single<LeaguesApiDataSource> { LeaguesApiDataSource(get(named(THE_SPORTS_DB_API))) }
    }
