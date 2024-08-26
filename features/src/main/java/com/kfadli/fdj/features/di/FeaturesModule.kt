package com.kfadli.fdj.features.di

import com.kfadli.fdj.features.screens.home.HomeViewModel
import org.koin.core.module.dsl.viewModelOf
import org.koin.dsl.module

val featuresModule =
    module {
        viewModelOf(::HomeViewModel)
    }
