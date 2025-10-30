package com.youapps.onlybeans.android.base

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import com.youapps.onlybeans.android.di.viewModelsModule
import com.youapps.onlybeans.di.androidSecurityModule


class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            loadKoinModules(
               listOf(
                   viewModelsModule,
                   androidSecurityModule
               )
            )
        }
    }
}