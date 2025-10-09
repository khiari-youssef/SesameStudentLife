package tn.sesame.spm.test.configuration

import android.app.Application
import androidx.test.platform.app.InstrumentationRegistry
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import tn.sesame.spm.android.di.viewModelsModule
import tn.sesame.spm.di.androidSecurityModule
import tn.sesame.spm.test.di.testModule

class TestApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        startKoin {
            androidContext(
                InstrumentationRegistry.getInstrumentation().targetContext.applicationContext
            )
            modules(listOf(testModule,androidSecurityModule, viewModelsModule))
        }
    }
}