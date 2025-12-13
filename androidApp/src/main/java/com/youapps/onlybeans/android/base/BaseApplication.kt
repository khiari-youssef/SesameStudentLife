package com.youapps.onlybeans.android.base

import android.app.Application
import com.youapps.onlybeans.android.di.viewModelsModule
import com.youapps.onlybeans.di.androidSecurityModule
import com.youapps.onlybeans.di.platformServicesModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin


class BaseApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(this@BaseApplication)
            loadKoinModules(
               listOf(
                   viewModelsModule,
                   androidSecurityModule,
                   platformServicesModule
               )
            )
        }

    }
}