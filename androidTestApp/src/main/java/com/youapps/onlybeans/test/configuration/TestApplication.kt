package com.youapps.spm.test.configuration

import android.app.Application
import androidx.test.platform.app.InstrumentationRegistry
import com.youapps.spm.android.di.viewModelsModule
import com.youapps.spm.di.androidSecurityModule
import com.youapps.spm.test.di.testModule
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin

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