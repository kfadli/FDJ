package com.kfadli.fdj.di

import com.kfadli.fdj.core.coreModule
import com.kfadli.fdj.data.di.dataModule
import com.kfadli.fdj.data.di.networkModule
import com.kfadli.fdj.domain.di.usecaseModule
import com.kfadli.fdj.features.di.featuresModule

fun appModule() = listOf(coreModule, featuresModule, usecaseModule, dataModule, networkModule)
