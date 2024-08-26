package com.kfadli.fdj

import android.app.Application
import com.kfadli.fdj.di.appModule
import org.koin.android.ext.koin.androidContext
import org.koin.android.ext.koin.androidLogger
import org.koin.core.context.GlobalContext.startKoin
import timber.log.Timber

class FDJApplication : Application() {
    override fun onCreate() {
        super.onCreate()

        Timber.plant(Timber.DebugTree())

        startKoin {
            androidLogger()
            androidContext(this@FDJApplication)
            modules(appModule())
        }
    }
}
