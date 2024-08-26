package com.kfadli.fdj.data.di

import com.kfadli.fdj.data.network.ClientApiFactory
import kotlinx.serialization.json.Json
import org.koin.core.qualifier.named
import org.koin.dsl.module

val networkModule =
    module {
        single {
            Json {
                ignoreUnknownKeys = true
                prettyPrint = true
            }
        }
        single {
            ClientApiFactory(
                engine = getOrNull(),
                json = get(),
            )
        }
        single(named(THE_SPORTS_DB_API)) {
            get<ClientApiFactory>().create(THE_SPORTS_DB)
        }
    }

const val THE_SPORTS_DB = "THE_SPORTS_DB"
const val THE_SPORTS_DB_API = "THE_SPORTS_DB_API"
