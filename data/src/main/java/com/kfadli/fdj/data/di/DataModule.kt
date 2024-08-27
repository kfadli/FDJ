package com.kfadli.fdj.data.di

import com.kfadli.fdj.data.datasource.LeaguesApiDataSource
import com.kfadli.fdj.data.datasource.LeaguesApiDataSourceImpl
import com.kfadli.fdj.data.repository.LeaguesRepository
import com.kfadli.fdj.data.repository.LeaguesRepositoryImpl
import org.koin.core.qualifier.named
import org.koin.dsl.module

val dataModule =
    module {
        single<LeaguesRepository> { LeaguesRepositoryImpl(get()) }
        single<LeaguesApiDataSource> { LeaguesApiDataSourceImpl(get(named(THE_SPORTS_DB_API))) }
    }
