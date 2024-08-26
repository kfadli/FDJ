package com.kfadli.fdj.features.di

import com.kfadli.fdj.features.screens.home.HomeViewModel
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module

val featuresModule =
    module {
        viewModel {
            HomeViewModel(
                getLeaguesUseCase = get(),
                getTeamsUseCase = get(),
            )
        }
    }
