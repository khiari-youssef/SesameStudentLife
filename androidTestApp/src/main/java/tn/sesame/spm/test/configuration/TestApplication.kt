package tn.sesame.spm.test.configuration

import android.app.Application
import androidx.test.platform.app.InstrumentationRegistry
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.core.module.Module
import tn.sesame.spm.android.di.viewModelsModule
import tn.sesame.spm.di.androidSecurityModule

class TestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(
                InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
            )
            modules(listOf(androidSecurityModule, viewModelsModule))
        }
    }
}