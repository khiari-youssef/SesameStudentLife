package tn.sesame.spm.android.base

import android.app.Application
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.loadKoinModules
import org.koin.core.context.startKoin
import tn.sesame.spm.android.di.viewModelsModule
import tn.sesame.spm.di.androidSecurityModule


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